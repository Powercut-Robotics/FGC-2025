package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class Shooter implements Subsystem {
    public static final Shooter INSTANCE = new Shooter();
    private Shooter() { }

    private ControlSystem flywheelControlSystem = ControlSystem.builder()
            .velPid(1)
            .build();

    private ControlSystem pusherControlSystem = ControlSystem.builder()
            .velPid(1)
            .build();

    private MotorEx flywheelMotor = new MotorEx("flywheelMotor");
    private MotorEx pusherMotor = new MotorEx("pusherMotor");

    public Command spinUp = new RunToVelocity(flywheelControlSystem, 2000).requires(flywheelMotor);
    public Command spinDown = new RunToVelocity(flywheelControlSystem, 0).requires(flywheelMotor);

    public Command holdBalls = new RunToVelocity(pusherControlSystem, 0).requires(pusherMotor);
    public Command shootBalls = new RunToVelocity(pusherControlSystem, 100).requires(pusherMotor);

    @Override
    public void periodic() {
        flywheelMotor.setPower(flywheelControlSystem.calculate(flywheelMotor.getState()));
        pusherMotor.setPower(pusherControlSystem.calculate(pusherMotor.getState()));
    }

}
