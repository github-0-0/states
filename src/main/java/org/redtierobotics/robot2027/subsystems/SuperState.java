package org.redtierobotics.robot2027.subsystems;

import org.redtierobotics.robot2027.subsystems.Arm.ArmState;
import org.redtierobotics.robot2027.subsystems.Elevator.ElevatorState;
import org.redtierobotics.robot2027.subsystems.EndEffector.EndEffectorState;
import org.redtierobotics.robot2027.subsystems.Indexer.IndexerState;
import org.redtierobotics.robot2027.subsystems.IntakePivot.IntakePivotState;
import org.redtierobotics.robot2027.subsystems.IntakeRoller.IntakeRollerState;

public enum SuperState {
	INTAKING_1(
			Arm.getInstance().HOME,
			Elevator.getInstance().INTAKE,
			EndEffector.getInstance().STOP,
			Indexer.getInstance().INDEX,
			IntakePivot.getInstance().DOWN,
			IntakeRoller.getInstance().INTAKE),
	INTAKING_2(
			Arm.getInstance().DOWN,
			Elevator.getInstance().INTAKE,
			EndEffector.getInstance().STOP,
			Indexer.getInstance().INDEX,
			IntakePivot.getInstance().DOWN,
			IntakeRoller.getInstance().INTAKE),
	GRAB_PIECE(
			Arm.getInstance().HOME,
			Elevator.getInstance().GRAB,
			EndEffector.getInstance().GRAB,
			Indexer.getInstance().STOP,
			IntakePivot.getInstance().HOME,
			IntakeRoller.getInstance().STOP),
	STOW_PIECE_1(
			Arm.getInstance().HOME,
			Elevator.getInstance().INTAKE,
			EndEffector.getInstance().GRAB,
			Indexer.getInstance().INDEX,
			IntakePivot.getInstance().HOME,
			IntakeRoller.getInstance().STOP),
	STOW_PIECE_2(
			Arm.getInstance().HOME,
			Elevator.getInstance().HOME,
			EndEffector.getInstance().STOP,
			Indexer.getInstance().INDEX,
			IntakePivot.getInstance().HOME,
			IntakeRoller.getInstance().STOP),
	PRE_SCORE(
			Arm.getInstance().PRE_SCORE,
			Elevator.getInstance().PRE_SCORE,
			EndEffector.getInstance().STOP,
			Indexer.getInstance().INDEX,
			IntakePivot.getInstance().HOME,
			IntakeRoller.getInstance().STOP),
	SCORE(
			Arm.getInstance().SCORE,
			Elevator.getInstance().SCORE,
			EndEffector.getInstance().EJECT,
			Indexer.getInstance().INDEX,
			IntakePivot.getInstance().HOME,
			IntakeRoller.getInstance().STOP),
	DEFAULT(
			Arm.getInstance().HOME,
			Elevator.getInstance().HOME,
			EndEffector.getInstance().STOP,
			Indexer.getInstance().INDEX,
			IntakePivot.getInstance().HOME,
			IntakeRoller.getInstance().STOP);

	public ArmState arm;
	public ElevatorState elevator;
	public EndEffectorState endEffector;
	public IndexerState indexer;
	public IntakePivotState intakePivot;
	public IntakeRollerState intakeRoller;

	SuperState(
			ArmState arm,
			ElevatorState elevator,
			EndEffectorState endEffector,
			IndexerState indexer,
			IntakePivotState intakePivot,
			IntakeRollerState intakeRoller) {
		this.arm = arm;
		this.elevator = elevator;
		this.endEffector = endEffector;
		this.indexer = indexer;
		this.intakePivot = intakePivot;
		this.intakeRoller = intakeRoller;
	}
}
