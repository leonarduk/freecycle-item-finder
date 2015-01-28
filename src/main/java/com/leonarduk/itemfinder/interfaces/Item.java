/**
 *
 */
package com.leonarduk.itemfinder.interfaces;

import java.util.Date;

/**
 *
 *
 *
 * @author Stephen Leonard
 * @since 28 Jan 2015
 *
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 *
 */
public interface Item {

	/**
	 * The Enum Condition.
	 */
	public enum Condition {

		/** The new. */
		NEW,
		/** The used. */
		USED,
		/** The refurbished. */
		REFURBISHED,
		/** The broken. */
		BROKEN,
		/** The unknown. */
		UNKNOWN
	}

	/**
	 * Gets the condition.
	 *
	 * @return the condition
	 */
	public Condition getCondition();

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription();

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink();

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * Gets the posted date.
	 *
	 * @return the posted date
	 */
	Date getPostedDate();

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public double getPrice();

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity();
}
