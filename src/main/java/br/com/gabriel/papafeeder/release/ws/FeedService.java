package br.com.gabriel.papafeeder.release.ws;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FeedService {
	
	/**
	 * Tags XML
	 */
	private static final String XML_TAG_ITEM        = "item",
								XML_TAG_TITLE       = "title",
								XML_TAG_LINK        = "link",
								XML_TAG_DESCRIPTION = "description";	
	
	/**
	 * Tags e atributos HTML
	 */
	private static final String HTML_TAG_BODY  = "body",
	                            HTML_TAG_P     = "p",
	                            HTML_TAG_DIV   = "div",
	                            HTML_TAG_IMG   = "img",
                        		HTML_TAG_UL    = "ul",
	                            HTML_TAG_LI    = "li",                        		
	                            HTML_ATTR_SRC  = "src",
	                            HTML_ATTR_HREF = "href",
	                            HTML_TAG_A     = "a";
	
	
	/**
	 * Tipo do Content
	 */
	private static final String TYPE_TEXT = "text",
							    TYPE_IMAGE = "image",
	                            TYPE_LINKS = "links";

	public String getJsonFeed(Document doc)
	{		
		Elements tagsItem = doc.select(XML_TAG_ITEM);
		ArrayList<Item> listItem = new ArrayList<>();
		
		for(Element elemItem: tagsItem)
		{
			Item item = new Item();
			
			Elements elemchilds = elemItem.select(XML_TAG_TITLE);
			if(elemchilds.size() > 0)
				item.setTitle(clearText(elemchilds.first().text()));
			
			elemchilds = elemItem.select(XML_TAG_LINK);
			if(elemchilds.size() > 0)
				item.setLink(clearText(elemchilds.first().text()));
			
			elemchilds = elemItem.select(XML_TAG_DESCRIPTION);
			if(elemchilds.size() > 0)
			{
				Element elemDesc = Jsoup.parse(elemchilds.first().text());
				elemchilds = elemDesc.select(HTML_TAG_BODY);
				
				ArrayList<ContentBase> listDescription = new ArrayList<>();
				
				for(Element elem: elemchilds.first().children())
				{
					String strTag = null;
					
					switch(elem.tag().getName().toLowerCase()) {
						case HTML_TAG_P:
							strTag = clearText(elem.toString());
							if(StringUtil.isBlank(strTag))
								continue;
							
							ContentText desc = new ContentText();
							desc.setType(TYPE_TEXT);
							desc.setContent(strTag);
							listDescription.add(desc);
							
							break;
							
						case HTML_TAG_DIV:
							ArrayList<String> listLink = new ArrayList<>();
							for(Element elemDivChild: elem.children())
							{
								switch (elemDivChild.tag().getName().toLowerCase())
								{
									case HTML_TAG_IMG:
										strTag = elemDivChild.attr(HTML_ATTR_SRC);
										if(StringUtil.isBlank(strTag))
											continue;
										
										desc = new ContentText();
										desc.setType(TYPE_IMAGE);
										desc.setContent(strTag);									
										listDescription.add(desc);
										
										break;
										
									case HTML_TAG_UL:
										Elements elemLinks = elemDivChild.select(HTML_TAG_LI);
										for(Element elemLink: elemLinks) {
											strTag = elemLink.select(HTML_TAG_A).attr(HTML_ATTR_HREF);
											if(StringUtil.isBlank(strTag))
												continue;
											listLink.add(strTag);
										}
										
										if(!listLink.isEmpty()) {
											ContentList descList = new ContentList();
											descList.setType(TYPE_LINKS);
											descList.setContent(listLink);									
											listDescription.add(descList);
										}
										break;
									
									default:
										break;
								}
							}
						break;
						
						default:
							break;
					}
				}
				
				if(!listDescription.isEmpty())
					item.setDescription(listDescription);
			}
			listItem.add(item);
		}
		
		Feed feed = new Feed();
		feed.setItem(listItem);
		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
		String strJson = gson.toJson(feed);
		System.out.println(strJson);
		return strJson;
	}
	
	public String clearText(String str) {
		return Jsoup.parse(str).text();
	}
	
}
