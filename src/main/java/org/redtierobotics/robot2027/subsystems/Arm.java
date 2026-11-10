package org.redtierobotics.robot2027.subsystems;

import static org.wpilib.units.Units.Degrees;
import static org.wpilib.units.Units.Inches;
import static org.wpilib.units.Units.PoundSquareInches;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import org.redtierobotics.lib.config.MechanismConfigs.ArmConfig;
import org.redtierobotics.lib.io.IMotor;
import org.redtierobotics.lib.io.ITalonFX;
import org.redtierobotics.lib.subsystem.State;
import org.redtierobotics.lib.subsystem.Subsystem;
import org.redtierobotics.robot2027.Ports;
import org.wpilib.math.system.DCMotor;
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

		public Subsystem getSubsystem() {
			return Arm.getInstance();
		}
	}

	public ArmConfig config =
			new ArmConfig(
					50, Degrees.of(-180), Degrees.of(180), Inches.of(12), PoundSquareInches.of(100));

	public TalonFX motor = Ports.ARM.getTalonFX();
	public IMotor io =
			IMotor.getTalonFxIo(motor, "Arm", config.getSim(config.minAngle(), DCMotor.getKrakenX60(1)));

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

	public Arm() {
		((ITalonFX) io)
				.configure(
						new TalonFXConfiguration()
								.withSlot0(
										new Slot0Configs()
												.withKV(0.0)
												.withKP(20.0)
												.withKI(0.0)
												.withKD(0.0)
												.withKG(0.0)
												.withGravityType(GravityTypeValue.Arm_Cosine))
								.withCurrentLimits(
										new CurrentLimitsConfigs()
												.withStatorCurrentLimit(40)
												.withSupplyCurrentLimit(30)
												.withStatorCurrentLimitEnable(true)
												.withSupplyCurrentLimitEnable(true))
								.withMotionMagic(
										new MotionMagicConfigs()
												.withMotionMagicAcceleration(50 * 5)
												.withMotionMagicCruiseVelocity(50 * 2)
												.withMotionMagicJerk(50 * 10))
								.withVoltage(
										new VoltageConfigs().withPeakForwardVoltage(12.0).withPeakReverseVoltage(-12.0))
								.withFeedback(new FeedbackConfigs().withSensorToMechanismRatio(50)));
	}

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
