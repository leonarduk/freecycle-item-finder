/**
 * All rights reserved. @Leonard UK Ltd.
 */
package com.leonarduk.itemfinder.freecycle;

import java.io.IOException;
import java.util.Arrays;

import org.apache.log4j.Logger;

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
	private static final int MAX_RESULTS_PER_PAGE = 100;

	/** The Constant MIN_RESULTS_PER_PAGE. */
	public static final int MIN_RESULTS_PER_PAGE = 10;

	/** The Constant FREECYCLE_FROM_EMAIL. */
	private static final String FREECYCLE_FROM_EMAIL = "freecycle.email.from.email";

	/** The Constant FREECYCLE_FROM_NAME. */
	private static final String FREECYCLE_FROM_NAME = "freecycle.email.from.name";

	/** The Constant FREECYCLE_TO_EMAIL. */
	private static final String FREECYCLE_TO_EMAIL = "freecycle.email.to";

	/** The Constant FREECYCLE_EMAIL_USER. */
	private static final String FREECYCLE_EMAIL_USER = "freecycle.email.user";

	/** The Constant FREECYCLE_EMAIL_SERVER. */
	private static final String FREECYCLE_EMAIL_SERVER = "freecycle.email.server";

	/** The Constant FREECYCLE_EMAIL_PASSWORD. */
	private static final String FREECYCLE_EMAIL_PASSWORD = "freecycle.email.password";

	/** The Constant FREECYCLE_EMAIL_PORT. */
	private static final String FREECYCLE_EMAIL_PORT = "freecycle.email.port";

	/** The Constant FREECYCLE_SEARCH_GROUP_NAMES. */
	private static final String FREECYCLE_SEARCH_GROUP_NAMES = "freecycle.search.groups";

	/** The Constant FREECYCLE_SEARCH_TERMS. */
	private static final String FREECYCLE_SEARCH_TERMS = "freecycle.search.terms";

<<<<<<< HEAD
	private static final Logger LOGGER = Logger.getLogger(FreecycleConfig.class);
=======
	static final Logger LOGGER = Logger.getLogger(FreecycleConfig.class);
