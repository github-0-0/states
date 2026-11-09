package org.redtierobotics.robot2027;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.hardware.TalonFX;
import org.redtierobotics.robot2027.Constants.Canbus;

public enum Ports {
	ARM(20, Canbus.CV),
	ELEVATOR_LEFT(21, Canbus.CV),
	ELEVATOR_RIGHT(22, Canbus.CV),
	INDEXER(23, Canbus.CV),
	INDEXER_SENSOR(24, Canbus.CV),
	INTAKE_PIVOT(25, Canbus.CV),
	INTAKE_ROLLER(26, Canbus.CV),
	END_EFFECTOR(27, Canbus.CV);

	int id;
	CANBus canbus;

	Ports(int id, CANBus canbus) {
		this.id = id;
		this.canbus = canbus;
	}

	public TalonFX getTalonFX() {
		return new TalonFX(id, canbus);
	}

	public CANrange getCANrange() {
		return new CANrange(id, canbus);
	}
}
