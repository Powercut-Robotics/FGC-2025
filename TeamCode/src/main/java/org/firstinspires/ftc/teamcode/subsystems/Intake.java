package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake() { }

    private ControlSystem intakeControlSystem = ControlSystem.builder()
            .velPid(1)
            .build();

    private MotorEx intakeMotor = new MotorEx("intakeMotor")
            .brakeMode();

    public Command stopIntake = new RunToVelocity(intakeControlSystem, 0).requires(intakeMotor);
    public Command intake = new RunToVelocity(intakeControlSystem, 100).requires(intakeMotor);

    @Override
    public void periodic() {
        intakeMotor.setPower(intakeControlSystem.calculate(intakeMotor.getState()));
    }

}
