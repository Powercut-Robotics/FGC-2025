package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;

public class Arms implements Subsystem {
    public static final Arms INSTANCE = new Arms();
    private Arms() { }

    private double armMaxPower = 0.5;

    private Telemetry telemetry;

    private boolean activated = false;

    private final MotorEx leftArm = new MotorEx("leftArm")
            .zeroed()
            .brakeMode()
            .reversed();
    private final MotorEx rightArm = new MotorEx("rightArm")
            .zeroed()
            .brakeMode();


 //   private final MotorGroup arms = new MotorGroup(leftArm, rightArm);

    private final ControlSystem armControlSystem = ControlSystem.builder()
            .posPid(0.005, 0, 0)
            .build();



    public Command foldBack = new SequentialGroup(
            new InstantCommand(() -> activated = true),
            new RunToPosition(armControlSystem, 100)
    ).requires(this);
    public Command openArm = new SequentialGroup(
            new InstantCommand(() -> activated = true),
            new RunToPosition(armControlSystem, 500)
    ).requires(this);
    public Command holdArm = new SequentialGroup(
            new InstantCommand(() -> activated = true),
            new RunToPosition(armControlSystem, 200)
    ).requires(this);
    public Command squeezeArm = new SequentialGroup(
            new InstantCommand(() -> activated = true),
            new RunToPosition(armControlSystem, 0)
    ).requires(this);
    
    public Command resetArms = new SequentialGroup(
            new InstantCommand(leftArm::zeroed),
            new InstantCommand(rightArm::zeroed)
    ).requires(this);



    @Override
    public void initialize() {
            telemetry = ActiveOpMode.telemetry();

        //0 for folded back
        leftArm.zeroed();
        rightArm.zeroed();
    }

    @Override
    public void periodic() {

        if (activated) {
            double leftPower = armControlSystem.calculate(leftArm.getState());
            double rightPower = armControlSystem.calculate(rightArm.getState());

            if (leftPower > armMaxPower) {
                leftPower = armMaxPower;
            } else if (leftPower < -armMaxPower) {
                leftPower = -armMaxPower;
            }

            if (leftPower > armMaxPower) {
                leftPower = armMaxPower;
            } else if (leftPower < -armMaxPower) {
                leftPower = -armMaxPower;
            }

            telemetry.addData("Left Arm Pos", leftArm.getCurrentPosition());
            telemetry.addData("Right Arm Pos", rightArm.getCurrentPosition());
            telemetry.addData("Left Arm Power", leftPower);
            telemetry.addData("Right Arm Power", rightPower);
            leftArm.setPower(leftPower);
            rightArm.setPower(rightPower);
        } else {
            leftArm.setPower(0);
            rightArm.setPower(0);
        }
    }
}
