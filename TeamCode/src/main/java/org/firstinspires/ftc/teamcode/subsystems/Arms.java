package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

public class Arms implements Subsystem {
    public static final Arms INSTANCE = new Arms();
    private Arms() { }

    private double armManualPowerSetting = 0.3;
    private double leftArmManualPower = 0;
    private double rightArmManualPower = 0;

    private final MotorEx leftArm = new MotorEx("leftArm")
            .zeroed()
            .brakeMode();
    private final MotorEx rightArm = new MotorEx("rightArm")
            .zeroed()
            .brakeMode()
            .reversed();

    public Command leftArmsIn = new InstantCommand(() -> leftArmManualPower = armManualPowerSetting).requires(leftArm);
    public Command leftArmsOut = new InstantCommand(() -> leftArmManualPower = -armManualPowerSetting).requires(leftArm);
    public Command leftArmsHold = new InstantCommand(() -> leftArmManualPower = 0).requires(leftArm);

    public Command rightArmsIn = new InstantCommand(() -> rightArmManualPower = armManualPowerSetting).requires(rightArm);
    public Command rightArmsOut = new InstantCommand(() -> rightArmManualPower = -armManualPowerSetting).requires(rightArm);
    public Command rightArmsHold = new InstantCommand(() -> rightArmManualPower = 0).requires(rightArm);



    @Override
    public void periodic() {

        double leftPower = leftArmManualPower;
        double rightPower = rightArmManualPower;

        leftArm.setPower(leftPower);
        rightArm.setPower(rightPower);
    }
}
