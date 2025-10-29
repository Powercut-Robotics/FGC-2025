package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Flywheel implements Subsystem {
    public static final Flywheel INSTANCE = new Flywheel();
    private Flywheel() { }
    private Telemetry telemetry;

    private double power = 0;


    private final MotorEx flywheelMotor = new MotorEx("flywheelMotor")
            .floatMode();

    public Command spinUp = new InstantCommand(() -> power = 0.8).requires(this);

    public Command cutPower = new InstantCommand(() -> power = 0).requires(this);

    @Override
    public void initialize() {
        telemetry = ActiveOpMode.telemetry();
    }

    @Override
    public void periodic() {
            if (ActiveOpMode.isStarted()) {
                telemetry.addData("Flywheel Speed", flywheelMotor.getVelocity());
            }
            flywheelMotor.setPower(power);

    }

}
