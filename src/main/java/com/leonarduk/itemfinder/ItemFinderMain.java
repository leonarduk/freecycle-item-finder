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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.leonarduk.core.config.Config;
import com.leonarduk.itemfinder.format.Formatter;
import com.leonarduk.itemfinder.format.HtmlFormatter;
import com.leonarduk.itemfinder.freecycle.FreecycleGroups;
import com.leonarduk.itemfinder.query.QueryReporter;
import com.leonarduk.itemfinder.query.SearchReporter;

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

	/** The Constant FREECYCLE_SEARCH_TERMS. */
	public static final String FREECYCLE_SEARCH_TERMS = "freecycle.search.terms";
	/** The Constant LOGGER. */
	static final Logger LOGGER = Logger.getLogger(ItemFinderMain.class);

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
		final String[] searches = config.getArrayProperty(ItemFinderMain.FREECYCLE_SEARCH_TERMS);

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
		SearchReporter.generateSearch(config, searches, groups, formatter, em, reporter,
		        failIfEmpty);

		while (true) {

			final int millisecondsPerSecond = 1000;
			final int secondsPerMinute = 60;
			final int minutesToWait = config.getIntegerProperty("freecycle.search.interval");

			final long time = millisecondsPerSecond * secondsPerMinute * minutesToWait;
			ItemFinderMain.LOGGER.info("Waiting " + minutesToWait + " minutes.");

			Thread.sleep(time);
			failIfEmpty = true;
			SearchReporter.generateSearch(config, searches, groups, formatter, em, reporter,
			        failIfEmpty);
		}
	}

	/**
	 * Instantiates a new item finder main.
	 */
	private ItemFinderMain() {

	}
}
