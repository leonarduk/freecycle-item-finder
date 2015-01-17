/**
 * 
 */
package com.leonarduk.itemfinder.freecycle;

import com.leonarduk.freecycle.FullPost;
import com.leonarduk.itemfinder.interfaces.Item;

/**
 * @author stephen
 *
 */
public class FreecycleItem implements Item {

	final private double price;
	final private Condition condition;
	final private FullPost post;

	public FreecycleItem(FullPost post) {
		this.post = post;
		this.condition = Condition.USED;
		this.price = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getQuantity()
	 */
	@Override
	public int getQuantity() {
		return this.post.getQuantity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getPrice()
	 */
	@Override
	public double getPrice() {
		return this.price;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getName()
	 */
	@Override
	public String getName() {
		return this.post.getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.post.getDescription();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getCondition()
	 */
	@Override
	public Condition getCondition() {
		return this.condition;
	}

	@Override
	public String toString() {
		return "FreecycleItem [name=" + getName() + ", quantity="
				+ getQuantity() + ", price=" + price + ", condition="
				+ condition + ", description=" + getDescription() + ", link="
				+ getLink() + "]";
	}

	public String getLink() {
		return this.post.getLink();
	}

	public FullPost getPost() {
		return this.post;
	}
}
