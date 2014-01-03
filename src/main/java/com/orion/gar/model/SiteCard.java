package com.orion.gar.model;

public class SiteCard {

	public final static int CITY_SITE			=	1;
	public final static int OUTSKIRTS_SITE		=	2;
	
	private Material material;
	
	private SiteType type;

	public SiteCard(final Material material, final SiteType type) {
		super();
		this.material = material;
		this.type = type;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(final Material material) {
		this.material = material;
	}
	
	public Integer getPoints(){
		return this.material.getValue();
	}

	public SiteType getType() {
		return type;
	}

	public void setType(SiteType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SiteCard [material=" + material + ", type=" + type.getName() + "]";
	}

}
