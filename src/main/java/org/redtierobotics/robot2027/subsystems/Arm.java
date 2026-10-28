package org.redtierobotics.robot2027.subsystems;

import static org.wpilib.units.Units.Degrees;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.TalonFX;
import org.redtierobotics.lib.io.IMotor;
import org.redtierobotics.lib.io.ITalonFX;
import org.redtierobotics.lib.subsystem.State;

public class Arm {
	public TalonFX motor = new TalonFX(5, new CANBus("CV"));
	public IMotor io = new ITalonFX(motor, "Arm");

	public final State HOME =
			new State() {
				@Override
				public void onEnter() {
					io.setPosition(Degrees.of(0));
				}
			};

	public void init() {}

	public void periodic() {}
}
