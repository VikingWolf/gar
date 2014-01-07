package com.orion.gar.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayAction extends GameAction {

	protected final static Logger logger = LoggerFactory.getLogger(PlayAction.class);

	List<Card> cards;
	
	Role role;

	public PlayAction(ActionType type, Role role, Card... cards){
		super(type);
		this.role = role;
		this.cards = new ArrayList<Card>();
		for (Card card : cards){
			this.cards.add(card);
		}
	}

	/* getters, setters & adders*/
	
	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public PlayAction addCards(Card... cards){
		for (Card card: cards){
			this.cards.add(card);
		}
		return this;
	}
	
	@Override
	public void execute(Game game) {
		if (this.getType().equals(ActionType.LEAD)){
			game.setLeadingRole(this.getRole());
		}
		for (Card card : this.getCards()){
			game.playCard(card);				
		}
	}

	@Override
	public String toString() {
		return this.getType() + " [cards=" + cards + ", role=" + role + "]";
	}
	
}
