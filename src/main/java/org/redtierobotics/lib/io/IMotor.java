package org.redtierobotics.lib.io;

import com.ctre.phoenix6.hardware.TalonFX;
import org.redtierobotics.lib.io.ITalonFXWithFollowers.TalonFXFollower;
import org.redtierobotics.lib.sim.Simulation;
import org.wpilib.framework.RobotBase;
import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Voltage;

public interface IMotor {
	public static enum RunMode {
		VOLTAGE,
		VOLTAGE_TRAPEZOIDAL,
		VOLTAGE_EXPONENTIAL,

		CURRENT,
		CURRENT_TRAPEZOIDAL,
		CURRENT_EXPONENTIAL
	}

	void setRunMode(RunMode mode);

	void setNeutral();

	void setDutyCycle(double dutyCycle);

	void setVoltage(Voltage voltage);

	void setPosition(Angle position);

	void setVelocity(AngularVelocity velocity);

	void setCurrent(Current current);

	void addPositionFeedforward(Current feedforward);

	void addVelocityFeedforward(Current feedforward);

	void addPositionFeedforward(Voltage feedforward);

	void addVelocityFeedforward(Voltage feedforward);

	Angle getPosition();

	AngularVelocity getVelocity();

	boolean positionIsNear(Angle eps);

	boolean velocityIsNear(AngularVelocity eps);

	void reset(Angle position);

	void log();

	public static IMotor getTalonFxIo(
			TalonFX motor, String key, Simulation sim, TalonFXFollower... followers) {
		return followers.length == 0
				? RobotBase.isReal() ? new ITalonFX(motor, key) : new ISimTalonFX(motor, sim, key)
				: RobotBase.isReal()
						? new ITalonFXWithFollowers(motor, key, followers)
						: new ISimTalonFXWithFollowers(motor, sim, key, followers);
	}
}
