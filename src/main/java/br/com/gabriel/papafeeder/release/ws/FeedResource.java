package br.com.gabriel.papafeeder.release.ws;



import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value="/feed")
public class FeedResource {
	
	private static final String FEED_URL =  "https://revistaautoesporte.globo.com/rss/ultimas/feed.xml";
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getFeed()
	{
		try {
			Document doc;
			doc = Jsoup.connect(FEED_URL).get();
			FeedService fs = new FeedService();		
			String response = fs.getJsonFeed(doc);			
			return ResponseEntity.ok().body(response);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
}
