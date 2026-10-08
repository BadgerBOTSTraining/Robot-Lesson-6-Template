package first.robot.mechanisms.shooter;

import static org.wpilib.units.Units.RotationsPerSecond;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command3.Mechanism;
import org.wpilib.units.measure.AngularVelocity;

public class Shooter implements Mechanism {
    private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();
    private final ShooterIO io;

    public Shooter(ShooterIO io) {
        this.io = io;
    }

    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Shooter", inputs);
    }

    public void setVelocity(AngularVelocity velocity) {
        io.setVelocity(velocity);
        Logger.recordOutput("Requested Velocity", velocity.in(RotationsPerSecond));
    }

    public void setIdle() {
        io.setIdle();
        Logger.recordOutput("Requested Velocity", 0);
    }

    /* Commands Below */



}
