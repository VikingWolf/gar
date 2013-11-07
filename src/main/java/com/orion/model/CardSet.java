package com.orion.model;

import java.util.ArrayList;
import java.util.List;

public class CardSet<T> {

	private List<T> body;

	public CardSet() {
		super();
		body = new ArrayList<T>();
	}
	
	public int size(){
		return this.body.size();
	}
	
	public boolean isEmpty(){
		return this.body.isEmpty();
	}
	
	public void remove(int index){
		this.body.remove(index);
	}
	
	public boolean remove(T item){
		return this.remove(item);
	}
	
	public T get(int index){
		return this.body.get(index);
	}

	public List<T> getBody() {
		return body;
	}

	public void setBody(List<T> body) {
		this.body = body;
	}
	
	public void add(T item){
		this.body.add(item);
	}
	
	public boolean contains(T item){
		return this.body.contains(item);
	}

	@Override
	public String toString() {
		String result = "CardSet=\n";
		for (T item : body){
			result += "\t" + item.toString() + "\n";
		}
		return result;
	}

}
