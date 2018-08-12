package com.wipro.lbg.services;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wipro.lbg.model.Crawler;

/**
 * @author Bibhuti
 *
 *         The SimpleWebCrawler to discover the crawled links
 * 
 */
public class SimpleWebCrawler {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleWebCrawler.class);
	// To filter only the .com URL
	private static final String regx = "(http|https)://(\\w+\\.)+(com)";
	private List<Crawler> crawlerList;
	private Set<String> links;
	private int count = 0;
	private Crawler crawler = null;

	/**
	 * SimpleWebCrawler
	 * 
	 * Initialized links
	 */
	public SimpleWebCrawler() {
		links = new HashSet<String>();
		
		/* 
		 * 1. LinkedList internally uses doubly linked list to store the elements.
		 * 2. Manipulation with LinkedList is faster than ArrayList because it uses doubly linked list so no bit shifting is required in memory.
		 * 3. LinkedList class can act as a list and queue both because it implements List and Deque interfaces.
		 * 4. LinkedList is better for manipulating data.
		 * 
		 */
		crawlerList = new LinkedList<Crawler>();  
	}

	/**
	 * @param URL
	 */
	public void discoverLinks(String URL) {

		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(URL);

		// 1. Filter till 100 URL
		if (links.size() > 100) {
			return;
		}

		// 4.(i) Check if you have already crawled the URLs
		// & validate which not follow the links to external sites such as
		// Google or Twitter.
		if (!links.contains(URL) && !(URL.contains("twitter") || URL.contains("google")) && matcher.find()) {
			try {
				// 2. Fetch the HTML code
				Document document = Jsoup.connect(URL).timeout(5000).get();

				// 3. Parse the HTML to extract links to other URLs
				Elements linksOnPage = document.select("a[href]");

				// 4. (ii) If not add it to the index
				if (links.add(URL)) {
					crawler = new Crawler();
					crawler.setId("" + ++count);
					crawler.setUrl(URL);
					crawler.setTitle(document.title());
					if (null != document.body()) {
						crawler.setBody(document.body().text());
					}
					crawlerList.add(crawler);

					LOGGER.debug("URL {} -- {}", count, URL);
				}
				// 5. For each extracted URL... go back to Step 4.
				for (Element page : linksOnPage) {
					discoverLinks(page.attr("abs:href"));
				}
			} catch (IOException ex) {
				LOGGER.error("For '" + URL + "': {} ", ex);
			}
		}
	}

	/**
	 * @return
	 */
	public List<Crawler> getCrawledList() {
		return crawlerList;
	}
}