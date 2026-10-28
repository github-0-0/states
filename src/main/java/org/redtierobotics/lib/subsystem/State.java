package org.redtierobotics.lib.subsystem;

public interface State {
	default void onEnter() {}
	;

	default void periodic() {}
	;

	default void onTransition() {}
	;

	default void onExit() {}
	;
}
