package com.orion.gar.model;

public abstract class GameAction {

	private ActionType type;
	
	public GameAction(ActionType type) {
		super();
		this.type = type;
	}

	/* getters & setters */

	public ActionType getType() {
		return type;
	}

	public void setType(ActionType type) {
		this.type = type;
	}
	
	public abstract void execute(Game game);

}
