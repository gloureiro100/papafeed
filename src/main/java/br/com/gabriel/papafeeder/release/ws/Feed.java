package br.com.gabriel.papafeeder.release.ws;

import java.util.ArrayList;
import java.util.List;

public class Feed {

	private List<Item> item = new ArrayList<>();
	
	public Feed() {}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}	
	
}