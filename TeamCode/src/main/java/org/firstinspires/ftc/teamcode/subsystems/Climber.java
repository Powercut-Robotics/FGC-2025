package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;

public class Climber implements Subsystem {
    public static final Climber INSTANCE = new Climber();
    private Climber() { }

    private double power = 0;

    private final MotorEx climbMotor = new MotorEx("climber")
            .brakeMode();


    public Command holdPosition = new InstantCommand(() -> power = 0);
    public Command climbUp = new InstantCommand(() -> power = 1);
    public Command holdHang = new InstantCommand(() -> power = 0.2);

    @Override
    public void initialize() {
        climbMotor.setCurrentPosition(0);
    }

    @Override
    public void periodic() {
        climbMotor.setPower(power);
    }
}
