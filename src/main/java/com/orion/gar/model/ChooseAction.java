package com.orion.gar.model;

public class ChooseAction extends GameAction {

	private Card card;

	public ChooseAction(Card card) {
		super(ActionType.CHOOSE_CARD);
		this.card = card;
	}
	
	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
	
	@Override
	public String toString() {
		if (card == null){
			return "" + getType();
		} else {
			return getType() + ", card=" + card;
		}
	}

	@Override
	public void execute(Game game) {
		// TODO Auto-generated method stub
	}

}
