package org.redtierobotics.lib.io;

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

	void reset(Angle position);

	void log();
}
