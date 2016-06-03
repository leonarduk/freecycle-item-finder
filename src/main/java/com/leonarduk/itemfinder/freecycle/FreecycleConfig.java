/**
 * All rights reserved. @Leonard UK Ltd.
 */
package com.leonarduk.itemfinder.freecycle;

import java.io.IOException;

import com.leonarduk.webscraper.core.config.Config;

/**
 * The Class FreecycleConfig.
 *
 * @author stephen
 * @version $Author: $: Author of last commit
 * @version $Rev: $: Revision of last commit
 * @version $Date: $: Date of last commit
 * @since 23 Feb 2015
 */
public class FreecycleConfig {

	/** The Constant FREECYCLE_SEARCH_PERIOD. */
	private static final String FREECYCLE_SEARCH_PERIOD = "freecycle.search.period";

	/** The Constant FREECYCLE_SEARCH_RESULTSPERPAGE. */
	private static final String FREECYCLE_SEARCH_RESULTSPERPAGE = "freecycle.search.resultsperpage";

	/** The Constant FREECYCLE_SEARCH_MAX_LIMIT. */
	private static final String FREECYCLE_SEARCH_MAX_LIMIT = "freecycle.search.limit";

	/** The Constant MAX_RESULTS_PER_PAGE. */
	public static final int MAX_RESULTS_PER_PAGE = 100;

	/** The Constant MIN_RESULTS_PER_PAGE. */
	public static final int MIN_RESULTS_PER_PAGE = 10;

	private static final String FREECYCLE_FROM_EMAIL = "freecycle.email.from.email";

	private static final String FREECYCLE_FROM_NAME = "freecycle.email.from.name";

	private static final String FREECYCLE_TO_EMAIL = "freecycle.email.to";

	private static final String FREECYCLE_EMAIL_USER = "freecycle.email.user";

	private static final String FREECYCLE_EMAIL_SERVER = "freecycle.email.server";

	private static final String FREECYCLE_EMAIL_PASSWORD = "freecycle.email.password";

	private static final String FREECYCLE_EMAIL_PORT = "freecycle.email.port";

	private static final String FREECYCLE_SEARCH_GROUP_NAMES = "freecycle.search.groups";

	public static final String FREECYCLE_SEARCH_TERMS = "freecycle.search.terms";

	private final Config config;

	/**
	 * Instantiates a new freecycle config.
	 */
	public FreecycleConfig() {
		this.config = new Config();
		this.setResultsPerPage(FreecycleConfig.MAX_RESULTS_PER_PAGE);
	}

	/**
	 * Instantiates a new freecycle config.
	 *
	 * @param configFile
	 *            the config file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public FreecycleConfig(final String configFile) throws IOException {
		this.config = new Config(configFile);
		this.setResultsPerPage(FreecycleConfig.MAX_RESULTS_PER_PAGE);
	}

	public final String getEmailPassword() {
		return this.config.getProperty(FreecycleConfig.FREECYCLE_EMAIL_PASSWORD);
	}

	public String getEmailPort() {
		return this.config.getProperty(FreecycleConfig.FREECYCLE_EMAIL_PORT);
	}

	public final String getEmailServer() {
		return this.config.getProperty(FreecycleConfig.FREECYCLE_EMAIL_SERVER);
	}

	public final String getEmailUser() {
		return this.config.getProperty(FreecycleConfig.FREECYCLE_EMAIL_USER);
	}

	public final String getFromEmail() {
		return this.config.getProperty(FreecycleConfig.FREECYCLE_FROM_EMAIL);
	}

	public final String getFromName() {
		return this.config.getProperty(FreecycleConfig.FREECYCLE_FROM_NAME);
	}

	/**
	 * Gets the results per page.
	 *
	 * @return the results per page
	 */
	public final int getResultsPerPage() {
		return this.config.getIntegerProperty(FreecycleConfig.FREECYCLE_SEARCH_RESULTSPERPAGE)
		        .intValue();
	}

	public final String[] getSearchGroupNames() {
		return this.config.getArrayProperty(FreecycleConfig.FREECYCLE_SEARCH_GROUP_NAMES);
	}

	/**
	 * Gets the search item limit.
	 *
	 * @return the search item limit
	 */
	public int getSearchItemLimit() {
		return this.config.getIntegerProperty(FreecycleConfig.FREECYCLE_SEARCH_MAX_LIMIT)
		        .intValue();
	}

	public final int getSearchPeriod() {
		return this.config.getIntegerProperty(FreecycleConfig.FREECYCLE_SEARCH_PERIOD).intValue();
	}

	public String[] getSearchTerms() {
		return this.config.getArrayProperty(FreecycleConfig.FREECYCLE_SEARCH_TERMS);
	}

	public final String[] getToEmail() {
		return this.config.getArrayProperty(FreecycleConfig.FREECYCLE_TO_EMAIL);
	}

	public void setEmailPassword(final String password) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_EMAIL_PASSWORD, password);
	}

	public void setEmailPort(final String port) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_EMAIL_PORT, port);
	}

	public void setEmailServer(final String server) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_EMAIL_SERVER, server);
	}

	public void setEmailUser(final String user) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_EMAIL_USER, user);
	}

	/**
	 * Sets the results per page.
	 *
	 * @param resultsPerPage
	 *            the new results per page
	 */
	public final void setResultsPerPage(final int resultsPerPage) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_SEARCH_RESULTSPERPAGE,
		        String.valueOf(resultsPerPage));
	}

	public void setSearchGroupNames(final String namesCsv) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_SEARCH_GROUP_NAMES, namesCsv);
	}

	/**
	 * Sets the search period.
	 *
	 * @param period
	 *            the new search period
	 */
	public final void setSearchPeriod(final int period) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_SEARCH_PERIOD, String.valueOf(period));
	}

	public void setSearchTerms(final String searchTermsCsv) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_SEARCH_TERMS, searchTermsCsv);
	}

	public void setToEmail(final String toEmailCsv) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_TO_EMAIL, toEmailCsv);
	}

}
