package org.redtierobotics.lib.subsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SubsystemManager {
	private static SubsystemManager instance;

	public static SubsystemManager getInstance() {
		if (instance == null) {
			instance = new SubsystemManager();
		}

		return instance;
	}

	List<Subsystem> subsystems = new ArrayList<>();
	Map<Subsystem, State> states = new HashMap<>();

	private static record Transition(Subsystem subsystem, State state, State nextState) {}

	ConcurrentLinkedQueue<Transition> transitions = new ConcurrentLinkedQueue<>();

	public SubsystemManager() {}

	public void registerSubsystem(Subsystem subsystem) {
		subsystems.add(subsystem);
		states.put(subsystem, subsystem.getDefaultState());
	}

	public void periodic() {
		Transition transition;

		while ((transition = transitions.poll()) != null) {
			states.put(transition.subsystem(), transition.nextState());

			transition.state().onExit();
			transition.nextState().onEnter();
		}

		for (Subsystem subsystem : subsystems) {
			states.get(subsystem).periodic();
			subsystem.log();
		}
	}

	void transition(State nextState) {
		Subsystem subsystem = nextState.getSubsystem();

		if (states.get(subsystem) == nextState) {
			return;
		}

		transitions.add(new Transition(subsystem, states.get(subsystem), nextState));
	}
}
