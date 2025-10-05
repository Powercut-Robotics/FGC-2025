package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Flywheel implements Subsystem {
    public static final Flywheel INSTANCE = new Flywheel();
    private Flywheel() { }
    private boolean powered = true;
    private final ControlSystem flywheelControlSystem = ControlSystem.builder()
            .velPid(1)
            .build();


    private final MotorEx flywheelMotor = new MotorEx("flywheelMotor")
            .floatMode();

    public Command spinUp = new ParallelGroup(
            new InstantCommand(() -> powered = true),
            new RunToVelocity(flywheelControlSystem, 2200).requires(flywheelMotor)
    );
    public Command cutPower = new InstantCommand(() -> powered = false);


    @Override
    public void periodic() {
        if (powered) {
            flywheelMotor.setPower(flywheelControlSystem.calculate(flywheelMotor.getState()));
        } else {
            flywheelMotor.setPower(0);
        }
    }

}
