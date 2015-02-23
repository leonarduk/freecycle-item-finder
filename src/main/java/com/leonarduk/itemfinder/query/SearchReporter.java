/**
 * All rights reserved. @Leonard UK Ltd.
 */
package com.leonarduk.itemfinder.query;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.leonarduk.core.config.Config;
import com.leonarduk.core.email.EmailSender;
import com.leonarduk.core.email.EmailSession;
import com.leonarduk.itemfinder.ItemFinderException;
import com.leonarduk.itemfinder.format.Formatter;
import com.leonarduk.itemfinder.freecycle.FreecycleConfig;
import com.leonarduk.itemfinder.freecycle.FreecycleGroups;

/**
 * The Class SearchReporter.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 30 Jan 2015
 */
public final class SearchReporter {

	/** The Constant LOGGER. */
	static final Logger LOGGER = Logger.getLogger(SearchReporter.class);

	/**
	 * Generate report.
	 *
	 * @param config
	 *            the config
	 * @param searches
	 *            the searches
	 * @param groups
	 *            the groups
	 * @param formatter
	 *            the formatter
	 * @param em
	 *            the em
	 * @param reporter
	 *            the reporter
	 * @param failIfEmpty
	 *            the fail if empty
	 * @return the string
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ExecutionException
	 *             the execution exception
	 * @throws ItemFinderException
	 *             the item finder exception
	 */
	public static String generateReport(final FreecycleConfig config, final String[] searches,
	        final FreecycleGroups[] groups, final Formatter formatter, final EntityManager em,
	        final QueryReporter reporter, final boolean failIfEmpty) throws InterruptedException,
	        ExecutionException, ItemFinderException {
		final StringBuilder emailBody = new StringBuilder();

		final Integer resultsPerPage = config.getResultsPerPage();

		final String runReport = reporter
		        .runReport(searches, groups, config.getIntegerProperty("freecycle.search.period"),
		                formatter, em, resultsPerPage);
		if (failIfEmpty && runReport.equals(QueryReporter.NO_RESULTS)) {
			throw new ItemFinderException("No results found for " + searches);
		}
		final String heading = "Searched " + Arrays.asList(groups) + " for " + " "
		        + Arrays.asList(searches);
		emailBody.append(formatter.formatSubHeader(heading));
		emailBody.append(runReport);
		emailBody.append("<hr/>");
		emailBody.append(formatter.formatSubHeader("Searched " + Arrays.asList(groups)
		        + " for everything"));
		emailBody.append(reporter.runReport(new String[] { "" }, groups, 1, formatter, em,
		        resultsPerPage));
		return emailBody.toString();
	}

	/**
	 * Generate search.
	 *
	 * @param config
	 *            the config
	 * @param searches
	 *            the searches
	 * @param groups
	 *            the groups
	 * @param formatter
	 *            the formatter
	 * @param em
	 *            the em
	 * @param reporter
	 *            the reporter
	 * @param failIfEmpty
	 *            the fail if empty
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ExecutionException
	 *             the execution exception
	 */
	public static void generateSearch(final FreecycleConfig config, final String[] searches,
	        final FreecycleGroups[] groups, final Formatter formatter, final EntityManager em,
	        final QueryReporter reporter, final boolean failIfEmpty) throws InterruptedException,
	        ExecutionException {
		SearchReporter.LOGGER.info("generateSearch:" + Arrays.asList(searches) + " - "
		        + Arrays.asList(groups));
		try {
			final String text = SearchReporter.generateReport(config, searches, groups, formatter,
			        em, reporter, failIfEmpty);
			SearchReporter.sendReport(config, text);
		}
		catch (final ItemFinderException e) {
			SearchReporter.LOGGER.info("No results - not sending message");
		}
	}

	/**
	 * Send report.
	 *
	 * @param config
	 *            the config
	 * @param emailBody
	 *            the email body
	 */
	public static void sendReport(final Config config, final String emailBody) {
		final String[] toEmail = config.getArrayProperty("freecycle.email.to");
		final String user = config.getProperty("freecycle.email.user");
		final String server = config.getProperty("freecycle.email.server");
		final String password = config.getProperty("freecycle.email.password");
		final String port = config.getProperty("freecycle.email.port");

		final EmailSender emailSender = new EmailSender();

		final EmailSession session = new EmailSession(user, password, server, port);
		emailSender.sendMessage(config.getProperty("freecycle.email.from.email"),
		        config.getProperty("freecycle.email.from.name"), "Matching Freecycle items found",
		        emailBody.toString(), true, session, toEmail);
	}

	/**
	 * Instantiates a new search reporter.
	 */
	private SearchReporter() {
	}

}
