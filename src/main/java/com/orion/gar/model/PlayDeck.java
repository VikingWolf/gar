package com.orion.gar.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayDeck extends Deck<ActionCard> {

	private static final long serialVersionUID = -4163665300334365836L;
	
	final protected static Logger logger = LoggerFactory.getLogger(PlayDeck.class);
		
	public PlayDeck(){
		super();
		setUpDeck();
	}
	
	/* programmatically initial deck */
	private void setUpDeck(){
		addActionCard("Calzada", GameEffect.ALL_AS_STONE, GameTiming.PERMANENT, Role.LABORER, Material.RUBBLE, 6);
		addActionCard("Ínsula", GameEffect.CLIENTS_PLUS_TWO, GameTiming.PERMANENT, Role.LABORER, Material.RUBBLE, 6);
		addActionCard("Taberna", GameEffect.TABERN, GameTiming.ACTION_PATRON, Role.LABORER, Material.RUBBLE, 6);
		addActionCard("Letrina", GameEffect.DISCARD_ONE, GameTiming.BEFORE_THINK, Role.LABORER, Material.RUBBLE, 6);

		addActionCard("Circo", GameEffect.ALL_AS_ARCHITECT, GameTiming.PERMANENT, Role.CRAFTSMAN, Material.WOOD, 6);
		addActionCard("Empalizada", GameEffect.LEGIONARY_DEFENSE, GameTiming.PERMANENT, Role.CRAFTSMAN, Material.WOOD, 6);
		addActionCard("Mercado", GameEffect.TREASURE_PLUS_TWO, GameTiming.PERMANENT, Role.CRAFTSMAN, Material.WOOD, 6);
		addActionCard("Muelle", GameEffect.LABORER_EXTRA_CARD, GameTiming.ACTION_LABORER, Role.CRAFTSMAN, Material.WOOD, 6);

		addActionCard("Fundición", GameEffect.LABORER_INFLUENCE_TIMES, GameTiming.ON_COMPLETION, Role.LEGIONARY, Material.BRICK, 3);
		addActionCard("Academia", GameEffect.THINK, GameTiming.AFTER_ACTION_CRAFTSMAN, Role.LEGIONARY, Material.BRICK, 3);
		addActionCard("Escuela", GameEffect.THINK_INFLUENCE_TIMES, GameTiming.ON_COMPLETION, Role.LEGIONARY, Material.BRICK, 3);
		addActionCard("Termas", GameEffect.USE_CLIENT, GameTiming.ON_CLIENT_ADD, Role.LEGIONARY, Material.BRICK, 3);
		addActionCard("Arco", GameEffect.ARCHITECT_FROM_POOL, GameTiming.ACTION_ARCHITECT, Role.LEGIONARY, Material.BRICK, 3);
		addActionCard("Atrio", GameEffect.DECK_TO_TREASURE, GameTiming.ACTION_MERCHANT, Role.LEGIONARY, Material.BRICK, 3);
		addActionCard("Portón", GameEffect.MARBLE_UNCOMPLETED_EFFECTS, GameTiming.PERMANENT, Role.LEGIONARY, Material.BRICK, 3);
		addActionCard("Santuario", GameEffect.MAX_HAND_PLUS_TWO, GameTiming.PERMANENT, Role.LEGIONARY, Material.BRICK, 3);

		addActionCard("Acueducto", new GameEffect[]{GameEffect.DOUBLE_CLIENTS, GameEffect.EXTRA_CLIENT}, 
				new GameTiming[]{GameTiming.PERMANENT, GameTiming.AFTER_ACTION_PATRON}, Role.ARCHITECT, Material.CONCRETE, 3);
		addActionCard("Amfiteatro", GameEffect.CRAFTSMAN_INFLUENCE_TIMES, GameTiming.ON_COMPLETION, Role.ARCHITECT, Material.CONCRETE, 3);
		addActionCard("Despensa", GameEffect.ALL_AS_LABORER, GameTiming.PERMANENT, Role.ARCHITECT, Material.CONCRETE, 3);
		
		addActionCard("Muralla", new GameEffect[]{GameEffect.POINTS_BY_RESOURCE, GameEffect.LEGIONARY_DEFENSE, GameEffect.BRIDGE_DEFENSE}, 
				new GameTiming[]{GameTiming.ON_GAME_END, GameTiming.PERMANENT, GameTiming.PERMANENT}, Role.ARCHITECT, Material.CONCRETE, 3);		
		addActionCard("Puente", new GameEffect[]{GameEffect.STEAL_TO_ALL, GameEffect.STEAL_CLIENTS}, 
				new GameTiming[]{GameTiming.ACTION_LEGIONARY, GameTiming.ACTION_LEGIONARY}, Role.ARCHITECT, Material.CONCRETE, 3);
		addActionCard("Senado", GameEffect.TAKE_SENATORS, GameTiming.ON_TURN_END, Role.ARCHITECT, Material.CONCRETE, 3);
		addActionCard("Torre", new GameEffect[]{GameEffect.RUBBLE_JOKER, GameEffect.ONE_ACTION_OUTSKIRTS}, 
				new GameTiming[]{GameTiming.PERMANENT,GameTiming.PERMANENT}, Role.ARCHITECT, Material.CONCRETE, 3);
		addActionCard("Vomitorium", GameEffect.DISCARD_ALL, GameTiming.BEFORE_THINK, Role.ARCHITECT, Material.CONCRETE, 3);
		
		addActionCard("Alcantarillado", GameEffect.SAVE_ROLES, GameTiming.ON_TURN_END, Role.MERCHANT, Material.STONE, 3);
		addActionCard("Biblioteca", GameEffect.MARBLE_JOKER, GameTiming.PERMANENT, Role.MERCHANT, Material.STONE, 3);
		addActionCard("Catacumbas", GameEffect.GAME_ENDS, GameTiming.ON_COMPLETION, Role.MERCHANT, Material.STONE, 3);
		addActionCard("Circo Máximo", GameEffect.DOUBLE_CLIENT_ACTION, GameTiming.ON_ACTION, Role.MERCHANT, Material.STONE, 3);
		addActionCard("Jardín", GameEffect.PATRON_INFLUENCE_TIMES, GameTiming.ON_COMPLETION, Role.MERCHANT, Material.STONE, 3);		
		addActionCard("Prisión", GameEffect.STEAL_BUILDING, GameTiming.ON_COMPLETION, Role.MERCHANT, Material.STONE, 3);
		addActionCard("Villa", GameEffect.VILLE, GameTiming.TO_BUILD, Role.MERCHANT, Material.STONE, 3);
		addActionCard("Coliseo", GameEffect.STEAL_CLIENTS, GameTiming.ACTION_LEGIONARY, Role.MERCHANT, Material.STONE, 3);

		addActionCard("Basilica", GameEffect.MERCHANT_FROM_HAND, GameTiming.ACTION_MERCHANT, Role.PATRON, Material.MARBLE, 3);
		addActionCard("Escalinata", GameEffect.MAKE_PUBLIC_BUILDING, GameTiming.ACTION_ARCHITECT, Role.PATRON, Material.MARBLE, 3);
		addActionCard("Estatua", new GameEffect[]{GameEffect.BUILD_JOKER, GameEffect.THREE_EXTRA_POINTS}, 
				new GameTiming[]{GameTiming.PERMANENT,GameTiming.PERMANENT}, Role.PATRON, Material.MARBLE, 3);
		addActionCard("Foro", GameEffect.RESOURCES_GAME_WIN, GameTiming.ON_COMPLETION, Role.PATRON, Material.MARBLE, 3);		
		addActionCard("Fuente", GameEffect.FOUNTAIN, GameTiming.ACTION_CRAFTSMAN, Role.PATRON, Material.MARBLE, 3);		
		addActionCard("Ludus Magna", GameEffect.MERCHANT_JOKER, GameTiming.PERMANENT, Role.PATRON, Material.MARBLE, 3);		
		addActionCard("Palacio", GameEffect.MULTIPLE_ACTIONCARDS, GameTiming.ON_ACTION, Role.MERCHANT, Material.MARBLE, 3);		
		addActionCard("Templo", GameEffect.MAX_HAND_PLUS_FOUR, GameTiming.PERMANENT, Role.PATRON, Material.MARBLE, 3);
		
	}

	private void addActionCard(final String buildingName, final GameEffect[] effects, final GameTiming[] timings, final Role role, final Material material, 
			final int amount){
		for (int i = 0; i < amount; i++){
			add(new ActionCard(new Building(buildingName, effects, timings), role, material));
		}
	}
	
	private void addActionCard(final String buildingName, final GameEffect effect, final GameTiming timing, final Role role, final Material material, 
			final int amount){
		for (int i = 0; i < amount; i++){
			add(new ActionCard(new Building(buildingName, effect, timing), role, material));
		}
	}
	
}
