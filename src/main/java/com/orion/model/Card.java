package com.orion.model;

import java.util.List;

public abstract class Card {
	
	private static Integer ID_POINTER = 0;

	private Integer id;
	
	public Card(){
		ID_POINTER++;
		this.id = ID_POINTER;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public abstract List<Role> leadOptions();

}
