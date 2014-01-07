package com.orion.gar.model;

public class ThinkAction extends GameAction {

	public ThinkSubtype subtype;
	
	public ThinkAction(ThinkSubtype subtype) {
		super(ActionType.THINK);
		this.subtype = subtype;
	}

	/* getters and setters */
	
	public ThinkSubtype getSubtype() {
		return subtype;
	}

	public void setSubtype(ThinkSubtype subtype) {
		this.subtype = subtype;
	}
	
	public void execute(Game game){
		if (this.getSubtype() == ThinkSubtype.DRAW_FROM_DECK){
			game.playerThinks();
		} else if (this.getSubtype() == ThinkSubtype.TAKE_SENATOR){
			game.playerTakesSenator();
		}
	}

	@Override
	public String toString() {
		return "THINK [subtype=" + subtype + "]";
	}
	
}
