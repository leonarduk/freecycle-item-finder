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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.leonarduk.email.EmailSender;
import com.leonarduk.itemfinder.freecycle.FreecycleGroups;
import com.leonarduk.itemfinder.freecycle.FreecycleItemSearcher;
import com.leonarduk.itemfinder.freecycle.FreecycleQueryBuilder;
import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.itemfinder.query.CallableQuery;

/**
 * Starts the Spring Context and will initialize the Spring Integration routes.
 *
 * @author Stephen Leonard
 * @since 1.0
 *
 */
public final class ItemFinderMain {

	private static final Logger LOGGER = Logger.getLogger(ItemFinderMain.class);

	private ItemFinderMain() {

	}

	public static void main(String[] args) throws ItemFinderException,
			Exception {
		// JBidWatch.main(new String[]{});

		/**
		 * Double buggy or twin buggy/stroller, Travel system, Quinny, Buggy or
		 * stroller, Pram, Changing table or changing station, Child's bike seat
		 * or toddler bike seat, Printer table, Playhouse or outdoor playhouse,
		 * Buggyboard, Roof rack or car roof rack, Food processor, Microwave,
		 * Flatscreen TV or LCD TV,
		 */

		String[] searches = new String[] { "Double buggy", "twin buggy",
				"bugaboo", "twin stroller", "Travel system", "Quinny", "Buggy",
				"stroller", "Pram", "Changing table", "changing station",
				"Child's bike seat", "toddler bike seat", "Printer table",
				"Playhouse", "Roof rack", "Food processor", "Microwave",
				"Flatscreen TV", "LCD TV", "desk", "tiffany lamp" };

		FreecycleQueryBuilder queryBuilder = new FreecycleQueryBuilder()
				.setDateStart(LocalDate.now().minus(3, ChronoUnit.DAYS));
		Map<String, Set<Item>> resultsMap = runQueries(searches, queryBuilder);
		String specificQueries = convertResultsMapToString(resultsMap);

		StringBuilder emailBody = new StringBuilder("Searched for:<br/>"
				+ Arrays.asList(searches) + queryBuilder.getSearchCriteria()
				+ "<br/>");
		if (specificQueries.trim().length() > 0) {
			emailBody.append(specificQueries);
		} else {
			emailBody.append("Found nothing");
		}
		queryBuilder = new FreecycleQueryBuilder().setDateStart(LocalDate.now()
				.minus(1, ChronoUnit.DAYS));
		resultsMap = runQueries(new String[] { "" }, queryBuilder);
		String fullList = convertResultsMapToString(resultsMap);
		emailBody.append(queryBuilder.getSearchCriteria() + "Full search<br/>");
		if (fullList.trim().length() > 0) {
			emailBody.append(fullList);
		} else {
			emailBody.append("Found nothing");
		}

		String toEmail = "steveleonard11@gmail.com";
		String toEmail2 = "lucyleonard@hotmail.com";
		EmailSender.sendEmail("Matching Freecycle items found",
				emailBody.toString(), toEmail, toEmail2);
	}

	public static String convertResultsMapToString(
			Map<String, Set<Item>> resultsMap) {
		Set<Entry<String, Set<Item>>> keys = resultsMap.entrySet();
		StringBuilder emailBodyBuilder = new StringBuilder();
		for (Entry<String, Set<Item>> entry : keys) {
			System.out.println(entry.getKey());
			Set<Item> entries = entry.getValue();
			System.out.println(entries);
			if (entries.size() > 0) {
				for (Item item : entries) {
					String spacer = "<br/>";
					emailBodyBuilder.append(spacer);
					emailBodyBuilder.append("#############");
					emailBodyBuilder.append(spacer);
					emailBodyBuilder.append(item.getName());
					emailBodyBuilder.append(" - ");

					emailBodyBuilder.append(item.getLocation());
					emailBodyBuilder.append(spacer);
					emailBodyBuilder
							.append("<a href=\"" + item.getLink() + "\">");

					emailBodyBuilder.append(item.getDescription());

					emailBodyBuilder.append("</a>");
					emailBodyBuilder.append(spacer);
				}
			}
		}
		return emailBodyBuilder.toString();
	}

	public static Map<String, Set<Item>> runQueries(String[] searches,
			FreecycleQueryBuilder queryBuilder) throws InterruptedException,
			ExecutionException {
		FreecycleItemSearcher searcher = new FreecycleItemSearcher();

		ExecutorService executor = Executors.newFixedThreadPool(20);
		Map<String, Set<Item>> resultsMap = new HashMap<>();

		for (String filter : searches) {
			Set<Item> items = new HashSet<Item>();

			Set<FutureTask<Set<Item>>> tasks = new HashSet<>();
			for (FreecycleGroups freecycleGroups : FreecycleGroups.values()) {
				queryBuilder.setSearchWords(filter).setTown(freecycleGroups);
				CallableQuery query = new CallableQuery(searcher, queryBuilder);
				FutureTask<Set<Item>> futureTask = new FutureTask<>(query);
				tasks.add(futureTask);
				executor.execute(futureTask);
			}
			for (FutureTask<Set<Item>> futureTask : tasks) {
				items.addAll(futureTask.get());
			}

			resultsMap.put(filter, items);

		}
		executor.shutdown();
		return resultsMap;
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
}
