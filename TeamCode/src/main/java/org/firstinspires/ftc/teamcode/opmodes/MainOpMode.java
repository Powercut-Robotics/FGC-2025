package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Arms;
import org.firstinspires.ftc.teamcode.subsystems.Climber;
import org.firstinspires.ftc.teamcode.subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Pusher;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.DifferentialArcadeDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp(name = "Main OpMode")
public class MainOpMode extends NextFTCOpMode {
    public MainOpMode() {
        addComponents(
                new SubsystemComponent(Flywheel.INSTANCE, Pusher.INSTANCE, Intake.INSTANCE, Arms.INSTANCE, Climber.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    private final MotorEx leftDriveMotor = new MotorEx("leftDrive").reversed();
    private final MotorEx rightDriveMotor = new MotorEx("rightDrive");
    public DifferentialArcadeDriverControlled driverControlled;

    @Override
    public void onInit() {
        Flywheel.INSTANCE.cutPower.schedule();
        Arms.INSTANCE.resetArms.schedule();
    }

    @Override
    public void onStartButtonPressed() {
        driverControlled = new DifferentialArcadeDriverControlled(leftDriveMotor, rightDriveMotor, Gamepads.gamepad1().leftStickY().negate(), Gamepads.gamepad1().rightStickX().negate());
        driverControlled.schedule();

        Flywheel.INSTANCE.spinUp.schedule();
        Climber.INSTANCE.holdPosition.schedule();

        Gamepads.gamepad1().rightBumper()
                .whenBecomesTrue(() -> driverControlled.setScalar(0.2))
                .whenBecomesFalse(() -> driverControlled.setScalar(1));

        Gamepads.gamepad1().leftTrigger().atLeast(0.5)
                .whenBecomesTrue(Climber.INSTANCE.graspRope)
                .whenBecomesFalse(Climber.INSTANCE.releaseGrasp);

        Gamepads.gamepad1().leftBumper()
                .whenBecomesTrue(Climber.INSTANCE.climbUp)
                .whenBecomesTrue(Flywheel.INSTANCE.cutPower)
                .whenBecomesFalse(Climber.INSTANCE.holdPosition);

        Gamepads.gamepad1().cross()
                .whenBecomesTrue(Pusher.INSTANCE.pushUpBalls)
                .whenBecomesFalse(Pusher.INSTANCE.holdBalls);

        Gamepads.gamepad2().dpadDown()
                .whenBecomesTrue(Intake.INSTANCE.intake)
                .whenBecomesFalse(Intake.INSTANCE.stopIntake);

        Gamepads.gamepad2().cross()
                .whenBecomesTrue(Intake.INSTANCE.intake)
                .whenBecomesTrue(Arms.INSTANCE.squeezeArm)
                .whenBecomesFalse(Intake.INSTANCE.stopIntake);

        Gamepads.gamepad2().square()
                .whenBecomesTrue(Arms.INSTANCE.openArm);

        Gamepads.gamepad2().circle()
                .whenBecomesTrue(Arms.INSTANCE.holdArm);
    }

    @Override
    public void onUpdate() {
        telemetry.update();
    }


}
