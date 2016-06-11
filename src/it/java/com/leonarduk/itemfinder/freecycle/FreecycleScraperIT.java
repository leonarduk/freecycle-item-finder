/**
 * FreecycleScraperIT
 *
 * @author ${author}
 * @since 09-Jun-2016
 */
package com.leonarduk.itemfinder.freecycle;

import java.util.Date;

import org.htmlparser.util.ParserException;
import org.junit.Ignore;
import org.junit.Test;

import com.leonarduk.itemfinder.html.HtmlParser;
import com.leonarduk.itemfinder.html.HtmlParserImpl;

public class FreecycleScraperIT {

	@Ignore
	@Test
	public final void testGetFullPost() throws ParserException {

		final String urlString = "https://groups.freecycle.org/group/hammersmithandfulhamfreecycle/posts/54818262/Offered%20bath%20room%20cabinet";

		final HtmlParser parserInstance = new HtmlParserImpl(urlString);
		final FreecycleGroup group = FreecycleGroup.hammersmith;
		final FreecycleScraper scraper = new FreecycleScraper(parserInstance, group);
		final String titleValue = "";
		final Date postDateValue = new Date();
		final PostType postTypeValue = PostType.OFFER;
		final Post post = new Post(postTypeValue, postDateValue, titleValue, urlString, group);
		final FreecycleItem fullPost = scraper.getFullPost(post);
		System.out.println(fullPost.toString());
	}

}
