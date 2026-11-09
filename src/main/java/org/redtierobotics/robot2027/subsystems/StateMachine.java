package org.redtierobotics.robot2027.subsystems;

import java.util.function.BooleanSupplier;

public class StateMachine {

	// Triggers
	BooleanSupplier coralIndexed = Indexer.getInstance()::pieceSensed;
	BooleanSupplier coralInManipulator = EndEffector.getInstance()::pieceSensed;
}
