package org.redtierobotics.lib.subsystem;

public interface Subsystem {
	State getDefaultState();

	void log();

	default void transition(State nextState) {
		SubsystemManager.getInstance().transition(nextState);
	}
}
