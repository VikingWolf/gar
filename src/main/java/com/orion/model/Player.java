package com.orion.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

	private List<ActionCard> clients;
	
	private List<ActionCard> warehouse;
	
	private List<ActionCard> treasure;
	
	private List<SiteCard> influence;
	
	private Map<ActionCard, Integer> buildings;
	
	private List<Card> played;
	
	private CardSet<Card> hand;
	
	private String name;
		
	private final static int BASIC_INFLUENCE	=	2;
	private final static int BASIC_HAND_LIMIT	=	5;
	
	public Player(int player){
		super();
		clients = new ArrayList<ActionCard>();
		warehouse = new ArrayList<ActionCard>();
		treasure = new ArrayList<ActionCard>();
		influence = new ArrayList<SiteCard>();
		buildings = new HashMap<ActionCard, Integer>();
		played = new ArrayList<Card>();
		setUpInitialHand();
		this.name = "Player " + player;
	}
	
	public Integer countPoints(){
		//TODO falta contar los totales de los tesoros
		int result = getInfluence();
		for (ActionCard treasureItem : treasure){
			result += treasureItem.getPoints();
		}
		if (hasEffect(GameEffect.THREE_EXTRA_POINTS)){
			result += 3;
		}
		return result;
	}
	
	/* actions */
	public void drawCards(final PlayDeck deck, final int amount){
		for (int i = 0; i < amount; i++){
			hand.add(deck.draw());
		}
	}
	
	public void playCard(final int cardIndex){
		played.add(this.hand.get(cardIndex));
		hand.remove(cardIndex);
	}
	
	public ActionCard playAsInitialCard(final int index, final List<ActionCard> target){
		if (index < this.hand.size()){
			target.add((ActionCard)this.hand.get(index));
			hand.remove(index);
			return target.get(target.size()-1);
		} else {
			return null;
		}
	}
		
	/* private methods */
	private void setUpInitialHand(){
		hand = new CardSet<Card>();
	}
	
	private int getInfluence(){
		int result = BASIC_INFLUENCE;
		for (SiteCard site : influence){
			result += site.getPoints();
		}
		return result;		
	}
	
	private int clientLimit(){
		int result = getInfluence();
		if (this.hasEffect(GameEffect.DOUBLE_CLIENTS)){
			result *= 2;
		} 
		if (this.hasEffect(GameEffect.CLIENTS_PLUS_TWO)){
			result += 2;
		}
		return result;
	}
	
	private int treasureLimit(){
		int result = getInfluence();
		if (this.hasEffect(GameEffect.TREASURE_PLUS_TWO)){
			result += 2;
		}
		return result;		
	}
	
	private int handLimit(){
		int result = BASIC_HAND_LIMIT;
		if (this.hasEffect(GameEffect.MAX_HAND_PLUS_TWO)){
			result += 2;
		} 
		if (this.hasEffect(GameEffect.MAX_HAND_PLUS_FOUR)){
			result += 4;
		}
		return result;
	}
	
	public Card getCardAt(int index){
		if (index >= this.getHand().size()) return null;
		else return this.getHand().get(index);
	}
	
	private boolean hasEffect(final GameEffect effect){
		boolean result = false;
		for (GameEffect candidate : getAllEffects()) {
			if (candidate.equals(effect)){
				result = true;
				break;
			}
		}
		return result;
	}
	
	private List<GameEffect> getAllEffects(){
		List<GameEffect> base = new ArrayList<GameEffect>();
		for (Building building : getFinishedBuildings()){
			base.addAll(building.getEffects());
		}
		List<GameEffect> result = new ArrayList<GameEffect>();
		if (result.contains(GameEffect.MARBLE_UNCOMPLETED_EFFECTS)){
			for (Building building : getBuildingsOf(Material.MARBLE)){
				result.addAll(building.getEffects());
			}			
		}
		return result;
	}
	
	private List<Building> getBuildingsOf(final Material material){
		List<Building> result = new ArrayList<Building>();
		for (ActionCard card : this.buildings.keySet()){
			if (card.getBuilding().equals(material)){
				result.add(card.getBuilding());				
			}
		}
		return result;
	}
	
	private List<Building> getFinishedBuildings(){
		List<Building> result = new ArrayList<Building>();
		for (ActionCard card : this.buildings.keySet()){
			if (buildings.get(card).equals(card.getPoints())){
				result.add(card.getBuilding());
			}
		}
		return result;
	}
	
	public int cardsToFullHand(){
		return Math.max(0, this.handLimit() - this.getHand().size()); 
	}
	
	public boolean isAtHandLimit(){
		return cardsToFullHand() <= 0;
	}
	
	public boolean hasSenator(){
		return hand.contains(new Senator());
	}
	
	//MARK getters & setters
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CardSet<Card> getHand() {
		return hand;
	}

	public void setHand(CardSet<Card> hand) {
		this.hand = hand;
	}

	//MARK toString
	@Override
	public String toString() {
		return "[Player] name=" + this.getName() + "\n\thand=" + this.getHand();
	}

			
}
