package org.redtierobotics.robot2027.subsystems;

import static org.wpilib.units.Units.Inches;
import static org.wpilib.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.hardware.TalonFX;
import org.redtierobotics.lib.io.IMotor;
import org.redtierobotics.lib.io.ITalonFX;
import org.redtierobotics.lib.subsystem.State;
import org.redtierobotics.lib.subsystem.Subsystem;
import org.redtierobotics.robot2027.Ports;
import org.wpilib.units.measure.AngularVelocity;

public class Indexer implements Subsystem {
	public class IndexerState implements State {
		AngularVelocity velocity;

		public IndexerState(AngularVelocity velocity) {
			this.velocity = velocity;
		}

		@Override
		public void onEnter() {
			io.setVelocity(velocity);
		}

		public Indexer getSubsystem() {
			return Indexer.getInstance();
		}
	}

	public TalonFX motor = Ports.INDEXER.getTalonFX();
	public CANrange sensor = Ports.INDEXER_SENSOR.getCANrange();
	public IMotor io = new ITalonFX(motor, "Indexer");

	private static Indexer instance;

	public static Indexer getInstance() {
		if (instance == null) {
			instance = new Indexer();
		}

		return instance;
	}

	public final IndexerState STOP = new IndexerState(RotationsPerSecond.of(0));

	public final IndexerState INDEX = new IndexerState(RotationsPerSecond.of(10));

	public Indexer() {}

	public State getDefaultState() {
		return STOP;
	}

	public void log() {}

	public boolean atTarget() {
		return true; // does not matter
	}

	public boolean pieceSensed() {
		return sensor.getDistance().getValue().lt(Inches.of(0.5));
	}
}
