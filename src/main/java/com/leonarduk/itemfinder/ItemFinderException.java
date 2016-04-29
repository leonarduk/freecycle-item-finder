/**
 *
 */
package com.leonarduk.itemfinder;

/**
 * The Class ItemFinderException.
 */
public class ItemFinderException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 6957476799043277085L;

	/**
	 * Instantiates a new item finder exception.
	 *
	 * @param message
	 *            the message
	 */
	public ItemFinderException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new item finder exception.
	 *
	 * @param message
	 *            the message
	 * @param e
	 *            the e
	 */
	public ItemFinderException(final String message, final Exception e) {
		super(message, e);
	}

}
