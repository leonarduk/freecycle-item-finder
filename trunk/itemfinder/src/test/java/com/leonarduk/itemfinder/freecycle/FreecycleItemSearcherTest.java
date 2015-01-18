/**
 * 
 */
package com.leonarduk.itemfinder.freecycle;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
		this.searchTerm ="phone";
		testClass = new FreecycleItemSearcher("kingston");
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.freecycle.FreecycleItemSearcher#findItems(java.lang.String, double, int)}
	 * .
	 */
	@Test
	public final void testFindItemsStringDoubleInt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.freecycle.FreecycleItemSearcher#findItems(java.lang.String, double, int, java.util.List)}
	 * .
	 */
	@Test
	public final void testFindItemsStringDoubleIntListOfCondition() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.freecycle.FreecycleItemSearcher#findItems(java.lang.String, double, java.util.List)}
	 * .
	 */
	@Test
	public final void testFindItemsStringDoubleListOfCondition() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.freecycle.FreecycleItemSearcher#findItems(java.lang.String, java.util.List)}
	 * .
	 */
	@Test
	public final void testFindItemsStringListOfCondition() {
		fail("Not yet implemented"); // TODO
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
