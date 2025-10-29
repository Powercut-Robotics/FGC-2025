package org.firstinspires.ftc.teamcode.subsystems;

import java.time.Duration;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Climber implements Subsystem {
    public static final Climber INSTANCE = new Climber();
    private Climber() { }

    private double power = 0;

    private final MotorEx climbMotor = new MotorEx("climber")
            .brakeMode();

    private final ServoEx graspServo = new ServoEx("graspServo");


    public Command holdPosition = new InstantCommand(() -> power = 0).requires(climbMotor);
    public Command climbUp = new InstantCommand(() -> power = 1).requires(climbMotor);
    public Command continueHang = new SequentialGroup(
            new InstantCommand(() -> power = 1),
            new Delay(5),
            new InstantCommand(() -> power = 0)
    ).requires(climbMotor);


    public Command graspRope = new SetPosition(graspServo, 1).requires(graspServo);
    public Command releaseGrasp = new SetPosition(graspServo, 0.1).requires(graspServo);

    @Override
    public void initialize() {
        climbMotor.setCurrentPosition(0);
    }

    @Override
    public void periodic() {
        climbMotor.setPower(power);
    }
}
