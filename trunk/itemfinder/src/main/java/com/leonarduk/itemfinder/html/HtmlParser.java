package com.leonarduk.itemfinder.html;

import java.net.HttpURLConnection;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * Decorator class to allow later modifications.
 * 
 *
 * @author Stephen Leonard
 * @since 23 Jan 2015
 *
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 *
 */
public class HtmlParser extends Parser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8969008563666070296L;

	public HtmlParser(HttpURLConnection connection) throws ParserException {
		super(connection);
	}

	public HtmlParser(String url) throws ParserException {
		super(url);
	}

	@Override
	public NodeList extractAllNodesThatMatch(NodeFilter filter)
			throws ParserException {
		return super.extractAllNodesThatMatch(filter);
	}

	@Override
	public String getURL() {
		return super.getURL();
	}

	@Override
	public NodeList parse(final NodeFilter filter) throws ParserException {
		return super.parse(filter);
	}

	@Override
	public void setURL(final String url) throws ParserException {
		super.setURL(url);
	}

	@Override
	public String toString() {
		return getURL();
	}
}
