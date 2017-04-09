/**
 * FreecycleScraperTest
 *
 * @author ${author}
 * @since 08-Jun-2016
 */
package com.leonarduk.itemfinder.freecycle;

import java.util.Calendar;
import java.util.Date;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.leonarduk.itemfinder.freecycle.db.FreecycleItem;
import com.leonarduk.web.HtmlParser;

public class FreecycleScraperTest {

	private FreecycleScraper	scraper;
	private HtmlParser			parserInstance;

	@Before
	public void setUp() throws Exception {
		this.parserInstance = Mockito.mock(HtmlParser.class);
		Mockito.when(this.parserInstance.getURL()).thenReturn("http://testUrl");
		final FreecycleGroup group = FreecycleGroup.kingston;
		this.scraper = new FreecycleScraper(this.parserInstance, group);
	}

	@Test
	public final void testCleanHtml() {
		final String detailCandidate = "We have a spare Neue undercounter refrigerator, this ad. It is hardly used (1 week only) and in perfect working order. Comes complete with clean white door cover as per pictures. Perfect condition, just looking to sell! "
		        + "Thanks<img src=\"//static.freecycle.org/images/freecycle_logo.jpg\""
		        + "        alt=\"logo of The Freecycle Network\" title=\"The Freecycle Network\" height=\"169\" width=\"360\" /><br/><br/><hr/><br/><h2><a href=\"https://groups.freecycle.org/group/WandsworthUK/posts/54699328/Single%20wardrobe\">Single wardrobe</a> -   Wandsworth common  Date : Thu Jun  2 13:11:28 2016   Posted: Thu Jun 02 13:11:28 BST 2016</h2><br/>IKEA single wardrobe in good condition<img src=\"//static.freecycle.org/images/freecycle_logo.jpg\""
		        + "        alt=\"logo of The Freecycle Network\" title=\"The Freecycle Network\" height=\"169\" width=\"360\" /><br/><br/><hr/><br/><h2><a href=\"https://groups.freecycle.org/group/WandsworthUK/posts/54699344/Single%20wardrobe\">Single wardrobe</a> -   Wandsworth common  Date : Thu Jun  2 13:12:15 2016   Posted: Thu Jun 02 13:12:15 BST 2016</h2><br/>IKEA single wardrobe in good condition<img src=\"//static.freecycle.org/images/freecycle_logo.jpg\""
		        + "        alt=\"logo of The Freecycle Network\" title=\"The Freecycle Network\" height=\"169\" width=\"360\" /><br/><br/><hr/><br/><h2><a href=\"https://groups.freecycle.org/group/WandsworthUK/posts/54701638/Ikea%20Knives%20Tray\">Ikea Knives Tray</a> -   SW18  Date : Thu Jun  2 15:31:16 2016   Posted: Thu Jun 02 15:31:16 BST 2016</h2><br/>I have an Ikea VARIERA Knife tray to give away, high-gloss white size 10x50cm";
		final String actual = this.scraper.cleanHtml(detailCandidate);
		final String expected = "We have a spare Neue undercounter refrigerator, this posting. It is hardly used (1 week only) and in perfect working order. Comes complete with clean white door cover as per pictures. Perfect condition, just looking to sell! Thanks<br/><br/><hr/><br/><h2><a href=\"https://groups.freecycle.org/group/WandsworthUK/posts/54699328/Single%20wardrobe\">Single wardrobe</a> -   Wandsworth common  Date : Thu Jun  2 13:11:28 2016   Posted: Thu Jun 02 13:11:28 BST 2016</h2><br/>IKEA single wardrobe in good condition<br/><br/><hr/><br/><h2><a href=\"https://groups.freecycle.org/group/WandsworthUK/posts/54699344/Single%20wardrobe\">Single wardrobe</a> -   Wandsworth common  Date : Thu Jun  2 13:12:15 2016   Posted: Thu Jun 02 13:12:15 BST 2016</h2><br/>IKEA single wardrobe in good condition"
		        + "<br/><br/><hr/><br/><h2><a href=\"https://groups.freecycle.org/group/WandsworthUK/posts/54701638/Ikea%20Knives%20Tray\">Ikea Knives Tray</a> -   SW18  Date : Thu Jun  2 15:31:16 2016   Posted: Thu Jun 02 15:31:16 BST 2016</h2><br/>I have an Ikea VARIERA Knife tray to give away, high-gloss white size 10x50cm";
		Assert.assertEquals(expected, actual);
	}

	@Test
	public final void testGetFullPostMockedOut() throws ParserException {
		final PostType postTypeValue = PostType.OFFER;
		final Calendar instance = Calendar.getInstance();
		instance.set(2016, 6, 8, 0, 0, 0);
		final Date postDateValue = instance.getTime();

		final NodeList value = Mockito.mock(NodeList.class);
		final Node locationNode = Mockito.mock(Node.class);
		final String locationValue = " TestVille";
		Mockito.when(locationNode.toPlainTextString())
		        .thenReturn(FreecycleScraper.locationString + locationValue);
		Mockito.when(value.elementAt(FreecycleScraper.LOCATION_NODE_INDEX))
		        .thenReturn(locationNode);

		final Node descriptionNode = Mockito.mock(Node.class);
		final String descriptionValue = "Free stuff";
		Mockito.when(descriptionNode.toPlainTextString())
		        .thenReturn(FreecycleScraper.descriptionString + " " + descriptionValue);
		Mockito.when(value.elementAt(FreecycleScraper.LOCATION_NODE_INDEX + 1))
		        .thenReturn(descriptionNode);

		Mockito.when(this.parserInstance.parse(Matchers.any(NodeFilter.class))).thenReturn(value);
		final NodeList imageNodes = Mockito.mock(NodeList.class);
		final SimpleNodeIterator iter = Mockito.mock(SimpleNodeIterator.class);
		Mockito.when(imageNodes.elements()).thenReturn(iter);
		Mockito.when(Boolean.valueOf(iter.hasMoreNodes())).thenReturn(Boolean.FALSE);
		Mockito.when(this.parserInstance.extractAllNodesThatMatch(Matchers.any(NodeFilter.class)))
		        .thenReturn(imageNodes);

		final String titleValue = "gsdgds";
		final String linkValue = "https://groups.freecycle.org/group/hammersmithandfulhamfreecycle/posts/54818262/Offered%20bath%20room%20cabinet";
		final FreecycleGroup group = FreecycleGroup.kingston;
		final Post post = new Post(postTypeValue, postDateValue, titleValue, linkValue, group);
		final FreecycleItem actual = this.scraper.getFullPost(post);
		final String extraHtmlValue = "";
		final FreecycleItem expected = new FreecycleItem(linkValue, locationValue, titleValue,
		        extraHtmlValue, descriptionValue, postDateValue);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public final void testGetParser() {
		Assert.assertEquals(this.parserInstance, this.scraper.getParser());
	}
}
