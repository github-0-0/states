package org.redtierobotics.robot2027.subsystems;

import java.util.Map;
import java.util.function.BooleanSupplier;
import org.redtierobotics.lib.subsystem.State;
import org.redtierobotics.lib.subsystem.Subsystem;
import org.redtierobotics.robot2027.Robot;

public class Superstructure implements Subsystem {
	public static class SuperState implements State {
		SubstateData substate;
		Map<BooleanSupplier, State> branches;

		public SuperState(SubstateData substate) {
			this.substate = substate;
		}

		public void addBranch(BooleanSupplier trigger, State state) {
			branches.put(trigger, state);
		}

		@Override
		public void onEnter() {
			substate.arm.apply();
			substate.elevator.apply();
			substate.endEffector.apply();
			substate.indexer.apply();
			substate.intakePivot.apply();
			substate.intakeRoller.apply();
		}

		@Override
		public void periodic() {
			for (Map.Entry<BooleanSupplier, State> entry : branches.entrySet()) {
				if (entry.getKey().getAsBoolean()) {
					entry.getValue().apply();
				}
			}
		}

		@Override
		public Subsystem getSubsystem() {
			return Superstructure.getInstance();
		}
	}

	private static Superstructure instance;

	public static Superstructure getInstance() {
		if (instance == null) {
			instance = new Superstructure();
		}

		return instance;
	}

	// Triggers
	public final BooleanSupplier pieceIndexed = Indexer.getInstance()::pieceSensed;
	public final BooleanSupplier pieceInEndEffector = EndEffector.getInstance()::pieceSensed;
	public final BooleanSupplier atTarget =
			() ->
					Arm.getInstance().atTarget()
							&& Elevator.getInstance().atTarget()
							&& EndEffector.getInstance().atTarget()
							&& Indexer.getInstance().atTarget()
							&& IntakePivot.getInstance().atTarget()
							&& IntakeRoller.getInstance().atTarget();
	public final BooleanSupplier scoreTriggered = Robot.controller::getAButton;
	public final BooleanSupplier intakeTriggered = Robot.controller::getBButton;
	public final BooleanSupplier intakeCancelled = () -> !Robot.controller.getBButton();

	public final SuperState DEFAULT = new SuperState(SubstateData.DEFAULT);
	public final SuperState INTAKING_1 = new SuperState(SubstateData.INTAKING_1);
	public final SuperState INTAKING_2 = new SuperState(SubstateData.INTAKING_2);
	public final SuperState GRAB_PIECE = new SuperState(SubstateData.GRAB_PIECE);
	public final SuperState STOW_PIECE_1 = new SuperState(SubstateData.STOW_PIECE_1);
	public final SuperState STOW_PIECE_2 = new SuperState(SubstateData.STOW_PIECE_2);
	public final SuperState PRE_SCORE = new SuperState(SubstateData.PRE_SCORE);
	public final SuperState SCORE = new SuperState(SubstateData.SCORE);

	public Superstructure() {
		DEFAULT.addBranch(intakeTriggered, INTAKING_1);
		INTAKING_1.addBranch(atTarget, INTAKING_2);
		INTAKING_1.addBranch(intakeCancelled, DEFAULT);
		INTAKING_2.addBranch(pieceIndexed, GRAB_PIECE);
		INTAKING_2.addBranch(intakeCancelled, INTAKING_1);
		GRAB_PIECE.addBranch(atTarget, STOW_PIECE_1);
		STOW_PIECE_1.addBranch(atTarget, STOW_PIECE_2);
		STOW_PIECE_2.addBranch(scoreTriggered, PRE_SCORE);
		PRE_SCORE.addBranch(atTarget, SCORE);
	}

	@Override
	public State getDefaultState() {
		return DEFAULT;
	}

	@Override
	public void log() {}
}
