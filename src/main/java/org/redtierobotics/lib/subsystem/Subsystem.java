package org.redtierobotics.lib.subsystem;

public interface Subsystem {
	void init();

	void periodic();

	void log();
}
