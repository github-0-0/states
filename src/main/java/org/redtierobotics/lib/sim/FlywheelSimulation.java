package org.redtierobotics.lib.sim;

import static org.wpilib.units.Units.KilogramSquareMeters;

import org.wpilib.math.system.DCMotor;
import org.wpilib.math.system.Models;
import org.wpilib.simulation.FlywheelSim;
import org.wpilib.units.measure.MomentOfInertia;

public class FlywheelSimulation implements Simulation {
	FlywheelSim sim;
	double rotorToMechanismRatio;
	double accumulator;

	public FlywheelSimulation(
			double gearRatio, MomentOfInertia moi, DCMotor gearbox, double... measurementStdDevs) {
		rotorToMechanismRatio = gearRatio / (2 * Math.PI);
		sim =
				new FlywheelSim(
						Models.flywheelFromPhysicalConstants(gearbox, moi.in(KilogramSquareMeters), gearRatio),
						gearbox,
						measurementStdDevs);
	}

	@Override
	public void input(double voltage) {
		sim.setInput(voltage);
	}

	@Override
	public void update(double dt) {
		sim.update(dt);
		accumulator += dt * sim.getAngularVelocity();
	}

	@Override
	public double getPosition() {
		return accumulator;
	}

	@Override
	public double getVelocity() {
		return sim.getAngularVelocity();
	}

	@Override
	public double getAcceleration() {
		return sim.getAngularAcceleration();
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
