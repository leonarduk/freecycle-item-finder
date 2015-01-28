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

/**
 *
 *
 * @author Stephen Leonard
 * @since 28 Jan 2015
 *
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 *
 */
public class FreecycleScraper {

	static String	                dateFormat	                  = "EEE MMM dd HH:mm:ss yyyy";
	private static final NodeFilter	tableDataContainingLinkFilter	= new AndFilter(
	                                                                      new TagNameFilter("td"),
	                                                                      new HasChildFilter(
	                                                                              new TagNameFilter(
	                                                                                      "a")));

	private static Date parseDateFrom(final Node typeAndDateNode, final SimpleDateFormat dateFormat)
	        throws ParseException {
		final Node dateNode = typeAndDateNode.getChildren().elementAt(4);
		final String dateString = dateNode.toPlainTextString().trim();
		final Date date = dateFormat.parse(dateString);
		return date;
	}

	private final SimpleDateFormat	freecycleDateFormat;
	private final NodeFilter	   itemHeaderFilter	= new TagNameFilter("div");
	Logger	                       log	            = Logger.getLogger(FreecycleScraper.class);
	private final HtmlParser	   parser;

	private final List<Post>	   posts	        = new ArrayList<Post>();

	public FreecycleScraper(final HtmlParser parser) {
		this.log.info(String.format("Instantiated with url=%s, dateFormat=%s", parser.getURL(),
		        FreecycleScraper.dateFormat));
		this.parser = parser;
		this.freecycleDateFormat = new SimpleDateFormat(FreecycleScraper.dateFormat);

	}

	public FreecycleItem getFullPost(final Post post) throws ParserException {
		this.getParser().setURL(post.getLink());
		this.log.info("Extracting details for " + post.getLink());
		final NodeList nodes = this.getParser().parse(this.itemHeaderFilter);
		final String location = nodes.elementAt(16).toPlainTextString().replace("Location :", "");
		final String detail = nodes.elementAt(18).toPlainTextString().replace("Description  ", "")
		        .trim();
		this.getParser().setURL(post.getLink());

		final NodeList thumbnailNodes = this.getParser().extractAllNodesThatMatch(
		        new TagNameFilter("img"));
		final SimpleNodeIterator iter = thumbnailNodes.elements();
		final StringBuilder imagesBuilder = new StringBuilder();
		while (iter.hasMoreNodes()) {
			imagesBuilder.append(iter.nextNode().toHtml());
		}
		final FreecycleItem details = new FreecycleItem(post.getLink(), location, post.getText(),
		        imagesBuilder.toString(), detail, post.getDate());

		return details;
	}

	/**
	 *
	 * @return
	 * @throws ParserException
	 */
	private NodeList getHTMLNodes() throws ParserException {
		this.log.info("Extracting HTML nodes");
		this.parser.setURL(this.parser.getURL());
		return this.parser.extractAllNodesThatMatch(FreecycleScraper.tableDataContainingLinkFilter);
	}

	protected HtmlParser getParser() {
		return this.parser;
	}

	public List<Post> getPosts() {

		this.log.info(String.format("Fetching posts"));
		this.posts.clear();

		try {
			final NodeList list = this.getHTMLNodes();
			this.log.info(String.format("Parsed %s matching HTML nodes", list.size()));

			final SimpleNodeIterator iterator = list.elements();
			while (iterator.hasMoreNodes()) {
				try {
					final Post post = this.parsePostFromHTMLNodes(iterator);
					this.log.debug(post.toString());
					this.posts.add(0, post);
				}
				catch (final ParseException e) {
					this.log.error("Unable to parse HTML node into a post", e);
				}
			}
		}
		catch (final ParserException e) {
			this.log.error("Unable to retrieve HTML nodes", e);
		}

		this.log.info(String.format("Returning %s posts", this.posts.size()));
		return this.posts;
	}

	private Post parsePostFromHTMLNodes(final SimpleNodeIterator iterator) throws ParseException {
		final Node typeAndDateNode = iterator.nextNode();
		final PostType postType = PostType.parse(typeAndDateNode);
		final Date postDate = FreecycleScraper.parseDateFrom(typeAndDateNode,
		        this.freecycleDateFormat);

		final Node linkAndDescriptionNode = iterator.nextNode();
		final String description = linkAndDescriptionNode.getChildren().elementAt(1)
		        .toPlainTextString();
		final String link = ((TagNode) linkAndDescriptionNode.getChildren().elementAt(1))
		        .getAttribute("href");

		return new Post(postType, postDate, description, link);
	}

}
