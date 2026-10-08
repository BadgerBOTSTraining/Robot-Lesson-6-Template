// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot;

import static org.wpilib.units.Units.RotationsPerSecond;

import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.button.CommandXboxController;

import first.robot.mechanisms.shooter.Shooter;
import first.robot.mechanisms.shooter.ShooterIO;
import first.robot.mechanisms.shooter.ShooterIOReal;

/**
 * AdvantageKit robot lifecycle with the Commands v3 scheduler.
 * Configure mechanisms and command bindings after Logger.start() in the constructor.
 */
public class Robot extends LoggedRobot {
  private final CommandXboxController controller = new CommandXboxController(0);

  private final Shooter shooter;

  // Bind your commands here
  public void bindControls() {
    controller.a().onTrue(shooter.run(coroutine -> {
      shooter.setVelocity(RotationsPerSecond.of(10));
      coroutine.park();
    }).named("test"));
  }

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    Logger.recordMetadata("ProjectName", "CommandsLessonTemplate");

    String replayLog = System.getenv("AKIT_LOG_PATH");
    if (isReal()) {
      Logger.addDataReceiver(new WPILOGWriter("/home/systemcore/logs"));
      Logger.addDataReceiver(new NT4Publisher());
    } else if (replayLog != null && !replayLog.isBlank()) {
      // replayWatch supplies AKIT_LOG_PATH; it can also be set for simulateJava.
      setUseTiming(false);
      replayLog = LogFileUtil.findReplayLog();
      Logger.setReplaySource(new WPILOGReader(replayLog));
      Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(replayLog, "_sim")));
    } else {
      Logger.addDataReceiver(new WPILOGWriter("logs"));
      Logger.addDataReceiver(new NT4Publisher());
    }

    Logger.start();

    shooter = new Shooter(isReal() ? new ShooterIOReal() : new ShooterIO() {});

    bindControls();
  }

  /** Runs once every loop, including while disabled. */
  @Override
  public void robotPeriodic() {
    shooter.periodic();
    Scheduler.getDefault().run();
  }

  /** Schedule the autonomous command here with Scheduler.getDefault().schedule(...). */
  @Override
  public void autonomousInit() {}

  @Override
  public void teleopInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void utilityInit() {
    Scheduler.getDefault().cancelAll();
  }

  @Override
  public void simulationPeriodic() {}
}
