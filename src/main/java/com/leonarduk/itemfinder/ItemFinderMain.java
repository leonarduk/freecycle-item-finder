/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.leonarduk.itemfinder;

import java.util.Scanner;

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
 * @since 1.0
 *
 */
public final class ItemFinderMain {

	private static final Logger LOGGER = Logger.getLogger(ItemFinderMain.class);

	public static void main(final String[] args) throws ItemFinderException,
			Exception {
		// JBidWatch.main(new String[]{});

		Config config = new Config("itemfinder.properties");
		String[] searches = config.getArrayProperty("freecycle.search.terms");

		String[] groupNames = config
				.getArrayProperty("freecycle.search.groups");
		FreecycleGroups[] groups = new FreecycleGroups[groupNames.length];
		for (int i = 0; i < groups.length; i++) {
			groups[i] = FreecycleGroups.valueOf(groupNames[i]);
		}
		Formatter formatter = new HtmlFormatter();
		StringBuilder emailBody = new StringBuilder();

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("ReportableItem");
		EntityManager em = emf.createEntityManager();
		emailBody.append(QueryReporter.runReport(searches, groups,
				config.getIntegerProperty("freecycle.search.period"),
				formatter, em));
		emailBody.append(QueryReporter.runReport(new String[] { "" }, groups,
				1, formatter, em));

		String[] toEmail = config.getArrayProperty("freecycle.email.to");
		final String user = config.getProperty("freecycle.email.user");
		String server = config.getProperty("freecycle.email.server");
		final String password = config.getProperty("freecycle.email.password");
		String port = config.getProperty("freecycle.email.port");

		EmailSender emailSender = new EmailSender();

		EmailSession session = new EmailSession(user, password, server, port);
		emailSender.sendMessage(
				config.getProperty("freecycle.email.from.email"),
				config.getProperty("freecycle.email.from.name"),
				"Matching Freecycle items found", emailBody.toString(), true,
				session, toEmail);
	}

	/**
	 * Load the Spring Integration Application Context
	 *
	 * @param args
	 *            - command line arguments
	 */
	public static void main2(final String... args) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("\n========================================================="
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

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("\n========================================================="
					+ "\n                                                         "
					+ "\n    Please press 'q + Enter' to quit the application.    "
					+ "\n                                                         "
					+ "\n=========================================================");
		}

		while (!scanner.hasNext("q")) {
			// Do nothing unless user presses 'q' to quit.
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Exiting application...bye.");
		}

		System.exit(0);

	}

	private ItemFinderMain() {

	}
}
