package org.redtierobotics.lib.util;

import org.wpilib.math.kinematics.ChassisVelocities;
import org.wpilib.math.util.MathUtil;
import org.wpilib.units.Measure;
import org.wpilib.units.Unit;

/** Contains basic functions that are used often. */
public class Util {
	public static double chassisSpeedsMagnitude(ChassisVelocities other) {
		return Math.sqrt(other.vx * other.vx + other.vy * other.vy);
	}

	public static <T extends Unit> boolean epsilonEquals(
			Measure<T> actual, Measure<T> target, Measure<T> epsilon) {
		double actualUnits = actual.baseUnitMagnitude();
		double targetUnits = target.baseUnitMagnitude();
		double epsilonUnits = epsilon.baseUnitMagnitude();
		return MathUtil.isNear(actualUnits, targetUnits, epsilonUnits);
	}
}
