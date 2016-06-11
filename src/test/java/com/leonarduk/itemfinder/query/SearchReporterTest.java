/**
 * All rights reserved. @Leonard UK Ltd.
 */
package com.leonarduk.itemfinder.query;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.leonarduk.itemfinder.ItemFinderException;
import com.leonarduk.itemfinder.MockObjectGenerator;
import com.leonarduk.itemfinder.freecycle.FreecycleConfig;
import com.leonarduk.itemfinder.freecycle.FreecycleGroup;
import com.leonarduk.itemfinder.freecycle.FreecycleItem;
import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.webscraper.core.email.impl.EmailSenderImpl;
import com.leonarduk.webscraper.core.format.Formatter;
import com.leonarduk.webscraper.core.format.HtmlFormatter;

/**
 * The Class SearchReporterTest.
 *
 * @author stephen
 * @version $Author: $: Author of last commit
 * @version $Rev: $: Revision of last commit
 * @version $Date: $: Date of last commit
 * @since 23 Feb 2015
 */
public class SearchReporterTest {

	/** The Constant LOGGER. */
	static final Logger LOGGER = Logger.getLogger(SearchReporterTest.class);

	/** The config. */
	private FreecycleConfig config;

	/** The searches. */
	private String[] searches;

	/** The groups. */
	private FreecycleGroup[] groups;

	/** The formatter. */
	private Formatter formatter;

	/** The em. */
	private EntityManager em;

	/** The reporter. */
	private QueryReporter reporter;

	/** The fail if empty. */
	private boolean failIfEmpty;

	private SearchReporter searchReporter;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public final void setUp() throws Exception {
		this.config = MockObjectGenerator.createFreecycleConfig();
		this.searches = new String[] { "bed", "desk" };
		this.groups = new FreecycleGroup[] { FreecycleGroup.kingston };
		this.reporter = new QueryReporter();
		this.failIfEmpty = false;
		this.formatter = new HtmlFormatter();
		this.em = MockObjectGenerator.getMockEntityManager();
		this.searchReporter = new SearchReporter();

	}

	@Ignore   // need to fix date - Travis is UST
	@Test
	public final void testAddPostDetails() throws Exception {
		final String linkValue = "http://sss";
		final String locationValue = "TestVille";
		final String nameValue = "Free things";
		final String extraHtmlValue = "more";
		final String descriptionValue = "PLease take this old rubbish away";
		final Calendar instance = Calendar.getInstance();
		instance.set(2016, 6, 8, 0, 0, 0);
		final Date createdDateValue = instance.getTime();

		final Item item = new FreecycleItem(linkValue, locationValue, nameValue, extraHtmlValue,
		        descriptionValue, createdDateValue);
		final String actual = this.searchReporter.addPostDetails(this.formatter, item);
		final String expected = "<br/><hr/><br/><h2><a href=\"" + linkValue
		        + "\">Free things</a> - " + locationValue
		        + " Posted: Fri Jul 08 00:00:00 BST 2016</h2><br/>" + descriptionValue
		        + extraHtmlValue + "<br/>";
		Assert.assertEquals(expected, actual);
	}

	/**
	 * Test generate report.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ExecutionException
	 *             the execution exception
	 * @throws ItemFinderException
	 *             the item finder exception
	 */
	@Test
	@Ignore
	public final void testGenerateReport()
	        throws InterruptedException, ExecutionException, ItemFinderException {
		SearchReporter.generateReport(this.config, this.searches, this.groups, this.formatter,
		        this.em, this.reporter, this.failIfEmpty);

	}

	/**
	 * Test generate search.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ExecutionException
	 *             the execution exception
	 * @throws ParserException
	 *             the parser exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	@Ignore
	public final void testGenerateSearch()
	        throws InterruptedException, ExecutionException, ParserException, IOException {
		final EmailSenderImpl sender = Mockito.mock(EmailSenderImpl.class);
		this.config.setToEmail("test@test.com");
		this.searchReporter.generateSearch(this.config, this.formatter, this.em, this.reporter,
		        this.failIfEmpty, sender);
	}

}
