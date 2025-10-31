package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Arms;

import org.firstinspires.ftc.teamcode.subsystems.Climber;
import org.firstinspires.ftc.teamcode.subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Pusher;

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
    }

    @Override
    public void onStartButtonPressed() {
        driverControlled = new DifferentialArcadeDriverControlled(leftDriveMotor, rightDriveMotor, Gamepads.gamepad1().leftStickY().negate(), Gamepads.gamepad1().rightStickX().negate().mapToRange(doubleValue -> doubleValue * 0.5));
        driverControlled.schedule();

        Flywheel.INSTANCE.spinUp.schedule();
        Climber.INSTANCE.holdPosition.schedule();

        Gamepads.gamepad1().rightBumper()
                .whenBecomesTrue(() -> driverControlled.setScalar(0.3))
                .whenBecomesFalse(() -> driverControlled.setScalar(1));

        Gamepads.gamepad1().rightTrigger().atLeast(0.5)
                .whenBecomesTrue(() -> driverControlled.setScalar(0.5))
                .whenBecomesFalse(() -> driverControlled.setScalar(1));

        Gamepads.gamepad1().leftBumper()
                .whenBecomesTrue(Climber.INSTANCE.climbUp)
                .whenBecomesTrue(Flywheel.INSTANCE.cutPower)
                .whenBecomesFalse(Climber.INSTANCE.holdPosition);

        Gamepads.gamepad1().cross()
                .whenBecomesTrue(Pusher.INSTANCE.pushUpBalls)
                .whenBecomesFalse(Pusher.INSTANCE.holdBalls);

        Gamepads.gamepad2().square()
                .whenBecomesTrue(Climber.INSTANCE.continueHangSixSec);

        Gamepads.gamepad2().circle()
                .whenBecomesTrue(Climber.INSTANCE.continueHangTenSec);

        Gamepads.gamepad2().leftTrigger().atLeast(0.2)
                .whenBecomesTrue(Arms.INSTANCE.leftArmsOut)
                .whenBecomesFalse(Arms.INSTANCE.leftArmsHold);

        Gamepads.gamepad2().rightTrigger().atLeast(0.2)
                .whenBecomesTrue(Arms.INSTANCE.rightArmsOut)
                .whenBecomesFalse(Arms.INSTANCE.rightArmsHold);

        Gamepads.gamepad2().leftBumper()
                .whenBecomesTrue(Arms.INSTANCE.leftArmsIn)
                .whenBecomesFalse(Arms.INSTANCE.leftArmsHold);

        Gamepads.gamepad2().rightBumper()
                .whenBecomesTrue(Arms.INSTANCE.rightArmsIn)
                .whenBecomesFalse(Arms.INSTANCE.rightArmsHold);

        Gamepads.gamepad2().dpadDown()
                .whenBecomesTrue(Intake.INSTANCE.intake)
                .whenBecomesFalse(Intake.INSTANCE.stopIntake);

        Gamepads.gamepad2().cross()
                .whenBecomesTrue(Intake.INSTANCE.intake)
                .whenBecomesFalse(Intake.INSTANCE.stopIntake);
    }

    @Override
    public void onUpdate() {
        telemetry.update();
    }


}
