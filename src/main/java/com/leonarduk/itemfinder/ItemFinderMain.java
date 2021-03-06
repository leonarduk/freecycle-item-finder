/*
 * Copyright 2002-2013 the original author or authors. Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package com.leonarduk.itemfinder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.leonarduk.itemfinder.freecycle.FreecycleConfig;
import com.leonarduk.itemfinder.query.QueryReporter;
import com.leonarduk.itemfinder.query.SearchReporter;
import com.leonarduk.webscraper.core.email.impl.EmailSenderImpl;
import com.leonarduk.webscraper.core.format.Formatter;
import com.leonarduk.webscraper.core.format.HtmlFormatter;

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

		final FreecycleConfig config = new FreecycleConfig("itemfinder.properties");

		ItemFinderMain.LOGGER.info("Config: " + config.toString());

		final Formatter formatter = new HtmlFormatter();

		final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReportableItem");
		final EntityManager em = emf.createEntityManager();
		final QueryReporter reporter = new QueryReporter();
		final boolean failIfEmpty = true;

		final EmailSenderImpl emailSender = new EmailSenderImpl();
		final EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			final SearchReporter searchReporter = new SearchReporter();
			final StringBuilder emailBody = searchReporter.generateSearch(config, formatter, em,
			        reporter, failIfEmpty, emailSender);
			ItemFinderMain.LOGGER.info("Email content: \n" + emailBody);
			if (emailBody != null) {
				searchReporter.sendEmail(config, emailSender, emailBody);
			}
			tx.commit();
		}
		catch (final Throwable e) {
			tx.rollback();
			// we rollback if there is an issue with email send
			ItemFinderMain.LOGGER.fatal("Unhandled error:", e);
			throw new RuntimeException("Error in search", e);
		}

	}

	/**
	 * Instantiates a new item finder main.
	 */
	private ItemFinderMain() {

	}
}
