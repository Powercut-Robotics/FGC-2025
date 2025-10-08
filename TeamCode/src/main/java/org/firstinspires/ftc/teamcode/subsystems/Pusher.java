package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class Pusher implements Subsystem {
    public static final Pusher INSTANCE = new Pusher();
    private Pusher() { }

    private double power = 0;

    private final MotorEx pusherMotor = new MotorEx("pusherMotor")
            .brakeMode()
            .reversed();


    public Command pushDownBalls = new InstantCommand(() -> power = -1).requires(this);
    public Command holdBalls = new InstantCommand(() -> power = 0).requires(this);
    public Command pushUpBalls = new InstantCommand(() -> power = 1).requires(this);

    @Override
    public void periodic() {
        pusherMotor.setPower(power);
    }

}
