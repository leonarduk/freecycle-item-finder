/**
 * All rights reserved. @Leonard UK Ltd.
 */

package com.leonarduk.itemfinder.html;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * The Class HtmlParserTest.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 28 Jan 2015
 */
public class HtmlParserTest {

	/** The file path prefix. */
	private static String filePathPrefix;

	/** The full post. */
	private String fullPost;

	/** The list. */
	private String list;

	/** The listparser. */
	private HtmlParser listparser;

	/** The fullpostparser. */
	private HtmlParser fullpostparser;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public final void setUp() throws Exception {
		this.list = ClassLoader.getSystemResource("list.html").getPath();
		this.listparser = new HtmlParser(this.list);
		this.fullPost = ClassLoader.getSystemResource("sofa.html").getPath();
		this.fullpostparser = new HtmlParser(this.fullPost);
		HtmlParserTest.filePathPrefix = "file://localhost" + System.getProperty("user.dir") + "/";
	}

	/**
	 * Test extract all nodes that match node filter.
	 *
	 * @throws ParserException
	 *             the parser exception
	 */
	@Test
	@Ignore
	public final void testExtractAllNodesThatMatchNodeFilter() throws ParserException {
		final NodeFilter filter = new TagNameFilter("img");

		final NodeList nodeList = this.fullpostparser.extractAllNodesThatMatch(filter);
		Assert.assertNotNull(nodeList);

		final String expected = "<img src=\"//static.freecycle.org/images/freecycle_logo.jpg\"\n"
		        + "			alt=\"The Freecycle Network\" title=\"The Freecycle Network\"\n"
		        + "			height=\"169\" width=\"360\" />";
		final String actual = nodeList.elementAt(0).toHtml();
		Assert.assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link com.leonarduk.itemfinder.html.HtmlParser#getURL()} .
	 */
	@Test
	@Ignore
	public final void testGetURL() {
		Assert.assertEquals(HtmlParserTest.filePathPrefix + "target/test-classes/sofa.html",
		        this.fullpostparser.getURL());
	}

	/**
	 * Test method for {@link com.leonarduk.itemfinder.html.HtmlParser#HtmlParser(java.lang.String)}
	 * .
	 *
	 * @throws ParserException
	 *             the parser exception
	 */
	@Test
	public final void testHtmlParserString() throws ParserException {
		Assert.assertNotNull(this.listparser);
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.html.HtmlParser#parse(org.htmlparser.NodeFilter)} .
	 *
	 * @throws ParserException
	 *             the parser exception
	 */
	@Test
	public final void testParseNodeFilter() throws ParserException {
		final NodeList footer = this.fullpostparser.parse(new TagNameFilter("footer"));
		Assert.assertNotNull(footer);
	}

	/**
	 * Test method for {@link com.leonarduk.itemfinder.html.HtmlParser#setURL(java.lang.String)} .
	 *
	 * @throws ParserException
	 *             the parser exception
	 */
	@Test
	@Ignore
	public final void testSetURLString() throws ParserException {
		this.fullpostparser.setURL(this.list);
		Assert.assertEquals(HtmlParserTest.filePathPrefix + "target/test-classes/list.html",
		        this.fullpostparser.getURL());
	}

	/**
	 * Test method for {@link com.leonarduk.itemfinder.html.HtmlParser#toString()}.
	 */
	@Test
	@Ignore
	public final void testToString() {
		Assert.assertEquals(HtmlParserTest.filePathPrefix + "target/test-classes/list.html",
		        this.listparser.toString());
	}

}
