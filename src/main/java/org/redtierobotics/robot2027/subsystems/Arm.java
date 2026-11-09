package org.redtierobotics.robot2027.subsystems;

import static org.wpilib.units.Units.Degrees;
import static org.wpilib.units.Units.Inches;
import static org.wpilib.units.Units.PoundSquareInches;

import com.ctre.phoenix6.hardware.TalonFX;
import org.redtierobotics.lib.config.MechanismConfigs.ArmConfig;
import org.redtierobotics.lib.io.IMotor;
import org.redtierobotics.lib.io.ITalonFX;
import org.redtierobotics.lib.subsystem.State;
import org.redtierobotics.lib.subsystem.Subsystem;
import org.redtierobotics.robot2027.Ports;
import org.wpilib.units.measure.Angle;

public class Arm implements Subsystem {
	public class ArmState implements State {
		Angle position;

		public ArmState(Angle position) {
			this.position = position;
		}

		@Override
		public void onEnter() {
			io.setPosition(position);
		}

		public Arm getSubsystem() {
			return Arm.getInstance();
		}
	}

	public TalonFX motor = Ports.ARM.getTalonFX();
	public IMotor io = new ITalonFX(motor, "Arm");

	public ArmConfig config =
			new ArmConfig(
					50, Degrees.of(-180), Degrees.of(180), Inches.of(12), PoundSquareInches.of(100));

	private static Arm instance;

	public static Arm getInstance() {
		if (instance == null) {
			instance = new Arm();
		}

		return instance;
	}

	public final ArmState HOME = new ArmState(Degrees.of(0));

	public final ArmState DOWN = new ArmState(Degrees.of(-180));

	public final ArmState PRE_SCORE = new ArmState(Degrees.of(45));

	public final ArmState SCORE = new ArmState(Degrees.of(60));

	public Arm() {}

	public State getDefaultState() {
		return HOME;
	}

	public void log() {}

	public boolean atTarget() {
		return io.positionIsNear(Degrees.of(3));
	}
}
