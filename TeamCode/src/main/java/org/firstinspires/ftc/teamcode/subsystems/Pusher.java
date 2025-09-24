package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class Pusher implements Subsystem {
    public static final Pusher INSTANCE = new Pusher();
    private Pusher() { }

    private double power = 0;

    //private ControlSystem pusherControlSystem = ControlSystem.builder()
    //        .velPid(1)
    //        .build();

    private MotorEx pusherMotor = new MotorEx("pusherMotor")
            .brakeMode();

    //public Command pushDownBalls = new RunToVelocity(pusherControlSystem, -100).requires(pusherMotor);
    //public Command holdBalls = new RunToVelocity(pusherControlSystem, 0).requires(pusherMotor);
    //public Command shootBalls = new RunToVelocity(pusherControlSystem, 100).requires(pusherMotor);

    public Command pushDownBalls = new InstantCommand(() -> power = -1).requires(pusherMotor);
    public Command holdBalls = new InstantCommand(() -> power = 0).requires(pusherMotor);
    public Command pushUpBalls = new InstantCommand(() -> power = 1).requires(pusherMotor);

    @Override
    public void periodic() {
        //pusherMotor.setPower(pusherControlSystem.calculate(pusherMotor.getState()));
        pusherMotor.setPower(power);
    }

}
