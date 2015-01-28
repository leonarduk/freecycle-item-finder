/**
 *
 */
package com.leonarduk.itemfinder.freecycle;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.leonarduk.itemfinder.interfaces.Item;

/**
 * The Class FreecycleItem.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 28 Jan 2015
 */
@Entity
@Table(name = "FreecycleItem")
public class FreecycleItem implements Item {

	/** The condition. */
	@Basic
	private Condition	condition;

	/** The price. */
	@Basic
	private double	  price;

	/** The created date. */
	@Basic
	private Date	  createdDate;

	/** The link. */
	@Id
	private String	  link;

	/** The location. */
	@Basic
	private String	  location;

	/** The name. */
	@Basic
	private String	  name;

	/** The quantity. */
	@Basic
	private int	      quantity;

	/** The extra html. */
	@Basic
	private String	  extraHtml;

	/** The description. */
	@Basic
	private String	  description;

	/**
	 * Instantiates a new freecycle item.
	 */
	protected FreecycleItem() {
		// for JPA
	}

	/**
	 * Instantiates a new freecycle item.
	 *
	 * @param condition
	 *            the condition
	 * @param price
	 *            the price
	 * @param link
	 *            the link
	 * @param location
	 *            the location
	 * @param name
	 *            the name
	 * @param quantity
	 *            the quantity
	 * @param extraHtml
	 *            the extra html
	 * @param description
	 *            the description
	 * @param createdDate
	 *            the created date
	 */
	public FreecycleItem(final Condition condition, final double price, final String link,
	        final String location, final String name, final int quantity, final String extraHtml,
	        final String description, final Date createdDate) {
		super();
		this.createdDate = createdDate;
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
	 * Instantiates a new freecycle item.
	 *
	 * @param link
	 *            the link
	 * @param location
	 *            the location
	 * @param name
	 *            the name
	 * @param extraHtml
	 *            the extra html
	 * @param description
	 *            the description
	 * @param date
	 *            the date
	 */
	public FreecycleItem(final String link, final String location, final String name,
	        final String extraHtml, final String description, final Date date) {
		this(Condition.USED, 0.0, link, location, name, 1, extraHtml, description, date);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.description + this.extraHtml;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getLink()
	 */
	@Override
	public String getLink() {
		return this.link;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getLocation()
	 */
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
	 * @see com.leonarduk.itemfinder.interfaces.Item#getPostedDate()
	 */
	@Override
	public Date getPostedDate() {
		return this.createdDate;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FreecycleItem [condition=" + this.condition + ", price=" + this.price + ", link="
		        + this.link + ", location=" + this.location + ", name=" + this.name + ", quantity="
		        + this.quantity + ", extraHtml=" + this.extraHtml + ", description="
		        + this.description + "]";
	}
}
