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
            .brakeMode()
            .reversed();

    private final ControlSystem armControlSystem = ControlSystem.builder()
            .posPid(0.01, 0, 0)
            .build();

    public Command foldAway = new RunToPosition(armControlSystem, 0).requires(this);
    public Command openArm = new RunToPosition(armControlSystem, 200).requires(this);
    public Command holdArm = new RunToPosition(armControlSystem, 300).requires(this);
    public Command squeezeArm = new RunToPosition(armControlSystem, 400).requires(this);

    @Override
    public void initialize() {
        //400 for arms folded across intake
        //0 for folded back
        leftArm.setCurrentPosition(400);
        rightArm.setCurrentPosition(400);
    }

    @Override
    public void periodic() {
        leftArm.setPower(armControlSystem.calculate(leftArm.getState()));
        rightArm.setPower(armControlSystem.calculate(rightArm.getState()));
    }
}
