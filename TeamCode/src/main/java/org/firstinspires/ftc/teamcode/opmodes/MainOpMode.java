package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
                new SubsystemComponent(Flywheel.INSTANCE, Pusher.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    private final MotorEx leftDriveMotor = new MotorEx("front_left").reversed();
    private final MotorEx rightDriveMotor = new MotorEx("front_right");
    public Command driverControlled;

    @Override
    public void onStartButtonPressed() {
        driverControlled = new DifferentialArcadeDriverControlled(leftDriveMotor, rightDriveMotor, Gamepads.gamepad1().leftStickY().negate(), Gamepads.gamepad1().rightStickX());
        driverControlled.schedule();

        Flywheel.INSTANCE.spinUp.schedule();

        Gamepads.gamepad2().dpadUp()
                .whenBecomesTrue(Intake.INSTANCE.intake)
                .whenBecomesFalse(Intake.INSTANCE.stopIntake);

        Gamepads.gamepad1().cross()
                .whenBecomesTrue(Pusher.INSTANCE.shootBalls)
                .whenBecomesFalse(Pusher.INSTANCE.holdBalls);

    }


}
