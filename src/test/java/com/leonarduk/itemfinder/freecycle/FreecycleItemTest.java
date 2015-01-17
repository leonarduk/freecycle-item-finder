/**
 * 
 */
package com.leonarduk.itemfinder.freecycle;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.leonarduk.itemfinder.interfaces.Item.Condition;

/**
 * @author stephen
 *
 */
public class FreecycleItemTest {

	private FreecycleItem testClass;
	private int quantity;
	private double price;
	private String name;
	private String description;
	private Condition condition;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.testClass = new FreecycleItem();
		this.quantity = 2;
		this.price = 12.45;
		this.name = "testitem";
		this.description = "nice item";
		this.condition = Condition.REFURBISHED;
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.freecycle.FreecycleItem#getQuantity()}.
	 */
	@Test
	public final void testGetQuantity() {
		assertEquals(this.quantity, this.testClass.getQuantity());
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.freecycle.FreecycleItem#getPrice()}.
	 */
	@Test
	public final void testGetPrice() {
		assertEquals(this.price, this.testClass.getPrice(), 0);
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.freecycle.FreecycleItem#getName()}.
	 */
	@Test
	public final void testGetName() {
		assertEquals(this.name, this.testClass.getName());
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.freecycle.FreecycleItem#getDescription()}
	 * .
	 */
	@Test
	public final void testGetDescription() {
		assertEquals(this.description, this.testClass.getDescription());
	}

	/**
	 * Test method for
	 * {@link com.leonarduk.itemfinder.freecycle.FreecycleItem#getCondition()}.
	 */
	@Test
	public final void testGetCondition() {
		assertEquals(this.condition, this.testClass.getCondition());
	}

}
