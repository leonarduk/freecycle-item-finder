/**
 * 
 */
package com.leonarduk.itemfinder.freecycle;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.adamshone.freecycle.Post;
import com.adamshone.freecycle.PostType;
import com.leonarduk.freecycle.FullPost;
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
	private String link;
	private String location;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.quantity = 2;
		this.price = 12.45;
		this.name = "testitem";
		this.description = "nice item";
		this.condition = Condition.REFURBISHED;
		this.link = "https://groups.freecycle.org/group/freecycle-kingston/posts/44926473";

		this.testClass = new FreecycleItem(new FullPost(this.location,
				this.description, new Post(PostType.OFFER, new Date(),
						this.name, this.link), ""));
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
