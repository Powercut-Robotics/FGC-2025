package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake() { }

    private double power = 0;

//    private ControlSystem intakeControlSystem = ControlSystem.builder()
//            .velPid(1)
//            .build();

    private MotorEx intakeMotor = new MotorEx("intakeMotor")
            .brakeMode();

    public Command stopIntake = new InstantCommand(() -> power = 0).requires(this);
    public Command intake = new InstantCommand(() -> power = 1).requires(this);

    public Command outtake = new InstantCommand(() -> power = -1).requires(this);

    @Override
    public void periodic() {
        intakeMotor.setPower(power);
    }

}
