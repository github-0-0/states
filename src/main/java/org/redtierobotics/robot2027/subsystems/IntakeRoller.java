package org.redtierobotics.robot2027.subsystems;

import static org.wpilib.units.Units.PoundSquareInches;
import static org.wpilib.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import org.redtierobotics.lib.config.MechanismConfigs.RollerConfig;
import org.redtierobotics.lib.io.IMotor;
import org.redtierobotics.lib.io.ITalonFX;
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

	public final IntakeRollerState INTAKE = new IntakeRollerState(RotationsPerSecond.of(20));

	public IntakeRoller() {
		((ITalonFX) io)
				.configure(
						new TalonFXConfiguration()
								.withSlot0(new Slot0Configs().withKV(0.0).withKP(1.0).withKI(0.0).withKD(0.0))
								.withCurrentLimits(
										new CurrentLimitsConfigs()
												.withStatorCurrentLimit(40)
												.withSupplyCurrentLimit(30)
												.withStatorCurrentLimitEnable(true)
												.withSupplyCurrentLimitEnable(true))
								.withVoltage(
										new VoltageConfigs().withPeakForwardVoltage(12.0).withPeakReverseVoltage(-12.0))
								.withMotorOutput(
										new MotorOutputConfigs()
												.withInverted(InvertedValue.CounterClockwise_Positive)));
	}

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
