package com.orion.gar.model;

import java.util.Arrays;
import java.util.List;

public class SenatorCard extends Card {

	
	@Override
	public List<Role> leadOptions() {
		return Arrays.asList(Role.values());
	}

	@Override
	public String toString() {
		return "SenatorCard []";
	}
	
}
