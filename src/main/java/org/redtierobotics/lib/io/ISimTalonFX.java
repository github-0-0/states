package org.redtierobotics.lib.io;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.sim.TalonFXSimState;
import org.redtierobotics.lib.sim.Simulation;
import org.wpilib.system.Notifier;

public class ISimTalonFX extends ITalonFX {
	protected TalonFXSimState simState;
	protected Simulation simulation;
	protected Notifier simNotifier;

	public ISimTalonFX(TalonFX talonFx, Simulation simulation, String key) {
		super(talonFx, key);
		simState = talonFx.getSimState();
		this.simulation = simulation;
		simNotifier =
				new Notifier(
						() -> {
							updateSim(0.01);
						});
		simNotifier.setName(key);
		simNotifier.startPeriodic(0.01);
	}

	public void updateSim(double dt) {
		simState.setSupplyVoltage(12);
		double voltage = simState.getMotorVoltage();
		simulation.input(voltage);
		simulation.update(dt);
		simState.setRawRotorPosition(simulation.getRotorPosition());
		simState.setRotorVelocity(simulation.getRotorVelocity());
		simState.setRotorAcceleration(simulation.getRotorAcceleration());
		refresh();
	}
}
