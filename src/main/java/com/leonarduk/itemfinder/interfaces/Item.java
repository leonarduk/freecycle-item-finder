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
	enum Condition {

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
	Condition getCondition();

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	String getDescription();

	/**
	 * Gets the extra html.
	 *
	 * @return the extra html
	 */
	String getExtraHtml();

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	String getLink();

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	String getLocation();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();

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
	double getPrice();

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	int getQuantity();
}
