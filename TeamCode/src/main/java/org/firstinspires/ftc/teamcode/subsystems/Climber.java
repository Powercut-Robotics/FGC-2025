package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
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
    public Command continueHangSixSec = new SequentialGroup(
            new InstantCommand(() -> power = 1).thenWait(6),
            new InstantCommand(() -> power = 0)
    ).requires(climbMotor);
    public Command continueHangTenSec = new SequentialGroup(
            new InstantCommand(() -> power = 1).thenWait(10),
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
