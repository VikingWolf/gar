package com.orion.gar.model;

public enum Role {

	LABORER("laborer"),
	ARCHITECT("architect"),
	CRAFTSMAN("craftsman"),
	LEGIONARY("legionary"),
	PATRON("patron"),
	MERCHANT("merchant");	

	private String role;
	
	private Role(final String role){
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
