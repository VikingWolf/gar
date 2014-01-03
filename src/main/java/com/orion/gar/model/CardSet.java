package com.orion.gar.model;

import java.util.ArrayList;

public class CardSet<T> extends ArrayList<T> {

	private static final long serialVersionUID = -5762247081507249390L;

	public CardSet() {
		super();
	}

	@Override
	public String toString() {
		String result = "CardSet=\n";
		for (T item : this){
			result +="\t" + item + "\n";
		}
		return result;
	}
	
	
		
}
