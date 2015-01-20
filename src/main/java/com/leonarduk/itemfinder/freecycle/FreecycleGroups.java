package com.leonarduk.itemfinder.freecycle;

/**
 * Ealing, Elmbridge, Epsom, Hammersmith and Fulham, Kingston upon Thames,
 * Merton, Richmond upon Thames, Wandsworth
 */

public enum FreecycleGroups {
	kingston("freecycle-kingston"), elmbridge("Elmbridge_Freecycle");
	private String url;

	private FreecycleGroups(String url) {
		this.url = url;
	}

	public String url() {
		return this.url;
	}
}
