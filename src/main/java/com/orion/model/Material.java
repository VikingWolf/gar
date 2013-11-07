package com.orion.model;

public enum Material {

	WOOD("wood", 1),
	RUBBLE("rubble", 1),
	BRICK("brick", 2),
	CONCRETE("concrete", 2),
	STONE("stone", 3),
	MARBLE("marble", 3);
	
	private String name;
	
	private Integer value;
	
	private Material(final String name, final Integer value){
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
