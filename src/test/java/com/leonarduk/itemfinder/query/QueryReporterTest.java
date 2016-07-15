/**
 * All rights reserved. @Leonard UK Ltd.
 */
package com.leonarduk.itemfinder.query;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.leonarduk.itemfinder.freecycle.FreecycleItem;
import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.webscraper.core.format.HtmlFormatter;

/**
 * The Class QueryReporterTest.
 */
public class QueryReporterTest {

	/**
	 * Test add post details.
	 */
	@Test
	@Ignore
	public final void testAddPostDetails() {
		final Item item = new FreecycleItem("http://localhost/page1", "Here", "3 wheel pram",
		        "<hr>", "old pram", new Date(0));
		final String post = new QueryReporter().addPostDetails(new HtmlFormatter(), item);
		Assert.assertEquals("<br/><hr/><br/><h2><a href=\"http://localhost/page1\">"
		        + "3 wheel pram</a> - Here Posted: Thu Jan 01 01:00:00 GMT 1970</h2>"
		        + "<br/>old pram<hr><br/>", post);
	}

	/**
	 * Test get email body.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public final void testGetEmailBody() throws Exception {
		final Set<Item> resultsMap = new HashSet<>();
		new QueryReporter().convertResultsSetToString(resultsMap, new HtmlFormatter());
	}
}
