package com.leonarduk.itemfinder.freecycle;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;
import org.junit.Before;
import org.junit.Test;

import com.adamshone.freecycle.Post;
import com.adamshone.freecycle.PostType;
import com.leonarduk.freecycle.FreecycleItemScraper;
import com.leonarduk.itemfinder.ItemFinderException;
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
		testClass = new FreecycleItemSearcher();
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.freecycle.FreecycleItemSearcher#findItems(java.lang.String)}
	 * .
	 * 
	 * @throws ItemFinderException
	 */
	@Test
	public final void testFindItemsString() throws ItemFinderException {
		QueryBuilder queryBuilder = new FreecycleQueryBuilder().setTown(
				FreecycleGroups.kingston).setSearchWords(searchTerm);

		Set<Item> findItems = this.testClass.findItems(queryBuilder);
		System.out.println(findItems);
		assertTrue(findItems.size() > 0);
	}

	@Test
	public final void testFullPost() throws ParserException, IOException {
		QueryBuilder queryBuilder = new FreecycleQueryBuilder().setTown(
				FreecycleGroups.kingston).setSearchWords(searchTerm);
		Parser parser = queryBuilder.build();

		FreecycleItemScraper scraper = new FreecycleItemScraper(parser);
		Post post = new Post(
				PostType.OFFER,
				new Date(),
				"sofa",
				"https://groups.freecycle.org/group/freecycle-kingston/posts/45010383/leather%203%20seater%20sofa%20");
		FreecycleItem fullPost = new FreecycleItem(scraper.getFullPost(post));
		System.out.println(fullPost);

	}
}
