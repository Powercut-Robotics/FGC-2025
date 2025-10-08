package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;

public class Arms implements Subsystem {
    public static final Arms INSTANCE = new Arms();
    private Arms() { }

    private Telemetry telemetry = ActiveOpMode.telemetry();

    private boolean activated = false;

    private final MotorEx leftArm = new MotorEx("leftArm")
            .zeroed()
            .brakeMode();

    private final MotorEx rightArm = new MotorEx("rightArm")
            .zeroed()
            .brakeMode()
            .reversed();

    private final MotorGroup arms = new MotorGroup(leftArm, rightArm);

    private final ControlSystem armControlSystem = ControlSystem.builder()
            .posPid(0.005, 0, 0)
            .build();



    public Command foldBack = new SequentialGroup(
            new InstantCommand(() -> activated = true),
            new RunToPosition(armControlSystem, 0).requires(this)
    );
    public Command openArm = new SequentialGroup(
            new InstantCommand(() -> activated = true),
            new RunToPosition(armControlSystem, 500).requires(this)
    );
    public Command holdArm = new SequentialGroup(
            new InstantCommand(() -> activated = true),
            new RunToPosition(armControlSystem, 650).requires(this)
    );
    public Command squeezeArm = new SequentialGroup(
            new InstantCommand(() -> activated = true),
            new RunToPosition(armControlSystem, 1000).requires(this)
    );



    @Override
    public void initialize() {
        //0 for folded back
        leftArm.setCurrentPosition(1000);
        rightArm.setCurrentPosition(1000);
    }

    @Override
    public void periodic() {
        telemetry.addData("Left Arm Pos", leftArm.getCurrentPosition());
        telemetry.addData("Right Arm Pos", rightArm.getCurrentPosition());
        if (activated) {
            arms.setPower(armControlSystem.calculate(arms.getState()));
        } else {
            arms.setPower(0);
        }
    }
}
