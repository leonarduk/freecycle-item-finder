/**
 *
 */
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

// TODO: Auto-generated Javadoc
/**
 * The Class FreecycleScraper.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 28 Jan 2015
 */
public class FreecycleScraper {

	/** The date format. */
	public static final String	    DATE_FORMAT	      = "EEE MMM dd HH:mm:ss yyyy";

	/** The Constant tableDataContainingLinkFilter. */
	private static final NodeFilter	TABLE_LINK_FILTER	= new AndFilter(new TagNameFilter("td"),
	                                                          new HasChildFilter(new TagNameFilter(
	                                                                  "a")));

	/**
	 * Parses the date from.
	 *
	 * @param typeAndDateNode
	 *            the type and date node
	 * @param dateFormatValue
	 *            the date format
	 * @return the date
	 * @throws ParseException
	 *             the parse exception
	 */
	private static Date parseDateFrom(final Node typeAndDateNode,
	        final SimpleDateFormat dateFormatValue) throws ParseException {
		final Node dateNode = typeAndDateNode.getChildren().elementAt(4);
		final String dateString = dateNode.toPlainTextString().trim();
		final Date date = dateFormatValue.parse(dateString);
		return date;
	}

	/** The freecycle date format. */
	private final SimpleDateFormat	freecycleDateFormat;

	/** The item header filter. */
	private final NodeFilter	   itemHeaderFilter	= new TagNameFilter("div");

	/** The log. */
	private static final Logger	   LOG	            = Logger.getLogger(FreecycleScraper.class);

	/** The parser. */
	private final HtmlParser	   parser;

	/** The posts. */
	private final List<Post>	   posts	        = new ArrayList<Post>();

	/**
	 * Instantiates a new freecycle scraper.
	 *
	 * @param parserInstance
	 *            the parser
	 */
	public FreecycleScraper(final HtmlParser parserInstance) {
		FreecycleScraper.LOG.info(String.format("Instantiated with url=%s, dateFormat=%s",
		        parserInstance.getURL(), FreecycleScraper.DATE_FORMAT));
		this.parser = parserInstance;
		this.freecycleDateFormat = new SimpleDateFormat(FreecycleScraper.DATE_FORMAT);

	}

	/**
	 * Gets the full post.
	 *
	 * @param post
	 *            the post
	 * @return the full post
	 * @throws ParserException
	 *             the parser exception
	 */
	public final FreecycleItem getFullPost(final Post post) throws ParserException {
		final int locationNode = 16;
		final int detailsNode = 18;
		this.getParser().setURL(post.getLink());
		FreecycleScraper.LOG.info("Extracting details for " + post.getLink());
		final NodeList nodes = this.getParser().parse(this.itemHeaderFilter);

		final String location = nodes.elementAt(locationNode).toPlainTextString()
		        .replace("Location :", "");
		final String detail = nodes.elementAt(detailsNode).toPlainTextString()
		        .replace("Description  ", "").trim();
		this.getParser().setURL(post.getLink());

		final NodeList thumbnailNodes = this.getParser().extractAllNodesThatMatch(
		        new TagNameFilter("img"));
		final SimpleNodeIterator iter = thumbnailNodes.elements();
		final StringBuilder imagesBuilder = new StringBuilder();
		while (iter.hasMoreNodes()) {
			imagesBuilder.append(iter.nextNode().toHtml());
		}
		final String htmlToSkip = "<img src=\"\" alt=\"The Freecycle Network\""
		        + " title=\"The Freecycle Network\" height=\"169\" width=\"360\" class=\"CToWUd\">";

		final FreecycleItem details = new FreecycleItem(post.getLink(), location, post.getText(),
		        imagesBuilder.toString().replaceAll(htmlToSkip, ""), detail, post.getDate());

		return details;
	}

	/**
	 * Gets the HTML nodes.
	 *
	 * @return the HTML nodes
	 * @throws ParserException
	 *             the parser exception
	 */
	private NodeList getHTMLNodes() throws ParserException {
		FreecycleScraper.LOG.info("Extracting HTML nodes");
		this.parser.setURL(this.parser.getURL());
		return this.parser.extractAllNodesThatMatch(FreecycleScraper.TABLE_LINK_FILTER);
	}

	/**
	 * Gets the parser.
	 *
	 * @return the parser
	 */
	protected final HtmlParser getParser() {
		return this.parser;
	}

	/**
	 * Gets the posts.
	 *
	 * @return the posts
	 */
	public final List<Post> getPosts() {

		FreecycleScraper.LOG.info(String.format("Fetching posts"));
		this.posts.clear();

		try {
			final NodeList list = this.getHTMLNodes();
			FreecycleScraper.LOG.info(String.format("Parsed %s matching HTML nodes", list.size()));

			final SimpleNodeIterator iterator = list.elements();
			while (iterator.hasMoreNodes()) {
				try {
					final Post post = this.parsePostFromHTMLNodes(iterator);
					FreecycleScraper.LOG.debug(post.toString());
					this.posts.add(0, post);
				}
				catch (final ParseException e) {
					FreecycleScraper.LOG.error("Unable to parse HTML node into a post", e);
				}
			}
		}
		catch (final ParserException e) {
			FreecycleScraper.LOG.error("Unable to retrieve HTML nodes", e);
		}

		FreecycleScraper.LOG.info(String.format("Returning %s posts", this.posts.size()));
		return this.posts;
	}

	/**
	 * Parses the post from html nodes.
	 *
	 * @param iterator
	 *            the iterator
	 * @return the post
	 * @throws ParseException
	 *             the parse exception
	 */
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