/**
 *
 */
package com.leonarduk.itemfinder.freecycle;

import org.htmlparser.Node;

/**
 * The Enum PostType.
 */
public enum PostType {

	/** The offer. */
	OFFER, /** The wanted. */
	WANTED, /** The unknown. */
	UNKNOWN;

	/** The Constant OFFER_STRING. */
	private static final String OFFER_STRING = "OFFER";

	/** The Constant WANTED_STRING. */
	private static final String WANTED_STRING = "WANTED";

	/**
	 * Parses the.
	 *
	 * @param typeAndDateNode
	 *            the type and date node
	 * @return the post type
	 */
	public static PostType parse(final Node typeAndDateNode) {
		final Node typeLink = typeAndDateNode.getChildren().elementAt(1);
		final String typeLinkString = typeLink.toHtml();

		if (typeLinkString.contains(PostType.WANTED_STRING)) {
			return WANTED;
		}
		else if (typeLinkString.contains(PostType.OFFER_STRING)) {
			return OFFER;
		}
		else {
			return UNKNOWN;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.name();
	}
}
