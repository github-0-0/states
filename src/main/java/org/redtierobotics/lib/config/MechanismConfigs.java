package org.redtierobotics.lib.config;

import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.Distance;
import org.wpilib.units.measure.Mass;
import org.wpilib.units.measure.MomentOfInertia;

public class MechanismConfigs {
	public static record ElevatorConfig(
			double gearRatio,
			Distance sprocketRadius,
			Distance minHeight,
			Distance maxHeight,
			Mass carriageMass) {}

	public static record ArmConfig(
			double gearRatio,
			Angle minAngle,
			Angle maxAngle,
			Distance cogDistance,
			MomentOfInertia moi) {}

	public static record RollerConfig(double gearRatio, MomentOfInertia moi) {}
}
