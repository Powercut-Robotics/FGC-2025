package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;

public class Arms implements Subsystem {
    public static final Arms INSTANCE = new Arms();
    private Arms() { }

    private final MotorEx leftArm = new MotorEx("leftArm")
            .brakeMode();

    private final MotorEx rightArm = new MotorEx("rightArm")
            .brakeMode();

    private final ControlSystem armControlSystem = ControlSystem.builder()
            .posPid(0, 0, 0)
            .build();

    public Command foldAway = new RunToPosition(armControlSystem, 0).requires(this);
    public Command openArm = new RunToPosition(armControlSystem, 100).requires(this);
    public Command holdArm = new RunToPosition(armControlSystem, 200).requires(this);
    public Command squeezeArm = new RunToPosition(armControlSystem, 300).requires(this);

    @Override
    public void initialize() {
        leftArm.setCurrentPosition(0);
        rightArm.setCurrentPosition(0);
    }

    @Override
    public void periodic() {
        leftArm.setPower(armControlSystem.calculate(leftArm.getState()));
        rightArm.setPower(armControlSystem.calculate(rightArm.getState()));
    }
}
