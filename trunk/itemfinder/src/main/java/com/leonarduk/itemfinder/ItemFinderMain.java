/*
 * Copyright 2002-2013 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.leonarduk.itemfinder;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.leonarduk.core.config.Config;
import com.leonarduk.core.email.EmailSender;
import com.leonarduk.core.email.EmailSession;
import com.leonarduk.itemfinder.format.Formatter;
import com.leonarduk.itemfinder.format.HtmlFormatter;
import com.leonarduk.itemfinder.freecycle.FreecycleGroups;
import com.leonarduk.itemfinder.query.QueryReporter;

/**
 * Starts the Spring Context and will initialize the Spring Integration routes.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 28 Jan 2015
 */
public final class ItemFinderMain {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(ItemFinderMain.class);

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
	 * @return the string
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ExecutionException
	 *             the execution exception
	 * @throws ItemFinderException
	 */
	public static String generateReport(final Config config, final String[] searches,
	        final FreecycleGroups[] groups, final Formatter formatter, final EntityManager em,
	        final QueryReporter reporter, final boolean failIfEmpty) throws InterruptedException,
	        ExecutionException, ItemFinderException {
		final StringBuilder emailBody = new StringBuilder();

		final String runReport = reporter.runReport(searches, groups,
		        config.getIntegerProperty("freecycle.search.period"), formatter, em);
		if (failIfEmpty && runReport.equals(QueryReporter.NO_RESULTS)) {
			throw new ItemFinderException("No results found for " + searches);
		}
		final String heading = "Searched " + Arrays.asList(groups) + " for " + " "
		        + Arrays.asList(searches);
		emailBody.append(formatter.formatHeader(heading));
		emailBody.append(runReport);
		emailBody.append("<hr/>");
		emailBody.append(formatter.formatHeader("Searched " + Arrays.asList(groups)
		        + " for everything"));
		emailBody.append(reporter.runReport(new String[] { "" }, groups, 1, formatter, em));
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
	public static void generateSearch(final Config config, final String[] searches,
	        final FreecycleGroups[] groups, final Formatter formatter, final EntityManager em,
	        final QueryReporter reporter, final boolean failIfEmpty) throws InterruptedException,
	        ExecutionException {
		ItemFinderMain.LOGGER.info("generateSearch:" + Arrays.asList(searches) + " - "
		        + Arrays.asList(groups));
		try {
			final String text = ItemFinderMain.generateReport(config, searches, groups, formatter,
			        em, reporter, failIfEmpty);
			ItemFinderMain.sendReport(config, text);
		}
		catch (final ItemFinderException e) {
			ItemFinderMain.LOGGER.info("No results - not sending message");
		}
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(final String[] args) throws Exception {
		// JBidWatch.main(new String[]{});

		final Config config = new Config("itemfinder.properties");
		final String[] searches = config.getArrayProperty("freecycle.search.terms");

		final String[] groupNames = config.getArrayProperty("freecycle.search.groups");
		final FreecycleGroups[] groups = new FreecycleGroups[groupNames.length];
		for (int i = 0; i < groups.length; i++) {
			groups[i] = FreecycleGroups.valueOf(groupNames[i]);
		}
		final Formatter formatter = new HtmlFormatter();

		final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReportableItem");
		final EntityManager em = emf.createEntityManager();
		final QueryReporter reporter = new QueryReporter();
		boolean failIfEmpty = true;
		ItemFinderMain.generateSearch(config, searches, groups, formatter, em, reporter,
		        failIfEmpty);

		while (true) {

			final int millisecondsPerSecond = 1000;
			final int secondsPerMinute = 60;
			final int minutesToWait = config.getIntegerProperty("freecycle.search.interval");

			final long time = millisecondsPerSecond * secondsPerMinute * minutesToWait;
			ItemFinderMain.LOGGER.info("Waiting " + minutesToWait + " minutes.");

			Thread.sleep(time);
			failIfEmpty = true;
			ItemFinderMain.generateSearch(config, searches, groups, formatter, em, reporter,
			        failIfEmpty);
		}
	}

	/**
	 * Load the Spring Integration Application Context.
	 *
	 * @param args
	 *            - command line arguments
	 */
	public static void main2(final String... args) {

		if (ItemFinderMain.LOGGER.isInfoEnabled()) {
			ItemFinderMain.LOGGER
			        .info("\n========================================================="
			                + "\n                                                         "
			                + "\n          Welcome to Spring Integration!                 "
			                + "\n                                                         "
			                + "\n    For more information please visit:                   "
			                + "\n    http://www.springsource.org/spring-integration       "
			                + "\n                                                         "
			                + "\n=========================================================");
		}

		final AbstractApplicationContext context = new ClassPathXmlApplicationContext(
		        "classpath:META-INF/spring/integration/*-context.xml");

		context.registerShutdownHook();

		SpringIntegrationUtils.displayDirectories(context);

		final Scanner scanner = new Scanner(System.in);

		if (ItemFinderMain.LOGGER.isInfoEnabled()) {
			ItemFinderMain.LOGGER
			        .info("\n========================================================="
			                + "\n                                                         "
			                + "\n    Please press 'q + Enter' to quit the application.    "
			                + "\n                                                         "
			                + "\n=========================================================");
		}

		while (!scanner.hasNext("q")) {
			// Do nothing unless user presses 'q' to quit.
			try {
				final int onesecond = 1000;
				Thread.sleep(onesecond);
			}
			catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (ItemFinderMain.LOGGER.isInfoEnabled()) {
			ItemFinderMain.LOGGER.info("Exiting application...bye.");
		}
		scanner.close();
		System.exit(0);

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
	 * Instantiates a new item finder main.
	 */
	private ItemFinderMain() {

	}
}
