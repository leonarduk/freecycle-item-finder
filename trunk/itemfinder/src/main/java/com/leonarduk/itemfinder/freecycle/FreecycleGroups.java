/**
 *
 */
package com.leonarduk.itemfinder.freecycle;

/**
 * The Enum FreecycleGroups.
 */
public enum FreecycleGroups {

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
	ealing("EalingUK");

	/** The url. */
	private String	url;

	/**
	 * Instantiates a new freecycle groups.
	 *
	 * @param url
	 *            the url
	 */
	private FreecycleGroups(final String url) {
		this.url = url;
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
