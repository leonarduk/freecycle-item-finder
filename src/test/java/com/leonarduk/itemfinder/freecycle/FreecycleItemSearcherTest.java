package com.leonarduk.itemfinder.freecycle;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import javax.persistence.EntityManager;

import org.htmlparser.util.ParserException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.leonarduk.itemfinder.ItemFinderException;
import com.leonarduk.itemfinder.html.HtmlParser;
import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.itemfinder.interfaces.ItemSearcher;
import com.leonarduk.itemfinder.query.QueryBuilder;

/**
 * @author stephen
 *
 */
public class FreecycleItemSearcherTest {

	private ItemSearcher testClass;
	private String searchTerm;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.searchTerm = "";
		EntityManager em = mock(EntityManager.class);
		testClass = new FreecycleItemSearcher(em);
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.freecycle.FreecycleItemSearcher#findItems(java.lang.String)}
	 * .
	 * 
	 * @throws ItemFinderException
	 */
	@Test
	@Ignore
	public final void testFindItemsString() throws ItemFinderException {
		QueryBuilder queryBuilder = new FreecycleQueryBuilder().setTown(
				FreecycleGroups.kingston).setSearchWords(searchTerm);

		Set<Item> findItems = this.testClass.findItems(queryBuilder);
		System.out.println(findItems);
		assertTrue(findItems.size() > 0);
	}

	@Ignore
	@Test
	public final void testFullPost() throws ParserException, IOException {
		QueryBuilder queryBuilder = new FreecycleQueryBuilder().setTown(
				FreecycleGroups.kingston).setSearchWords(searchTerm);
		HtmlParser parser = queryBuilder.build();

		FreecycleScraper scraper = new FreecycleScraper(parser);
		Post post = new Post(
				PostType.OFFER,
				new Date(),
				"sofa",
				"https://groups.freecycle.org/group/freecycle-kingston/posts/45010383/leather%203%20seater%20sofa%20");
		FreecycleItem fullPost = scraper.getFullPost(post);
		System.out.println(fullPost);

	}

	@Test
	public void testIncludePost() throws Exception {
		EntityManager em = mock(EntityManager.class);

		FreecycleItemSearcher searcher = new FreecycleItemSearcher(em);
		QueryBuilder queryBuilder = new FreecycleQueryBuilder()
				.setSearchWords("Pram");
		FreecycleItem fullPost = new FreecycleItem(
				"http://",
				"Thames Ditton",
				"3 wheel pram",
				"<hr>",
				"Mothercare mychoice 3 wheel pram, green, good working order, comes with a rain cover and small handy pump for the tyres, can be used frombirth up to 4 years, Maxicosi car seat can be used with it.",
				new Date());
		assertTrue(searcher.includePost(queryBuilder, fullPost));

	}
}
