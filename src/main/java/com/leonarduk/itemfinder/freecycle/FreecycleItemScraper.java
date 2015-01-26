package com.leonarduk.itemfinder.freecycle;

import org.apache.log4j.Logger;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class FreecycleItemScraper extends FreecycleNewhamScraper {

	Logger log = Logger.getLogger(FreecycleItemScraper.class);
	static String dateFormat = "EEE MMM dd HH:mm:ss yyyy";
	private NodeFilter itemHeaderFilter = new TagNameFilter("div");

	public FreecycleItemScraper(Parser parser) {
		super(parser, dateFormat);
	}

	public FullPost getFullPost(Post post) throws ParserException {
		getParser().setURL(post.getLink());
		log.info("Extracting details for " + post.getLink());
		NodeList nodes = getParser().parse(itemHeaderFilter);
		String location = nodes.elementAt(16).toPlainTextString()
				.replace("Location :", "");
		String detail = nodes.elementAt(18).toPlainTextString()
				.replace("Description  ", "").trim();
		getParser().setURL(post.getLink());

		NodeList thumbnailNodes = getParser().extractAllNodesThatMatch(
				new TagNameFilter("img"));
		SimpleNodeIterator iter = thumbnailNodes.elements();
		StringBuilder imagesBuilder = new StringBuilder();
		while (iter.hasMoreNodes()) {
			imagesBuilder.append(iter.nextNode().toHtml());
		}
		FullPost details = new FullPost(location, detail, post,
				imagesBuilder.toString());

		return details;
	}

}
