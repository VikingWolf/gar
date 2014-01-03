package com.orion.gar.model;

public enum SiteType {

	CITY("city"),
	OUTSKIRTS("outskirts");
	
	private String name;
	
	private SiteType(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
