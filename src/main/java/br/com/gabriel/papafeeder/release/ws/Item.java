package br.com.gabriel.papafeeder.release.ws;

import java.util.ArrayList;
import java.util.List;

public class Item{
	private String title;
	private String link;
	private List<ContentBase> content = new ArrayList<>();
	
	public Item() {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<ContentBase> getDescription() {
		return content;
	}

	public void setDescription(List<ContentBase> description) {
		this.content = description;
	}

		
}
