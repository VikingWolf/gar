package com.orion.model;

import java.util.ArrayList;
import java.util.List;


public class Deck<T> extends CardSet<T> {

	public T draw(){
		T result;
		result = this.get(size()-1);
		removeTop();
		return result;
	}
	
	public void removeTop(){
		this.remove(size()-1);		
	}
	
	public void shuffle(){
		List<T> shuffled = new ArrayList<T>();		
		while (!this.isEmpty()) {
			int random = (int) (Math.random() * this.size());
			shuffled.add(this.get(random));
			this.remove(random);
		}
		this.setBody(shuffled);
	}

}