>>>>>>> branch 'feature/#18-reduce-spaminess-of-email' of https://github.com/leonarduk/freecycle-item-finder.git

	private static final String	FREECYCLE_SEND_HTML_EMAILS	= "freecycle.send.html.emails";
	/** The config. */
	private final Config		config;

	/**
	 * Instantiates a new freecycle config.
	 */
	public FreecycleConfig() {
		this.config = new Config();
		this.setResultsPerPage(FreecycleConfig.MAX_RESULTS_PER_PAGE);
		FreecycleConfig.LOGGER.info(this.toString());
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

	/**
	 * Gets the email password.
	 *
	 * @return the email password
	 */
	public final String getEmailPassword() {
		return this.config.getProperty(FreecycleConfig.FREECYCLE_EMAIL_PASSWORD);
	}

	/**
	 * Gets the email port.
	 *
	 * @return the email port
	 */
	public String getEmailPort() {
		return this.config.getProperty(FreecycleConfig.FREECYCLE_EMAIL_PORT);
	}

	/**
	 * Gets the email server.
	 *
	 * @return the email server
	 */
	public final String getEmailServer() {
		return this.config.getProperty(FreecycleConfig.FREECYCLE_EMAIL_SERVER);
	}

	/**
	 * Gets the email user.
	 *
	 * @return the email user
	 */
	public final String getEmailUser() {
		return this.config.getProperty(FreecycleConfig.FREECYCLE_EMAIL_USER);
	}

	/**
	 * Gets the from email.
	 *
	 * @return the from email
	 */
	public final String getFromEmail() {
		return this.config.getProperty(FreecycleConfig.FREECYCLE_FROM_EMAIL);
	}

	/**
	 * Gets the from name.
	 *
	 * @return the from name
	 */
	public final String getFromName() {
		return this.config.getProperty(FreecycleConfig.FREECYCLE_FROM_NAME);
	}

	/**
	 * Gets the results per page.
	 *
	 * @return the results per page
	 */
	public final Integer getResultsPerPage() {
		return this.config.getIntegerProperty(FreecycleConfig.FREECYCLE_SEARCH_RESULTSPERPAGE);
	}

	/**
	 * Gets the search group names.
	 *
	 * @return the search group names
	 */
	public final String[] getSearchGroupNames() {
		return this.config.getArrayProperty(FreecycleConfig.FREECYCLE_SEARCH_GROUP_NAMES);
	}

	/**
	 * Gets the search item limit.
	 *
	 * @return the search item limit
	 */
	public Integer getSearchItemLimit() {
		return this.config.getIntegerProperty(FreecycleConfig.FREECYCLE_SEARCH_MAX_LIMIT);
	}

	/**
	 * Gets the search period.
	 *
	 * @return the search period
	 */
	public final Integer getSearchPeriod() {
		return this.config.getIntegerProperty(FreecycleConfig.FREECYCLE_SEARCH_PERIOD);
	}

	/**
	 * Gets the search terms.
	 *
	 * @return the search terms
	 */
	public String[] getSearchTerms() {
		return this.config.getArrayProperty(FreecycleConfig.FREECYCLE_SEARCH_TERMS);
	}

	/**
	 * Gets the to email.
	 *
	 * @return the to email
	 */
	public final String[] getToEmail() {
		return this.config.getArrayProperty(FreecycleConfig.FREECYCLE_TO_EMAIL);
	}

	public boolean isSendAsHtml() {
		return this.config.getBooleanProperty(FreecycleConfig.FREECYCLE_SEND_HTML_EMAILS);
	}

	/**
	 * Sets the email password.
	 *
	 * @param password
	 *            the new email password
	 */
	public void setEmailPassword(final String password) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_EMAIL_PASSWORD, password);
	}

	/**
	 * Sets the email port.
	 *
	 * @param port
	 *            the new email port
	 */
	public void setEmailPort(final String port) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_EMAIL_PORT, port);
	}

	/**
	 * Sets the email server.
	 *
	 * @param server
	 *            the new email server
	 */
	public void setEmailServer(final String server) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_EMAIL_SERVER, server);
	}

	/**
	 * Sets the email user.
	 *
	 * @param user
	 *            the new email user
	 */
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

	/**
	 * Sets the search group names.
	 *
	 * @param namesCsv
	 *            the new search group names
	 */
	public void setSearchGroupNames(final String namesCsv) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_SEARCH_GROUP_NAMES, namesCsv);
	}

	public void setSearchItemLimit(final int i) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_SEARCH_MAX_LIMIT, String.valueOf(i));
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

	/**
	 * Sets the search terms.
	 *
	 * @param searchTermsCsv
	 *            the new search terms
	 */
	public void setSearchTerms(final String searchTermsCsv) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_SEARCH_TERMS, searchTermsCsv);
	}

	public void setSendAsHtml(final boolean sendAsHtml) {
		this.config.setBooleanProperty(FreecycleConfig.FREECYCLE_SEND_HTML_EMAILS, sendAsHtml);
	}

	/**
	 * Sets the to email.
	 *
	 * @param toEmailCsv
	 *            the new to email
	 */
	public void setToEmail(final String toEmailCsv) {
		this.config.setProperty(FreecycleConfig.FREECYCLE_TO_EMAIL, toEmailCsv);
	}

	@Override
	public String toString() {
		return "FreecycleConfig [getEmailPassword()=" + this.getEmailPassword()
		        + ", getEmailPort()=" + this.getEmailPort() + ", getEmailServer()="
		        + this.getEmailServer() + ", getEmailUser()=" + this.getEmailUser()
		        + ", getFromEmail()=" + this.getFromEmail() + ", getFromName()="
		        + this.getFromName() + ", getResultsPerPage()=" + this.getResultsPerPage()
		        + ", getSearchGroupNames()=" + Arrays.toString(this.getSearchGroupNames())
		        + ", getSearchItemLimit()=" + this.getSearchItemLimit() + ", getSearchPeriod()="
		        + this.getSearchPeriod() + ", getSearchTerms()="
		        + Arrays.toString(this.getSearchTerms()) + ", getToEmail()="
		        + Arrays.toString(this.getToEmail()) + "]";
	}

}
