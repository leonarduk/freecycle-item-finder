package com.leonarduk.itemfinder.freecycle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

import com.leonarduk.itemfinder.html.HtmlParser;

public class FreecycleScraper {

	static String dateFormat = "EEE MMM dd HH:mm:ss yyyy";
	private static final NodeFilter tableDataContainingLinkFilter = new AndFilter(
			new TagNameFilter("td"), new HasChildFilter(new TagNameFilter("a")));

	private static Date parseDateFrom(Node typeAndDateNode,
			SimpleDateFormat dateFormat) throws ParseException {
		Node dateNode = typeAndDateNode.getChildren().elementAt(4);
		String dateString = dateNode.toPlainTextString().trim();
		Date date = dateFormat.parse(dateString);
		return date;
	}

	private final SimpleDateFormat freecycleDateFormat;
	private NodeFilter itemHeaderFilter = new TagNameFilter("div");
	Logger log = Logger.getLogger(FreecycleScraper.class);
	private final HtmlParser parser;

	private final List<Post> posts = new ArrayList<Post>();

	public FreecycleScraper(HtmlParser parser) {
		log.info(String.format("Instantiated with url=%s, dateFormat=%s",
				parser.getURL(), dateFormat));
		this.parser = parser;
		this.freecycleDateFormat = new SimpleDateFormat(dateFormat);

	}

	public FreecycleItem getFullPost(Post post) throws ParserException {
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
		FreecycleItem details = new FreecycleItem(post.getLink(), location,
				post.getText(), imagesBuilder.toString(), detail);

		return details;
	}

	private NodeList getHTMLNodes() throws ParserException {
		log.info("Extracting HTML nodes");
		parser.setURL(parser.getURL());
		return parser.extractAllNodesThatMatch(tableDataContainingLinkFilter);
	}

	protected HtmlParser getParser() {
		return parser;
	}

	public List<Post> getPosts() {

		log.info(String.format("Fetching posts"));
		posts.clear();

		try {
			NodeList list = getHTMLNodes();
			log.info(String.format("Parsed %s matching HTML nodes", list.size()));

			SimpleNodeIterator iterator = list.elements();
			while (iterator.hasMoreNodes()) {
				try {
					Post post = parsePostFromHTMLNodes(iterator);
					log.debug(post.toString());
					posts.add(0, post);
				} catch (ParseException e) {
					log.error("Unable to parse HTML node into a post", e);
				}
			}
		} catch (ParserException e) {
			log.error("Unable to retrieve HTML nodes", e);
		}

		log.info(String.format("Returning %s posts", posts.size()));
		return posts;
	}

	private Post parsePostFromHTMLNodes(SimpleNodeIterator iterator)
			throws ParseException {
		Node typeAndDateNode = iterator.nextNode();
		PostType postType = PostType.parse(typeAndDateNode);
		Date postDate = parseDateFrom(typeAndDateNode, freecycleDateFormat);

		Node linkAndDescriptionNode = iterator.nextNode();
		String description = linkAndDescriptionNode.getChildren().elementAt(1)
				.toPlainTextString();
		String link = ((TagNode) linkAndDescriptionNode.getChildren()
				.elementAt(1)).getAttribute("href");

		return new Post(postType, postDate, description, link);
	}

}
