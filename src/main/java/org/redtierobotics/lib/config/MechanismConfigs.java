package org.redtierobotics.lib.config;

import org.redtierobotics.lib.sim.ElevatorSimulation;
import org.redtierobotics.lib.sim.FlywheelSimulation;
import org.redtierobotics.lib.sim.SingleJointedArmSimulation;
import org.wpilib.math.system.DCMotor;
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
			Mass carriageMass) {
		public ElevatorSimulation getSim(Distance initialHeight, DCMotor gearbox) {
			return new ElevatorSimulation(
					gearRatio,
					carriageMass,
					sprocketRadius,
					minHeight,
					maxHeight,
					initialHeight,
					gearbox,
					0.0,
					0.0);
		}
	}

	public static record ArmConfig(
			double gearRatio, Angle minAngle, Angle maxAngle, Distance cogDistance, MomentOfInertia moi) {
		public SingleJointedArmSimulation getSim(Angle initialAngle, DCMotor gearbox) {
			return new SingleJointedArmSimulation(
					gearRatio, moi, cogDistance, minAngle, maxAngle, initialAngle, gearbox, 0.0, 0.0);
		}
	}

	public static record RollerConfig(double gearRatio, MomentOfInertia moi) {

		public FlywheelSimulation getSim(DCMotor gearbox) {
			return new FlywheelSimulation(gearRatio, moi, gearbox, 0.0);
		}
	}
}
