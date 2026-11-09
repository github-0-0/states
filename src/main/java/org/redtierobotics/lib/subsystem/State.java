package org.redtierobotics.lib.subsystem;

public interface State {
	default void onEnter() {}

	default void periodic() {}

	default void onExit() {}

	Subsystem getSubsystem();
}
