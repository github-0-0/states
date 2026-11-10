package org.redtierobotics.robot2027.subsystems;

import static org.wpilib.units.Units.PoundSquareInches;
import static org.wpilib.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.hardware.TalonFX;
import org.redtierobotics.lib.config.MechanismConfigs.RollerConfig;
import org.redtierobotics.lib.io.IMotor;
import org.redtierobotics.lib.subsystem.State;
import org.redtierobotics.lib.subsystem.Subsystem;
import org.redtierobotics.robot2027.Ports;
import org.wpilib.math.system.DCMotor;
import org.wpilib.units.measure.AngularVelocity;

public class IntakeRoller implements Subsystem {
	public class IntakeRollerState implements State {
		AngularVelocity velocity;

		public IntakeRollerState(AngularVelocity velocity) {
			this.velocity = velocity;
		}

		@Override
		public void onEnter() {
			io.setVelocity(velocity);
		}

		public Subsystem getSubsystem() {
			return IntakeRoller.getInstance();
		}
	}

	public RollerConfig config = new RollerConfig(1, PoundSquareInches.of(0.5));

	public TalonFX motor = Ports.INTAKE_ROLLER.getTalonFX();
	public IMotor io =
			IMotor.getTalonFxIo(motor, "IntakeRoller", config.getSim(DCMotor.getKrakenX44(1)));

	private static IntakeRoller instance;

	public static IntakeRoller getInstance() {
		if (instance == null) {
			instance = new IntakeRoller();
		}

		return instance;
	}

	public final IntakeRollerState STOP = new IntakeRollerState(RotationsPerSecond.of(0));
	;

	public final IntakeRollerState INTAKE = new IntakeRollerState(RotationsPerSecond.of(20));
	;

	public IntakeRoller() {}

	public State getDefaultState() {
		return STOP;
	}

	public void log() {
		io.log();
	}

	public boolean atTarget() {
		return true; // does not matter
	}
}
