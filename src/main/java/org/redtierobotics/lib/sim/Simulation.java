package org.redtierobotics.lib.sim;

public interface Simulation {
	void input(double voltage);

	void update(double dt);

	double getPosition();

	double getVelocity();

	double getAcceleration();

	double getRotorPosition();

	double getRotorVelocity();

	double getRotorAcceleration();
}
