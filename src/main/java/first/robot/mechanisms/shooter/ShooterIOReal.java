package first.robot.mechanisms.shooter;

import org.wpilib.units.measure.AngularVelocity;

import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;

import badgerutils.advantagekit.talonfx.TalonFXSignals;
import badgerutils.motor.MotorGroup;
import first.robot.Constants;

public class ShooterIOReal implements ShooterIO {
    // Motor declarations
    private final TalonFX rightMotor = new TalonFX(Constants.CANIds.SHOOTER_MOTOR_RIGHT, Constants.CANIds.SHOOTER_CAN_BUS);
    private final TalonFX leftMotor = new TalonFX(Constants.CANIds.SHOOTER_MOTOR_LEFT, Constants.CANIds.SHOOTER_CAN_BUS);

    // Motor signals
    private final TalonFXSignals rightMotorSignals = new TalonFXSignals(rightMotor);
    private final TalonFXSignals leftMotorSignals = new TalonFXSignals(leftMotor);

    // Motor group
    private final MotorGroup motorGroup = new MotorGroup(rightMotor, leftMotor);

    // Requests
    private final VelocityTorqueCurrentFOC velocityRequest = new VelocityTorqueCurrentFOC(0);
    private final NeutralOut neutralRequest = new NeutralOut();

    public ShooterIOReal() {
        rightMotor.getConfigurator().apply(ShooterConstants.MOTOR_CONFIG_CW);
        leftMotor.getConfigurator().apply(ShooterConstants.MOTOR_CONFIG_CCW);
    }

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.rightMotor = rightMotorSignals.createLoggedTalonFX();
        inputs.leftMotor = leftMotorSignals.createLoggedTalonFX();
    }

    @Override
    public void setVelocity(AngularVelocity velocity) {
        velocityRequest.withVelocity(velocity); // Setting velocity request
        motorGroup.setControl(velocityRequest); // Applying velocity request
    }

    @Override // Sets the motors to the idle mode (coast)
    public void setIdle() {
        motorGroup.setControl(neutralRequest);
    }
}
