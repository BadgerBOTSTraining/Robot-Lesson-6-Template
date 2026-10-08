package first.robot.mechanisms.shooter;

import org.littletonrobotics.junction.AutoLog;
import org.wpilib.units.measure.AngularVelocity;

import badgerutils.advantagekit.talonfx.LoggedTalonFX;
import first.robot.Constants;

public interface ShooterIO {
    @AutoLog
    public static class ShooterIOInputs {
        public LoggedTalonFX rightMotor;
        public LoggedTalonFX leftMotor;
    }

    public default void updateInputs(ShooterIOInputs inputs) {}

    public default void setVelocity(AngularVelocity velocity) {}

    public default void setIdle() {}
}
