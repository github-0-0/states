package org.redtierobotics.lib.sim;

import static org.wpilib.units.Units.KilogramSquareMeters;
import static org.wpilib.units.Units.Radians;
import static org.wpilib.units.Units.Rotations;

import org.wpilib.math.system.DCMotor;
import org.wpilib.math.system.Models;
import org.wpilib.simulation.FlywheelSim;
import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.MomentOfInertia;

public class FlywheelSimulation implements Simulation {
	FlywheelSim sim;
	double ratio;
	double acc;

	public FlywheelSimulation(
			Angle output,
			Angle input,
			MomentOfInertia moi,
			DCMotor gearbox,
			double... measurementStdDevs) {
		var gearRatio = input.in(Rotations) / output.in(Rotations);
		sim =
				new FlywheelSim(
						Models.flywheelFromPhysicalConstants(gearbox, moi.in(KilogramSquareMeters), gearRatio),
						gearbox,
						measurementStdDevs);
		ratio = input.in(Rotations) / output.in(Radians);
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
		acc += dt * sim.getAngularVelocity();
	}

	@Override
	public double getPosition() {
		return acc;
	}

	@Override
	public double getVelocity() {
		return sim.getAngularVelocity();
	}

	@Override
	public double getAcceleration() {
		return sim.getAngularAcceleration();
	}
}
