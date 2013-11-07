package com.orion.model;

import java.util.ArrayList;
import java.util.List;

public class ActionCard extends Card {

	protected Material material;
	
	protected Role role;
	
	protected Building building;
	
	public ActionCard(final Building building, final Role role, final Material material){
		this.building = building;
		this.role = role;
		this.material = material;
	}
	
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public Integer getPoints(){
		return material.getValue();
	}
	
	public boolean isLowerThan(final ActionCard target){
		return this.getBuilding().getName().compareTo(target.getBuilding().getName())<0; 
	}
	
	

	@Override
	public List<Role> leadOptions() {
		List<Role> result = new ArrayList<Role>();
		result.add(role);
		return result;
	}

	@Override
	public String toString() {
		return "ActionCard [id=" + getId() + ", material=" + material + ", role=" + role
				+ ", building=" + building.getName() + "]";
	}

	

}
