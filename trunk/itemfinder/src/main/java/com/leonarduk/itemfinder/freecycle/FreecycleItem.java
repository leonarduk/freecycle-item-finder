/**
 * 
 */
package com.leonarduk.itemfinder.freecycle;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.leonarduk.itemfinder.interfaces.Item;

/**
 * @author stephen
 *
 */
@Entity
@Table(name = "FreecycleItem")
public class FreecycleItem implements Item {
	public FreecycleItem() {
		// for JPA
	}

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

	@Basic
	private Condition condition;

	@Basic
	private double price;

	@Id
	private String link;

	@Basic
	private String location;
	@Basic
	private String name;

	@Basic
	private int quantity;
	@Basic
	private String extraHtml;
	@Basic
	private String description;

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

	@Override
	public String toString() {
		return "FreecycleItem [condition=" + condition + ", price=" + price
				+ ", link=" + link + ", location=" + location + ", name="
				+ name + ", quantity=" + quantity + ", extraHtml=" + extraHtml
				+ ", description=" + description + "]";
	}
}
