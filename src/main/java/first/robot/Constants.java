package first.robot;

import com.ctre.phoenix6.CANBus;

public class Constants {
    public class CANIds {
        public static final CANBus SHOOTER_CAN_BUS = new CANBus("Shooter");

        public static final int SHOOTER_MOTOR_RIGHT = 0;
        public static final int SHOOTER_MOTOR_LEFT = 1;
    }
}
