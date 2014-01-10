package com.orion.gar.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteDeck {

	protected final static Logger logger = LoggerFactory.getLogger("SiteDeck.class");

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

	public boolean isAvailable(Material material, SiteType mode){
		boolean result = false;
		for (SiteCard site : sites.keySet()){
			if (site.isSameMaterial(material)){
				if (site.getType()==SiteType.CITY){
					result = true;
					break;
				} else{
					if (mode == SiteType.OUTSKIRTS){
						result = true;
						break;
					}
				}
			}
		}
		return result;
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
