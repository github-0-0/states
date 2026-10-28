package org.redtierobotics.lib.subsystem;

import java.util.Arrays;
import java.util.List;

public class SubsystemManager {
	List<Subsystem> subsystems;

	public SubsystemManager(Subsystem... subsystems) {
		this.subsystems = Arrays.asList(subsystems);
		for (Subsystem subsystem : subsystems) {
			subsystem.init();
		}
	}

	public void periodic() {
		for (Subsystem subsystem : subsystems) {
			subsystem.periodic();
		}
	}
}
