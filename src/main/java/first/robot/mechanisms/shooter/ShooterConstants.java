package first.robot.mechanisms.shooter;

import static org.wpilib.units.Units.Amps;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import badgerutils.motor.MotorConfigUtils;

public class ShooterConstants {
    public static final double KP = 0, KD = 0, KS = 0, KV = 0;

    public static final TalonFXConfiguration MOTOR_CONFIG_CW = new TalonFXConfiguration()
        // CW is positive; Coasts when set to idle
        .withMotorOutput(MotorConfigUtils.createMotorOutputConfig(InvertedValue.Clockwise_Positive, NeutralModeValue.Coast))
        // 60 amp stator current limit; 80 amp supply current limit
        .withCurrentLimits(MotorConfigUtils.createCurrentLimitsConfig(Amps.of(60), Amps.of(80)))
        // PID configs; gravity type doesn't matter; Velocity sign
        .withSlot0(MotorConfigUtils.createSlotConfig(KP, KD, KS, KV, 0, GravityTypeValue.Arm_Cosine, StaticFeedforwardSignValue.UseVelocitySign));

    // Inverted config
    public static final TalonFXConfiguration MOTOR_CONFIG_CCW = MotorConfigUtils.createInvertedConfig(MOTOR_CONFIG_CW);
}
