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

	/** The Constant MAX_FIELD_SIZE. */
	private static final int MAX_FIELD_SIZE = 500;

	/** The condition. */
	@Basic
	private Condition condition;

	/** The created date. */
	@Basic
	private Date createdDate;

	/** The description. */
	@Column(columnDefinition = "character varying (" + FreecycleItem.MAX_FIELD_SIZE
	        + ") not null", length = FreecycleItem.MAX_FIELD_SIZE, nullable = false)
	private String description;

	/** The extra html. */
	@Column(columnDefinition = "character varying (" + FreecycleItem.MAX_FIELD_SIZE
	        + ") not null", length = FreecycleItem.MAX_FIELD_SIZE, nullable = false)
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
	public FreecycleItem(final String linkValue, final String locationValue, final String nameValue,
	        final int quantityValue, final String extraHtmlValue, final String descriptionValue,
	        final Date createdDateValue) {
		super();
		this.createdDate = createdDateValue;
		this.condition = Condition.USED;
		this.price = 0;
		this.link = linkValue;
		this.location = locationValue;
		this.name = nameValue;
		this.quantity = quantityValue;
		this.extraHtml = this.enforceMaxLength(extraHtmlValue, FreecycleItem.MAX_FIELD_SIZE);
		this.description = this.enforceMaxLength(descriptionValue, FreecycleItem.MAX_FIELD_SIZE);
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
	public FreecycleItem(final String linkValue, final String locationValue, final String nameValue,
	        final String extraHtmlValue, final String descriptionValue, final Date date) {
		this(linkValue, locationValue, nameValue, 1, extraHtmlValue, descriptionValue, date);
	}

	/**
	 * Enforce max length.
	 *
	 * @param value
	 *            the value
	 * @param length
	 *            the length
	 * @return the string
	 */
	private String enforceMaxLength(final String value, final int length) {
		if (value.length() <= length) {
			return value;
		}
		return value.substring(0, length - 1);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final FreecycleItem other = (FreecycleItem) obj;
		if (this.condition != other.condition) {
			return false;
		}
		if (this.createdDate == null) {
			if (other.createdDate != null) {
				return false;
			}
		}
		else if (!this.createdDate.equals(other.createdDate)) {
			return false;
		}
		if (this.description == null) {
			if (other.description != null) {
				return false;
			}
		}
		else if (!this.description.equals(other.description)) {
			return false;
		}
		if (this.extraHtml == null) {
			if (other.extraHtml != null) {
				return false;
			}
		}
		else if (!this.extraHtml.equals(other.extraHtml)) {
			return false;
		}
		if (this.link == null) {
			if (other.link != null) {
				return false;
			}
		}
		else if (!this.link.equals(other.link)) {
			return false;
		}
		if (this.location == null) {
			if (other.location != null) {
				return false;
			}
		}
		else if (!this.location.equals(other.location)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		}
		else if (!this.name.equals(other.name)) {
			return false;
		}
		if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
			return false;
		}
		if (this.quantity != other.quantity) {
			return false;
		}
		return true;
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

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.interfaces.Item#getExtraHtml()
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.condition == null) ? 0 : this.condition.hashCode());
		result = (prime * result) + ((this.createdDate == null) ? 0 : this.createdDate.hashCode());
		result = (prime * result) + ((this.description == null) ? 0 : this.description.hashCode());
		result = (prime * result) + ((this.extraHtml == null) ? 0 : this.extraHtml.hashCode());
		result = (prime * result) + ((this.link == null) ? 0 : this.link.hashCode());
		result = (prime * result) + ((this.location == null) ? 0 : this.location.hashCode());
		result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(this.price);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		result = (prime * result) + this.quantity;
		return result;
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
