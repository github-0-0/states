package org.redtierobotics.lib.sim;

import static org.wpilib.units.Units.KilogramSquareMeters;
import static org.wpilib.units.Units.Meters;
import static org.wpilib.units.Units.Radians;
import static org.wpilib.units.Units.Rotations;

import org.wpilib.math.system.DCMotor;
import org.wpilib.simulation.SingleJointedArmSim;
import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.Distance;
import org.wpilib.units.measure.MomentOfInertia;

public class SingleJointedArmSimulation implements Simulation {
	SingleJointedArmSim sim;
	double ratio;

	public SingleJointedArmSimulation(
			Angle output,
			Angle input,
			MomentOfInertia moi,
			Distance cogPivotDistance,
			Angle minHeight,
			Angle maxHeight,
			Angle startingHeight,
			DCMotor gearbox,
			double... measurementStdDevs) {
		double gearRatio = input.in(Rotations) / output.in(Rotations);
		double radiansToRotor = input.in(Rotations) / output.in(Radians);
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
		ratio = radiansToRotor;
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
		return sim.getAngle();
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
