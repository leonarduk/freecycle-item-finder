/**
 * 
 */
package com.leonarduk.itemfinder.freecycle;

import com.leonarduk.itemfinder.interfaces.Item;

/**
 * @author stephen
 *
 */
public class FreecycleItem implements Item {

	private String name;
	private int quantity;
	private double price;
	private Condition condition;
	private String description;
	private String link;

	public FreecycleItem(String name, int quantity, double price,
			Condition condition, String description, String link) {
		this.name = name;
		this.quantity = quantity;
		this.description = description;
		this.condition = condition;
		this.price = price;

		this.link = link;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getQuantity()
	 */
	@Override
	public int getQuantity() {
		return this.quantity;
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
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.description;
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
		return "FreecycleItem [name=" + name + ", quantity=" + quantity
				+ ", price=" + price + ", condition=" + condition
				+ ", description=" + description + ", link=" + link + "]";
	}

	public String getLink() {
		return this.link;
	}
}
