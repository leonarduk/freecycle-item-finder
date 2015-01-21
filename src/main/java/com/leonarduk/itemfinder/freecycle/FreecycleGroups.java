package com.leonarduk.itemfinder.freecycle;

public enum FreecycleGroups {
	kingston("freecycle-kingston"), elmbridge("Elmbridge_Freecycle"), richmond(
			"richmonduponthamesfreecycle"), wandsworth("WandsworthUK"), merton(
			"mertonfreecycle"), hammersmith("hammersmithandfulhamfreecycle"), epsom(
			"Epsom"), ealing("EalingUK");
	private String url;

	private FreecycleGroups(String url) {
		this.url = url;
	}

	public String url() {
		return this.url;
	}
}
