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

@TeleOp(name = "ArmTest")
public class ArmTest extends NextFTCOpMode {
    public ArmTest() {
        addComponents(
                new SubsystemComponent(Intake.INSTANCE, Arms.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }


    @Override
    public void onInit() {
        Arms.INSTANCE.foldAway.schedule();
    }

    @Override
    public void onStartButtonPressed() {
        Gamepads.gamepad2().cross()
                .whenBecomesTrue(Arms.INSTANCE.squeezeArm);

        Gamepads.gamepad2().square()
                .whenBecomesTrue(Arms.INSTANCE.openArm);

        Gamepads.gamepad2().circle()
                .whenBecomesTrue(Arms.INSTANCE.holdArm)
                .whenBecomesTrue(Intake.INSTANCE.intake)
                .whenBecomesFalse(Intake.INSTANCE.stopIntake);



    }


}
