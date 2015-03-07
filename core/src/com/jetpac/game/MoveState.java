package com.jetpac.game;

public class MoveState {
	private String[] moveStates;
	private int index;
	private float deltaTime;
	
	public MoveState(String[] moves) {
		moveStates = moves;
		index = 0;
	}
	
	public String getState() {
		if(moveStates == null || moveStates.length == 0)
			return null;
		return moveStates[index];
	}
	
	public String getNextState() {
		if(moveStates == null || moveStates.length == 0)
			return null;
		index ++;
		if(index >= moveStates.length)
			index = 0;
		return moveStates[index];
	}
	
	public String getNextState(float delta) {
		deltaTime += delta;
		if(deltaTime < 0.1f)
			return getState();
		deltaTime = 0;
		return getNextState();
	}
	
	public void reset() {
		index = 0;
	}
}
