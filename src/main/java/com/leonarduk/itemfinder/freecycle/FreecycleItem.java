/**
 *
 */
package com.leonarduk.itemfinder.freecycle;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
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
	private Condition condition;

	/** The created date. */
	@Basic
	private Date createdDate;

	/** The description. */
	@Column(columnDefinition = "character varying (500) not null", length = 500, nullable = false)
	private String description;

	/** The extra html. */
	@Column(columnDefinition = "character varying (500) not null", length = 500, nullable = false)
	private String extraHtml;

	/** The link. */
	@Id
	private String link;

	/** The location. */
	@Basic
	private String location;

	/** The name. */
	@Basic
	private String name;

	/** The price. */
	@Basic
	private double price;

	/** The quantity. */
	@Basic
	private int quantity;

	/**
	 * Instantiates a new freecycle item.
	 */
	protected FreecycleItem() {
		// for JPA
	}

	/**
	 * Instantiates a new freecycle item.
	 *
	 * @param linkValue
	 *            the link
	 * @param locationValue
	 *            the location
	 * @param nameValue
	 *            the name
	 * @param quantityValue
	 *            the quantity
	 * @param extraHtmlValue
	 *            the extra html
	 * @param descriptionValue
	 *            the description
	 * @param createdDateValue
	 *            the created date
	 */
	public FreecycleItem(final String linkValue, final String locationValue,
			final String nameValue, final int quantityValue, final String extraHtmlValue,
			final String descriptionValue, final Date createdDateValue) {
		super();
		this.createdDate = createdDateValue;
		this.condition = Condition.USED;
		this.price = 0;
		this.link = linkValue;
		this.location = locationValue;
		this.name = nameValue;
		this.quantity = quantityValue;
		this.extraHtml = this.enforceMaxLength(extraHtmlValue, 500);
		this.description = this.enforceMaxLength(descriptionValue, 500);
	}

	/**
	 * Instantiates a new freecycle item.
	 *
	 * @param linkValue
	 *            the link
	 * @param locationValue
	 *            the location
	 * @param nameValue
	 *            the name
	 * @param extraHtmlValue
	 *            the extra html
	 * @param descriptionValue
	 *            the description
	 * @param date
	 *            the date
	 */
	public FreecycleItem(final String linkValue, final String locationValue,
			final String nameValue, final String extraHtmlValue, final String descriptionValue,
			final Date date) {
		this(linkValue, locationValue, nameValue, 1, extraHtmlValue, descriptionValue, date);
	}

	private String enforceMaxLength(final String value, final int length) {
		if (value.length() <= length) {
			return value;
		}
		return value.substring(0, length - 1);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.interfaces.Item#getCondition()
	 */
	@Override
	public final Condition getCondition() {
		return this.condition;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public final Date getCreatedDate() {
		return this.createdDate;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.interfaces.Item#getDescription()
	 */
	@Override
	public final String getDescription() {
		return this.description;
	}

	/**
	 * Gets the extra html.
	 *
	 * @return the extra html
	 */
	@Override
	public final String getExtraHtml() {
		return this.extraHtml;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.interfaces.Item#getLink()
	 */
	@Override
	public final String getLink() {
		return this.link;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.interfaces.Item#getLocation()
	 */
	@Override
	public final String getLocation() {
		return this.location;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.interfaces.Item#getName()
	 */
	@Override
	public final String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.interfaces.Item#getPostedDate()
	 */
	@Override
	public final Date getPostedDate() {
		return this.createdDate;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.interfaces.Item#getPrice()
	 */
	@Override
	public final double getPrice() {
		return this.price;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.interfaces.Item#getQuantity()
	 */
	@Override
	public final int getQuantity() {
		return this.quantity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return "FreecycleItem [condition=" + this.condition + ", price=" + this.price + ", link="
				+ this.link + ", location=" + this.location + ", name=" + this.name + ", quantity="
				+ this.quantity + ", extraHtml=" + this.extraHtml + ", description="
				+ this.description + "]";
	}
}
