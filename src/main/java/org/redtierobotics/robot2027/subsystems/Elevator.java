package org.redtierobotics.robot2027.subsystems;

import static org.wpilib.units.Units.Centimeters;
import static org.wpilib.units.Units.Inches;
import static org.wpilib.units.Units.Pounds;
import static org.wpilib.units.Units.Radians;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import org.redtierobotics.lib.config.MechanismConfigs.ElevatorConfig;
import org.redtierobotics.lib.io.IMotor;
import org.redtierobotics.lib.io.ITalonFXWithFollowers.TalonFXFollower;
import org.redtierobotics.lib.subsystem.State;
import org.redtierobotics.lib.subsystem.Subsystem;
import org.redtierobotics.robot2027.Ports;
import org.wpilib.math.system.DCMotor;
import org.wpilib.units.measure.Distance;

public class Elevator implements Subsystem {
	public class ElevatorState implements State {
		Distance position;

		public ElevatorState(Distance position) {
			this.position = position;
		}

		@Override
		public void onEnter() {
			setPosition(position);
		}

		public Subsystem getSubsystem() {
			return Elevator.getInstance();
		}
	}

	public TalonFX leftMotor = Ports.ELEVATOR_LEFT.getTalonFX();
	public TalonFX rightMotor = Ports.ELEVATOR_RIGHT.getTalonFX();

	public ElevatorConfig config =
			new ElevatorConfig(5, Inches.of(1), Inches.of(0), Inches.of(60), Pounds.of(10));

	public IMotor io =
			IMotor.getTalonFxIo(
					leftMotor,
					"Elevator",
					config.getSim(config.minHeight(), DCMotor.getKrakenX60(1)),
					new TalonFXFollower(rightMotor, MotorAlignmentValue.Opposed));

	private static Elevator instance;

	public static Elevator getInstance() {
		if (instance == null) {
			instance = new Elevator();
		}

		return instance;
	}

	public final ElevatorState HOME = new ElevatorState(Inches.of(0));

	public final ElevatorState GRAB = new ElevatorState(Inches.of(24));

	public final ElevatorState INTAKE = new ElevatorState(Inches.of(26));

	public final ElevatorState PRE_SCORE = new ElevatorState(Inches.of(60));

	public final ElevatorState SCORE = new ElevatorState(Inches.of(55));

	public Elevator() {}

	public State getDefaultState() {
		return HOME;
	}

	public void log() {
		io.log();
	}

	public boolean atTarget() {
		return io.positionIsNear(
				Radians.of(Centimeters.of(0.5).div(config.sprocketRadius()).magnitude()));
	}

	public void setPosition(Distance distance) {
		io.setPosition(Radians.of(distance.div(config.sprocketRadius()).magnitude()));
	}
}
