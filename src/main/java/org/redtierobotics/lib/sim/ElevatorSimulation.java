package org.redtierobotics.lib.sim;

import static org.wpilib.units.Units.*;

import org.wpilib.math.system.DCMotor;
import org.wpilib.simulation.ElevatorSim;
import org.wpilib.units.measure.Distance;
import org.wpilib.units.measure.Mass;

public class ElevatorSimulation implements Simulation {
	ElevatorSim sim;
	double ratio;

	public ElevatorSimulation(
			double gearRatio,
			Mass carriageMass,
			Distance sprocketRadius,
			Distance minHeight,
			Distance maxHeight,
			Distance startingHeight,
			DCMotor gearbox,
			double... measurementStdDevs) {
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

		ratio = gearRatio / (2 * Math.PI * sprocketRadius.in(Meters));
	}

	@Override
	public double getRatio() {
		return ratio;
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
}
