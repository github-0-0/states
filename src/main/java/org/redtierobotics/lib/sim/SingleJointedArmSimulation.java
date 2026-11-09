package org.redtierobotics.lib.sim;

import static org.wpilib.units.Units.KilogramSquareMeters;
import static org.wpilib.units.Units.Meters;
import static org.wpilib.units.Units.Radians;

import org.wpilib.math.system.DCMotor;
import org.wpilib.simulation.SingleJointedArmSim;
import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.Distance;
import org.wpilib.units.measure.MomentOfInertia;

public class SingleJointedArmSimulation implements Simulation {
	SingleJointedArmSim sim;
	double rotorToMechanismRatio;
	double previousVelocity = 0.0;
	double acceleration = 0.0;

	public SingleJointedArmSimulation(
			double gearRatio,
			MomentOfInertia moi,
			Distance cogPivotDistance,
			Angle minHeight,
			Angle maxHeight,
			Angle startingHeight,
			DCMotor gearbox,
			double... measurementStdDevs) {
		rotorToMechanismRatio = gearRatio / (2 * Math.PI);
		sim =
				new SingleJointedArmSim(
						gearbox,
						gearRatio,
						moi.in(KilogramSquareMeters),
						cogPivotDistance.in(Meters),
						minHeight.in(Radians),
						maxHeight.in(Radians),
						true,
						startingHeight.in(Radians),
						measurementStdDevs);
	}

	@Override
	public void input(double voltage) {
		sim.setInput(voltage);
	}

	@Override
	public void update(double dt) {
		previousVelocity = getVelocity();
		sim.update(dt);
		acceleration = (getVelocity() - previousVelocity) / dt;
	}

	@Override
	public double getPosition() {
		return sim.getAngle();
	}

	@Override
	public double getVelocity() {
		return sim.getVelocity();
	}

	@Override
	public double getAcceleration() {
		return acceleration;
	}

	@Override
	public double getRotorPosition() {
		return getPosition() * rotorToMechanismRatio;
	}

	@Override
	public double getRotorVelocity() {
		return getVelocity() * rotorToMechanismRatio;
	}

	@Override
	public double getRotorAcceleration() {
		return getAcceleration() * rotorToMechanismRatio;
	}
}
