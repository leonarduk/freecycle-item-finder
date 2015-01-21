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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

import com.leonarduk.itemfinder.freecycle.FreecycleGroups;
import com.leonarduk.itemfinder.freecycle.FreecycleItemSearcher;
import com.leonarduk.itemfinder.freecycle.FreecycleQueryBuilder;
import com.leonarduk.itemfinder.interfaces.Item;

/**
 * Starts the Spring Context and will initialize the Spring Integration routes.
 *
 * @author Stephen Leonard
 * @since 1.0
 *
 */
public final class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class);

	private Main() {

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
		Map<String, List<Item>> resultsMap = new HashMap<>();

		FreecycleItemSearcher searcher = new FreecycleItemSearcher();
		String[] searches = new String[] { "Double buggy", "twin buggy",
				"twin stroller", "Travel system", "Quinny", "Buggy",
				"stroller", "Pram", "Changing table", "changing station",
				"Child's bike seat", "toddler bike seat", "Printer table",
				"Playhouse", "Roof rack", "Food processor", "Microwave",
				"Flatscreen TV", "LCD TV" };
		ExecutorService executor = Executors.newFixedThreadPool(20);

		for (String filter : searches) {
			FreecycleGroups[] groups = FreecycleGroups.values();
			List<Item> items = new ArrayList<Item>();

			List<FutureTask<List<Item>>> tasks = new ArrayList<>();
			for (FreecycleGroups freecycleGroups : groups) {
				FreecycleQueryBuilder queryBuilder = new FreecycleQueryBuilder(
						freecycleGroups).setSearchWords(filter).setDateStart(
						LocalDate.now().minus(1, ChronoUnit.DAYS));
				CallableQuery query = new CallableQuery(searcher, queryBuilder);
				FutureTask<List<Item>> futureTask = new FutureTask<>(query);
				tasks.add(futureTask);
				executor.execute(futureTask);
			}
			for (FutureTask<List<Item>> futureTask : tasks) {
				items.addAll(futureTask.get());
			}

			resultsMap.put(filter, items);

		}
		Set<Entry<String, List<Item>>> keys = resultsMap.entrySet();
		for (Entry<String, List<Item>> entry : keys) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());

		}

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
