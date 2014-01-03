package com.orion.gar.model;

import java.util.Arrays;
import java.util.List;

public class Senator extends Card{
	
	final String name = "Senador";

	public String getName() {
		return name;
	}

	@Override
	public List<Role> leadOptions(){	
		return Arrays.asList(Role.values());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Senator other = (Senator) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Senator [id=" + getId() + "]";
	}
	
	
	
}
