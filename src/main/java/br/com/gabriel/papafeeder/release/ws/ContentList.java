package br.com.gabriel.papafeeder.release.ws;

import java.util.List;

public class ContentList extends ContentBase{
	private List<String> content;
	
	public ContentList() {}

	public List<String> getContent() {
		return content;
	}

	public void setContent(List<String> content) {
		this.content = content;
	}

		
}