/**
 *
 */
package com.leonarduk.itemfinder.freecycle;

/**
 * The Enum FreecycleGroups.
 */
public enum FreecycleGroup {

	/** The kingston. */
	kingston("freecycle-kingston"),
	/** The elmbridge. */
	elmbridge("Elmbridge_Freecycle"),
	/** The richmond. */
	richmond("richmonduponthamesfreecycle"),
	/** The wandsworth. */
	wandsworth("WandsworthUK"),
	/** The merton. */
	merton("mertonfreecycle"),
	/** The hammersmith. */
	hammersmith("hammersmithandfulhamfreecycle"),
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
