package tooling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class UniqueList<T> extends ArrayList<T> implements Set<T> {

	private static final long serialVersionUID = -6825263724431459776L;

	public UniqueList() {
		super();
	}

	@Override
	public T set(int index, T element) {
		int indexOf = this.indexOf(element);
		if (indexOf==-1){
			return super.set(index, element);
		} else if (indexOf == index){			
			return super.set(index, element);
		} else{
			return null;
		}
	}

	@Override
	public boolean add(T element) {
		if (!this.contains(element)){
			return super.add(element);
		} else return false;
	}

	@Override
	public void add(int index, T element) {
		if (!this.contains(element)){
			super.add(index, element);
		} 
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean result = false;
		for (T element : c){
			result |= this.add(element);
		}
		return result;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		boolean result = false;
		for (T element : c){
			result |= !this.contains(c);
			this.add(index, element);
		}
		return result;
	}
}
