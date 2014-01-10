package com.orion.gar.model;

import java.util.ArrayList;
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

	private CardSet<Card> limbo;

	private List<Senator> senators;

	private int initialPlayer = 0;

	private int currentPlayer = 0;

	private int turnCounter = 0;

	private Role leadingRole;

	public Game(int players){
		super();		
		players = Math.min(players, MAX_PLAYERS);
		pool = new CardSet<ActionCard>();
		limbo = new CardSet<Card>();
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

	public void playCard(Card card){
		this.getCurrentPlayer().playCard(card);
		limbo.add(card);
	}

	public void chooseFirstPlayer(List<GameAction> options){
		ActionCard initialCard = null;		
		for (int i = 0; i < options.size(); i++){
			ChooseAction option = (ChooseAction) options.get(i);
			ActionCard played = (ActionCard)option.getCard();
			if (players.get(i).getHand().contains(played)) {
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
				players.get(i).getHand().remove(played);				
			}else{
				logger.error("ERROR GRAVE: carta no hallada en la mano");
			}
		}
		currentPlayer = initialPlayer;
		turnCounter++;
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

	/* game restrictions */
	public boolean canLead(Player player){
		return player.getHand().size()==0;
	}

	public boolean canFollow(Player player){
		boolean result = false;
		return result;
	}

	public List<GameAction> firstPlayerBidOptions(){
		List<GameAction> options = new ArrayList<GameAction>();
		for (Card card : players.get(this.currentPlayer).getHand()){
			options.add(new ChooseAction(card));
		}
		return options;
	}

	public List<GameAction> getPlayerOptions(){
		List<GameAction> actions = new ArrayList<GameAction>();
		actions.add(new ThinkAction(ThinkSubtype.DRAW_FROM_DECK));
		if (this.senatorsAvailable()){
			actions.add(new ThinkAction(ThinkSubtype.TAKE_SENATOR));
		}
		if (isFirstPlayerTurn()){
			/* LEADING OPTIONS */
			actions.addAll(leadingOptions());

		} else{
			/* FOLLOWING OPTIONS */
			actions.addAll(followingOptions());
		}
		return actions;
	}

	private List<GameAction> findPairs(Role role, ActionType actionType){
		List<GameAction> actions = new ArrayList<GameAction>();
		List<ActionCard> candidates = getCurrentPlayer().getCardsMatching(role);
		logger.trace("role: " + role +", candidates="+candidates);
		if (candidates.size()>1){
			for (int i = 0; i < candidates.size()-1; i++){
				for (int k = i+1; k < candidates.size(); k++){
					PlayAction action = new PlayAction(actionType, role, candidates.get(i));
					action.addCards(candidates.get(k));
					actions.add(action);
					logger.trace("i,k=" +i +","+k);
				}
			}
		}
		return actions;
	}

	private List<GameAction> followingOptions(){
		List<GameAction> actions = new ArrayList<GameAction>();
		for (Card card : this.getCurrentPlayer().getHand()){
			/* senators */
			if (card.getClass().equals(Senator.class)){
				actions.add(new PlayAction(ActionType.FOLLOW, this.getLeadingRole(), card));
			} else if (card.getClass().equals(ActionCard.class)){
				/* roles */
				ActionCard actionCard = (ActionCard) card;
				if (actionCard.getRole().equals(this.getLeadingRole())){
					actions.add(new PlayAction(ActionType.FOLLOW, this.getLeadingRole(), card));					
				}
			}
		}
		/* pairs */
		actions.addAll(findPairs(this.getLeadingRole(), ActionType.FOLLOW));
		return actions;
	}

	private List<GameAction> leadingOptions(){
		List<GameAction> actions = new ArrayList<GameAction>();
		for (Card card : this.getCurrentPlayer().getHand()){
			if (card.getClass().equals(Senator.class)){
				/* senators */
				for (Role role : Role.values()){
					actions.add(new PlayAction(ActionType.LEAD, role, card));
				}
			} else if (card.getClass().equals(ActionCard.class)){
				/* roles */
				ActionCard actionCard = (ActionCard) card;
				actions.add(new PlayAction(ActionType.LEAD, actionCard.getRole(), actionCard));
				if (getCurrentPlayer().hasEffect(GameEffect.MERCHANT_JOKER)){
					for (Role role : Role.values()){
						actions.add(new PlayAction(ActionType.LEAD, role, card));
					}					
				}
			}			
		}

		/* pairs */
		for (Role role : Role.values()){
			List<ActionCard> candidates = getCurrentPlayer().getCardsMatching(role);
			logger.trace("role: " + role +", candidates="+candidates);
			actions.addAll(findPairs(role, ActionType.LEAD));
		}				
		return actions;
	}

	public List<GameAction> getActionOptions(Player player){
		List<GameAction> actions = new ArrayList<GameAction>();
		actions.add(new PassAction());
		for (Card card : player.getPlayed()){
			actions.add(new PlayAction(ActionType.ROLE, this.getLeadingRole(), card));
		}
		for (ActionCard client : player.getClients()){
			if (client.getRole().equals(this.getLeadingRole())){
				actions.add(new PlayAction(ActionType.ROLE, this.getLeadingRole(), client));
			}
		}
		return actions;
	}

	public void endTurn(){
		for (Card card : limbo){
			if (card.getClass().equals(ActionCard.class)){
				pool.add((ActionCard)card);
			} else if (card.getClass().equals(SenatorCard.class)){
				senators.add((Senator)card);
			}
		}
		this.leadingRole = null;
		this.currentPlayer = -1;
	}

	public void nextTurn(){
		setNextInitialPlayer();
		this.turnCounter++;
	}

	public List<GameAction> getActionOptions(Player player, GameAction action){
		List<GameAction> options = new ArrayList<GameAction>();
		if (this.getLeadingRole().equals(Role.ARCHITECT)){
			if (player.getWarehouse().size()>0){

			}
			for (Card card : player.getHand()){
				if (card.getClass().equals(ActionCard.class)){
					ActionCard actionCard = (ActionCard) card;
					if (sites.isAvailable(actionCard.getMaterial(), SiteType.CITY)){
						options.add(new PlayAction(ActionType.CHOOSE_CARD, this.getLeadingRole(), actionCard));						
					}
				}
			}
		} else if (this.getLeadingRole().equals(Role.CRAFTSMAN)){

		}
		return options;
	}

	/* status transitions */
	private void setNextInitialPlayer(){
		/* aka end of turn */
		this.initialPlayer = (this.initialPlayer + 1) % players.size();
		this.currentPlayer = initialPlayer;
	}

	public void nextPlayer(){
		if (!this.isTurnEnded()){
			int candidate = (this.currentPlayer +1 ) % players.size();
			if (candidate==initialPlayer) this.currentPlayer = -1;
			else this.currentPlayer = candidate;
		}
	}

	/* status flags */
	public boolean isFirstPlayerTurn(){
		return this.initialPlayer == this.currentPlayer;
	}

	public boolean isTurnEnded(){
		return currentPlayer == -1;
	}

	public boolean senatorsAvailable(){
		return senators.size()>0;
	}

	public boolean islastPlayer(){
		logger.error("currentPlayer: " + this.currentPlayer);
		return this.currentPlayer == players.size()-1;
	}

	public void processActions(List<GameAction> actions){
		for (GameAction action : actions){
			logger.error("action="+action);
			action.execute(this);
			if ((action.getClass().equals(ThinkAction.class)) && (this.isFirstPlayerTurn())){
				this.endTurn();
			}
		}
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

	public int getDeckSize(){
		return deck.size();
	}

	public Role getLeadingRole() {
		return leadingRole;
	}

	public void setLeadingRole(Role leadingRole) {
		this.leadingRole = leadingRole;
	}

	/* return players in order */
	public List<Player> getPlayers() {
		return players;
	}

	public List<Player> getActivePlayers(){
		List<Player> orderedPlayers = new ArrayList<Player>();
		for (int i = initialPlayer; i < players.size(); i++){
			orderedPlayers.add(players.get(i));			
		}
		for (int i = 0; i < initialPlayer; i++){
			orderedPlayers.add(players.get(i));
		}
		return orderedPlayers;
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
