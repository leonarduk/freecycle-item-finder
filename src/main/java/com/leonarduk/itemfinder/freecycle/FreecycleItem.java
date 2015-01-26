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

	/**
	 * 
	 * @param condition
	 * @param price
	 * @param link
	 * @param location
	 * @param name
	 * @param quantity
	 * @param extraHtml
	 * @param description
	 */
	public FreecycleItem(Condition condition, double price, String link,
			String location, String name, int quantity, String extraHtml,
			String description) {
		super();
		this.condition = condition;
		this.price = price;
		this.link = link;
		this.location = location;
		this.name = name;
		this.quantity = quantity;
		this.extraHtml = extraHtml;
		this.description = description;
	}

	/**
	 * 
	 * @param link
	 * @param location
	 * @param name
	 * @param extraHtml
	 * @param description
	 */
	public FreecycleItem(String link, String location, String name,
			String extraHtml, String description) {
		this(Condition.USED, 0.0, link, location, name, 1, extraHtml,
				description);
	}

	final private Condition condition;
	// final private FullPost post;

	final private double price;

	final private String link;

	final private String location;
	final private String name;

	final private int quantity;

	final private String extraHtml;

	final private String description;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getCondition()
	 */
	@Override
	public Condition getCondition() {
		return this.condition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.description + this.extraHtml;
	}

	public String getLink() {
		return this.link;
	}

	@Override
	public String getLocation() {
		return this.location;
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
	 * @see com.leonarduk.itemfinder.interfaces.Item#getPrice()
	 */
	@Override
	public double getPrice() {
		return this.price;
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

}
