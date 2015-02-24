/**
 * All rights reserved. @Leonard UK Ltd
 */

package com.leonarduk.itemfinder.freecycle;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import javax.persistence.EntityManager;

import org.htmlparser.util.ParserException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.leonarduk.itemfinder.ItemFinderException;
import com.leonarduk.itemfinder.html.HtmlParser;
import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.itemfinder.interfaces.ItemSearcher;
import com.leonarduk.itemfinder.query.QueryBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class FreecycleItemSearcherTest.
 *
 * @author stephen
 */
public class FreecycleItemSearcherTest {

	/** The test class. */
	private ItemSearcher testClass;

	/** The search term. */
	private String searchTerm;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public final void setUp() throws Exception {
		this.searchTerm = "bed";
		final EntityManager em = Mockito.mock(EntityManager.class);
		this.testClass = new FreecycleItemSearcher(em);
	}

	/**
	 * Test find items string.
	 *
	 * @throws ItemFinderException
	 *             the item finder exception
	 */
	@Test
	@Ignore
	public final void testFindItemsString() throws ItemFinderException {
		final QueryBuilder queryBuilder = new FreecycleQueryBuilder().setTown(
				FreecycleGroups.kingston).setSearchWords(this.searchTerm);

		final Set<Item> findItems = this.testClass.findItems(queryBuilder);
		System.out.println(findItems);
		Assert.assertTrue(findItems.size() > 0);
	}

	/**
	 * Test full post.
	 *
	 * @throws ParserException
	 *             the parser exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Ignore
	@Test
	public final void testFullPost() throws ParserException, IOException {
		final QueryBuilder queryBuilder = new FreecycleQueryBuilder().setTown(
				FreecycleGroups.kingston).setSearchWords(this.searchTerm);
		final HtmlParser parser = queryBuilder.build();

		final FreecycleScraper scraper = new FreecycleScraper(parser);
		final Post post = new Post(PostType.OFFER, new Date(), "sofa",
				"https://groups.freecycle.org/group/freecycle-kingston/posts/"
		                + "45010383/leather%203%20seater%20sofa%20");
		final FreecycleItem fullPost = scraper.getFullPost(post);
		System.out.println(fullPost);

	}

	/**
	 * Test include post.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public final void testIncludePost() throws Exception {
		final EntityManager em = Mockito.mock(EntityManager.class);

		final FreecycleItemSearcher searcher = new FreecycleItemSearcher(em);
		final QueryBuilder queryBuilder = new FreecycleQueryBuilder().setSearchWords("Pram");
		final FreecycleItem fullPost = new FreecycleItem("http://", "Thames Ditton",
				"3 wheel pram", "<hr>",

				"Mothercare mychoice 3 wheel pram, green, good working order, "
						+ "comes with a rain cover and small handy pump for the tyres,"
						+ " can be used frombirth"
						+ " up to 4 years, Maxicosi car seat can be used with it.", new Date());
		Assert.assertTrue(searcher.includePost(queryBuilder, fullPost));

	}
}
