package com.orion.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tooling.UniqueList;

public class Game {

	protected final static Logger logger = LoggerFactory.getLogger("Game.class");
	
	private final static int MAX_PLAYERS 		=	5;	
	private final static int INITIAL_DRAW 		=	5;
	private final static int SENATORS			=	6;
	
	private PlayDeck deck;
	
	private List<Player> players;
	
	private SiteDeck sites;
	
	private CardSet<ActionCard> pool;
	
	private List<Senator> senators;
			
	private int initialPlayer = 0;
	
	private int currentPlayer = 0;
	
	private int turnCounter = 0;
	
	private Role leadingRole;
	
	private List<Card> inPlayCards;
	
	public Game(int players){
		super();		
		players = Math.min(players, MAX_PLAYERS);
		pool = new CardSet<ActionCard>();
		inPlayCards = new ArrayList<Card>();
		deck = new PlayDeck();
		setUpSenators();
		sites = new SiteDeck(players);
		deck.shuffle();
		setUpPlayers(players);
	}
	
	/* game setting up */	
	private void setUpSenators(){
		senators = new ArrayList<Senator>();
		for (int i = 0; i < SENATORS; i++){
			senators.add(new Senator());
		}
	}
	
	private void setUpPlayers(final int players){
		this.players = new ArrayList<Player>();
		for (int i = 0; i < players; i++){
			Player player = new Player(i);
			player.drawCards(this.deck, INITIAL_DRAW);
			this.players.add(player);
		}
	}	
	
	public void giveSenators(){
		for (Player player : this.players){			
			player.getHand().add(senators.get(senators.size()-1));
			senators.remove(senators.size()-1);
		}
	}
	
	
	/* game actions */
	
	public void chooseFirstPlayer(Integer... cardIndices){
		ActionCard initialCard = null;
		for (int i = 0; i < players.size(); i++){
			Player player = players.get(i);
			ActionCard played = (ActionCard) player.getCardAt(cardIndices[i]);
			if (initialCard == null){
				initialCard = played;
				initialPlayer = i;
			} else {
				if (played.isLowerThan(initialCard)){
					initialCard = played;
					initialPlayer = i;
				}
			}
			pool.add(played);
			player.getHand().remove(played);
		}
	}
	
	/* if hand size < hand limit draw till hand limit
	 * otherwise draw 1 */

	public int playerThinks(){
		int drawn = 0;
		if (getCurrentPlayer().isAtHandLimit()){
			drawn = 1;
		} else{
			drawn = getCurrentPlayer().cardsToFullHand();
		}
		getCurrentPlayer().drawCards(deck, drawn);
		return drawn;
	}
	
	public void playerTakesSenator(){
		getCurrentPlayer().getHand().add(senators.get(senators.size()-1));
		senators.remove(senators.size()-1);
	}
	
	public void lead(final Role role, final Card... cards){
		for (Card card : cards){
			inPlayCards.add(card);
		}
		this.leadingRole = role;
		this.nextPlayer();
	}
	
	public void follow(final Card... cards){
		for (Card card : cards){
			inPlayCards.add(card);
		}
		this.nextPlayer();		
	}
			
	/* game restrictions */
	public boolean canLead(Player player){
		return player.getHand().size()==0;
	}
	
	public boolean canFollow(Player player){
		boolean result = false;
		return result;
	}
	
	public CardSet<Card> playerInitialOptions(){
		return players.get(this.currentPlayer).getHand();
	}

	
	/* status transitions */
	public void setNextInitialPlayer(){
		/* aka end of turn */
		this.initialPlayer = (this.initialPlayer + 1) % players.size();
		this.currentPlayer = initialPlayer;
		this.turnCounter++;
	}
	
	public boolean nextPlayer(){
		boolean result = false;
		this.currentPlayer = this.currentPlayer +1;
		if (currentPlayer>=players.size()){
			currentPlayer = 0;
			result = false;
		}
		return result;
	}
	
	/* status flags */
	public boolean playingFirstPlayer(){
		return this.initialPlayer == this.currentPlayer;
	}
	
	public boolean isEnded(){
		return false;
	}
	
	public boolean senatorsAvailable(){
		return senators.size()>0;
	}
	
	public boolean notLastPlayer(){
		return this.currentPlayer == players.size()-1;
	}
	
	//MARK utilities
	public static UniqueList<Role> leadingOptions(List<Card> cards){
		UniqueList<Role> leadingOptions = new UniqueList<Role>();
		for (Card card : cards){
			leadingOptions.addAll(card.leadOptions());
		}
		return leadingOptions;
	}
	
	//MARK monitoring
	public String monitorPool(){
		return this.pool.toString();
	}
	
	public String monitorPlayers(){
		String result = "";
		for (Player player : players){
			result += player.toString() + "\n";
		}
		return result;
	}

	//MARK getters & setters
	public CardSet<ActionCard> getPool() {
		return pool;
	}
	
	public void setInitialPlayer(Player player){
		this.initialPlayer = players.indexOf(player);
		this.currentPlayer = initialPlayer;
	}
		
	public Player getInitialPlayer(){
		return players.get(this.initialPlayer);
	}

	public Player getCurrentPlayer(){
		return players.get(this.currentPlayer);
	}

	public int getTurnCounter() {
		return turnCounter;
	}

	public void setTurnCounter(int turnCounter) {
		this.turnCounter = turnCounter;
	}
	
	public int getDeckSize(){
		return deck.size();
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public SiteDeck getSites() {
		return sites;
	}

	public void setSites(SiteDeck sites) {
		this.sites = sites;
	}
	
	/** Secuencia de juego 
	 * 1		Preparación
	 * 1.1		Reparto inicial de cartas
	 * 1.2		Cada jugador apuesta una carta para jugador inicial
	 * 1.3		Escoger jugador inicial
	 * 1.4		Reparto senadores
	 * 2		Turno
	 * 2.1		Avanzar jugador inicial
	 * 2.2		Jugador inicial: liderar / pensar
	 * 2.2		Cada jugador seguir / pensar
	 * 2.3		Cada jugador realizar acciones
	 * 2.4		Gestionar descartes (al pool, reserva de senadores, almacen, etc)
	 */
}
