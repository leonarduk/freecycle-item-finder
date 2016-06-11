/**
 * HtmlParser
 *
 * @author ${author}
 * @since 08-Jun-2016
 */
package com.leonarduk.itemfinder.html;

import org.htmlparser.NodeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public interface HtmlParser {
	public NodeList extractAllNodesThatMatch(final NodeFilter filter) throws ParserException;

	public String getURL();

	public NodeList parse(final NodeFilter filter) throws ParserException;

	public void setURL(final String url) throws ParserException;

}
