/**
 * 
 */
package com.leonarduk.itemfinder.freecycle;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.leonarduk.itemfinder.ItemFinderException;
import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.itemfinder.interfaces.ItemSearcher;

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
		this.searchTerm ="Quinny";
		testClass = new FreecycleItemSearcher("kingston");
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.freecycle.FreecycleItemSearcher#findItems(java.lang.String)}
	 * .
	 * @throws ItemFinderException 
	 */
	@Test
	public final void testFindItemsString() throws ItemFinderException {
		List<Item> findItems = this.testClass.findItems(searchTerm);
		System.out.println(findItems);
		assertTrue(findItems.size() > 0);
	}

}
