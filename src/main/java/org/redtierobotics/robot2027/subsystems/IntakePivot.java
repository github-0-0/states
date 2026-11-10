package org.redtierobotics.robot2027.subsystems;

import static org.wpilib.units.Units.Degrees;
import static org.wpilib.units.Units.Inches;
import static org.wpilib.units.Units.PoundSquareInches;

import com.ctre.phoenix6.hardware.TalonFX;
import org.redtierobotics.lib.config.MechanismConfigs.ArmConfig;
import org.redtierobotics.lib.io.IMotor;
import org.redtierobotics.lib.subsystem.State;
import org.redtierobotics.lib.subsystem.Subsystem;
import org.redtierobotics.robot2027.Ports;
import org.wpilib.math.system.DCMotor;
import org.wpilib.units.measure.Angle;

public class IntakePivot implements Subsystem {
	public class IntakePivotState implements State {
		Angle position;

		public IntakePivotState(Angle position) {
			this.position = position;
		}

		@Override
		public void onEnter() {
			io.setPosition(position);
		}

		public Subsystem getSubsystem() {
			return IntakePivot.getInstance();
		}
	}

	public ArmConfig config =
			new ArmConfig(
					50, Degrees.of(-180), Degrees.of(180), Inches.of(6), PoundSquareInches.of(1000));

	public TalonFX motor = Ports.INTAKE_PIVOT.getTalonFX();
	public IMotor io =
			IMotor.getTalonFxIo(
					motor, "IntakePivot", config.getSim(config.minAngle(), DCMotor.getKrakenX60(1)));

	private static IntakePivot instance;

	public static IntakePivot getInstance() {
		if (instance == null) {
			instance = new IntakePivot();
		}

		return instance;
	}

	public final IntakePivotState HOME = new IntakePivotState(Degrees.of(110));

	public final IntakePivotState DOWN = new IntakePivotState(Degrees.of(0));

	public IntakePivot() {}

	public State getDefaultState() {
		return HOME;
	}

	public void log() {
		io.log();
	}

	public boolean atTarget() {
		return io.positionIsNear(Degrees.of(3));
	}
}
