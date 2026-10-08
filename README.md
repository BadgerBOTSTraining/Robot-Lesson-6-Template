# Commands Lesson Template

Uses WPILib **2027.0.0-alpha-7**, **Commands v3**, Java **25**, and
AdvantageKit **27.0.0-alpha-6**. The AdvantageKit annotation processor follows
the version in `vendordeps/AdvantageKit.json` and generates `*InputsAutoLogged`.

Build using the WPILib VS Code build command or, in PowerShell:

```powershell
$env:JAVA_HOME = 'C:/Users/Public/wpilib/2027_alpha7/jdk'
./gradlew.bat build
```

`Robot` extends `LoggedRobot` because this AdvantageKit alpha does not support
`OpModeRobot`. Set up mechanisms and command bindings after `Logger.start()`;
the Commands v3 scheduler runs in `robotPeriodic()`. Autonomous and teleop setup
belong in `autonomousInit()` and `teleopInit()`.

On Systemcore, logs are written to `/home/systemcore/logs`. Normal desktop
simulation writes to `logs/`. Both publish live data to AdvantageScope through
NetworkTables.

To replay a log in PowerShell:

```powershell
$env:AKIT_LOG_PATH = 'C:/path/to/robot.wpilog'
./gradlew.bat simulateJava
# Or rerun replay automatically when source files change:
./gradlew.bat replayWatch
Remove-Item Env:AKIT_LOG_PATH  # Return to normal simulation.
```

Replay writes a separate log with the `_sim` suffix. Mechanism inputs must be
passed to `Logger.processInputs(...)` each loop to participate in replay. The
shooter is already wired this way using AdvantageKit's generated
`ShooterIOInputsAutoLogged` class.
