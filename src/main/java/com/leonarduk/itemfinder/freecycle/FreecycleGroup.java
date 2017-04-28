/**
 *
 */
package com.leonarduk.itemfinder.freecycle;

/**
 * The Enum FreecycleGroups.
 */
public enum FreecycleGroup {

	/** The kingston. */
	kingston("KingstonUK"),
	/** The elmbridge. */
	elmbridge("ElmbridgeUK"),
	/** The richmond. */
	richmond("richmonduponthamesUK"),
	/** The wandsworth. */
	wandsworth("WandsworthUK"),
	/** The merton. */
	merton("MertonUK"),
	/** The hammersmith. */
	hammersmith("HammersmithandFulhamUK"),
	/** The epsom. */
	epsom("Epsom"),
	/** The ealing. */
	ealing("EalingUK"),
	/** The lambeth. */
	lambeth("LambethUK");

	/** The url. */
	private String url;

	/**
	 * Instantiates a new freecycle groups.
	 *
	 * @param aUrl
	 *            the a url
	 */
	private FreecycleGroup(final String aUrl) {
		this.url = aUrl;
	}

	/**
	 * Url.
	 *
	 * @return the string
	 */
	public String url() {
		return this.url;
	}
}
