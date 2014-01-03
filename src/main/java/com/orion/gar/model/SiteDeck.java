package com.orion.gar.model;

import java.util.HashMap;
import java.util.Map;

public class SiteDeck {

	private final static int SITES_BY_CATEGORY	= 6;
	
	private Map<SiteCard, Integer> sites;

	public SiteDeck(int players){
		super();
		sites = new HashMap<SiteCard, Integer>();
		generateSiteDeck(players);
	}
	
	private void generateSiteDeck(final int players){
		for (Material material : Material.values()){
			sites.put(new SiteCard(material, SiteType.CITY), SITES_BY_CATEGORY - players);
			sites.put(new SiteCard(material, SiteType.OUTSKIRTS), players);
		}		
	}

	public SiteCard drawSite(final Material material){
		return null;
	}

	@Override
	public String toString() {
		String result = "SiteDeck=\n";
		for (SiteCard site : sites.keySet()){
			result += "\t" + site.toString() + "\t=\t" + sites.get(site) + "\n";
		}
		return result;
	}
	
}
