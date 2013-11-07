package com.orion.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Building {
	
	private String name;
	
	private Map<GameEffect, GameTiming> effects;
	
	public Building(final String name, final GameEffect effect, final GameTiming timing){
		super();
		this.name = name;
		this.effects = new HashMap<GameEffect, GameTiming>();
		effects.put(effect, timing);
	}
	
	public Building(final String name, final GameEffect[] effects, final GameTiming[] timing){
		super();
		this.name = name;
		this.effects = new HashMap<GameEffect, GameTiming>();
		for (int i = 0; i < (Math.min(effects.length, timing.length)); i++){
			this.effects.put(effects[i], timing[i]);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<GameEffect> getEffects(){
		List<GameEffect> result = new ArrayList<GameEffect>();
		result.addAll(effects.keySet());
		return result;
	}

	@Override
	public String toString() {
		return "Building [name=" + name + ", effects=" + effects + "]";
	}
	
}
