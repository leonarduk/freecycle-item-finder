/**
 * 
 */
package com.leonarduk.itemfinder.html;

import static org.junit.Assert.*;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.junit.Before;
import org.junit.Test;

/**
 * @author stephen
 *
 */
public class HtmlParserTest {
	private String fullPost;
	private String list;
	private HtmlParser listparser;
	private HtmlParser fullpostparser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.list = ClassLoader.getSystemResource("list.html").getPath();
		listparser = new HtmlParser(this.list);
		this.fullPost = ClassLoader.getSystemResource("sofa.html").getPath();
		fullpostparser = new HtmlParser(this.fullPost);
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.html.HtmlParser#HtmlParser(java.lang.String)}
	 * .
	 * 
	 * @throws ParserException
	 */
	@Test
	public void testHtmlParserString() throws ParserException {
		assertNotNull(listparser);
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.html.HtmlParser#extractAllNodesThatMatch(org.htmlparser.NodeFilter)}
	 * .
	 * 
	 * @throws ParserException
	 */
	@Test
	public void testExtractAllNodesThatMatchNodeFilter() throws ParserException {
		NodeFilter filter = new TagNameFilter("img");

		NodeList nodeList = this.fullpostparser
				.extractAllNodesThatMatch(filter);
		assertNotNull(nodeList);

		String expected = "<img src=\"//static.freecycle.org/images/freecycle_logo.jpg\"\n"
				+ "			alt=\"The Freecycle Network\" title=\"The Freecycle Network\"\n"
				+ "			height=\"169\" width=\"360\" />";
		String actual = nodeList.elementAt(0).toHtml();
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link com.leonarduk.itemfinder.html.HtmlParser#getURL()}
	 * .
	 */
	@Test
	public void testGetURL() {
		assertEquals(
				"file://localhost/home/stephen/workspace/luk/trunk/itemfinder/target/test-classes/sofa.html",
				this.fullpostparser.getURL());
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.html.HtmlParser#parse(org.htmlparser.NodeFilter)}
	 * .
	 * 
	 * @throws ParserException
	 */
	@Test
	public void testParseNodeFilter() throws ParserException {
		NodeList footer = this.fullpostparser
				.parse(new TagNameFilter("footer"));
		assertNotNull(footer);
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.html.HtmlParser#setURL(java.lang.String)}
	 * .
	 * 
	 * @throws ParserException
	 */
	@Test
	public void testSetURLString() throws ParserException {
		this.fullpostparser.setURL(this.list);
		assertEquals(
				"file://localhost/home/stephen/workspace/luk/trunk/itemfinder/target/test-classes/list.html",
				this.fullpostparser.getURL());
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.html.HtmlParser#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals(
				"file://localhost/home/stephen/workspace/luk/trunk/itemfinder/target/test-classes/list.html",
				this.listparser.toString());
	}

}
