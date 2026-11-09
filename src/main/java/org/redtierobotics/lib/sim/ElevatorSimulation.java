package org.redtierobotics.lib.sim;

import static org.wpilib.units.Units.*;

import org.wpilib.math.system.DCMotor;
import org.wpilib.simulation.ElevatorSim;
import org.wpilib.units.measure.Distance;
import org.wpilib.units.measure.Mass;

public class ElevatorSimulation implements Simulation {
	ElevatorSim sim;
	double rotorToMechanismRatio;

	/**
	 * Creates a simulation of a generic linear mechanism
	 *
	 * @param gearRatio Ratio of motor rotations to sprocket rotations
	 * @param carriageMass Load mass
	 * @param sprocketRadius
	 * @param minHeight
	 * @param maxHeight
	 * @param startingHeight
	 * @param gearbox
	 * @param measurementStdDevs
	 */
	public ElevatorSimulation(
			double gearRatio,
			Mass carriageMass,
			Distance sprocketRadius,
			Distance minHeight,
			Distance maxHeight,
			Distance startingHeight,
			DCMotor gearbox,
			double... measurementStdDevs) {
		rotorToMechanismRatio = gearRatio / (2 * Math.PI) * sprocketRadius.in(Meters);
		sim =
				new ElevatorSim(
						gearbox,
						gearRatio,
						carriageMass.in(Kilograms),
						sprocketRadius.in(Meters),
						minHeight.in(Meters),
						maxHeight.in(Meters),
						true,
						startingHeight.in(Meters),
						measurementStdDevs);
	}

	@Override
	public void input(double voltage) {
		sim.setInput(voltage);
	}

	@Override
	public void update(double dt) {
		sim.update(dt);
	}

	@Override
	public double getPosition() {
		return sim.getPosition();
	}

	@Override
	public double getVelocity() {
		return sim.getVelocity();
	}

	@Override
	public double getAcceleration() {
		return 0.0;
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
