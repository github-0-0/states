package org.redtierobotics.robot2027.subsystems;

import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.PoundSquareInches;
import static org.wpilib.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.hardware.TalonFX;
import org.redtierobotics.lib.config.MechanismConfigs.RollerConfig;
import org.redtierobotics.lib.io.IMotor;
import org.redtierobotics.lib.io.ITalonFX;
import org.redtierobotics.lib.subsystem.State;
import org.redtierobotics.lib.subsystem.Subsystem;
import org.redtierobotics.robot2027.Ports;
import org.wpilib.framework.RobotBase;
import org.wpilib.math.system.DCMotor;
import org.wpilib.units.measure.AngularVelocity;

public class EndEffector implements Subsystem {
	public class EndEffectorState implements State {
		AngularVelocity velocity;

		public EndEffectorState(AngularVelocity velocity) {
			this.velocity = velocity;
		}

		@Override
		public void onEnter() {
			io.setVelocity(velocity);
		}

		public Subsystem getSubsystem() {
			return EndEffector.getInstance();
		}
	}

	public RollerConfig config = new RollerConfig(5, PoundSquareInches.of(0.05));

	public TalonFX motor = Ports.END_EFFECTOR.getTalonFX();
	public IMotor io =
			IMotor.getTalonFxIo(motor, "EndEffector", config.getSim(DCMotor.getKrakenX44(1)));

	private static EndEffector instance;

	public static EndEffector getInstance() {
		if (instance == null) {
			instance = new EndEffector();
		}

		return instance;
	}

	public final EndEffectorState STOP = new EndEffectorState(RotationsPerSecond.of(0));

	public final EndEffectorState GRAB = new EndEffectorState(RotationsPerSecond.of(5));

	public final EndEffectorState EJECT = new EndEffectorState(RotationsPerSecond.of(-5));

	public EndEffector() {}

	public State getDefaultState() {
		return STOP;
	}

	public void log() {
		io.log();
	}

	public boolean atTarget() {
		return io.velocityIsNear(RotationsPerSecond.of(0.5)) || pieceSensed();
	}

	public boolean pieceSensed() {
		return RobotBase.isReal()
				? !io.velocityIsNear(RotationsPerSecond.of(0.4))
						&& ((ITalonFX) io).getMotor().getTorqueCurrent().getValue().gt(Amps.of(20))
				: io.velocityIsNear(RotationsPerSecond.of(0.5));
	}
}
