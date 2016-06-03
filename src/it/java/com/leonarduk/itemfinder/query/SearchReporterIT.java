/**
 * SearchReporterIT
 *
 * @author ${author}
 * @since 31-May-2016
 */
package com.leonarduk.itemfinder.query;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.leonarduk.itemfinder.freecycle.FreecycleConfig;
import com.leonarduk.webscraper.core.email.EmailSender;
import com.leonarduk.webscraper.core.format.Formatter;
import com.leonarduk.webscraper.core.format.HtmlFormatter;

/**
 * The Class SearchReporterIT.
 */
public class SearchReporterIT {

	/** The Constant LOGGER. */
	static final Logger LOGGER = Logger.getLogger(SearchReporterIT.class);

	/** The search reporter. */
	private SearchReporter searchReporter;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		this.searchReporter = new SearchReporter();
	}

	/**
	 * Test add post details.
	 */
	@Test
	public final void testAddPostDetails() {

	}

	/**
	 * Test generate report.
	 */
	@Test
	public final void testGenerateReport() {

	}

	/**
	 * Test generate search.
	 *
	 * @throws ParserException
	 *             the parser exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ExecutionException
	 *             the execution exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Ignore
	@Test
	public final void testGenerateSearch()
	        throws ParserException, InterruptedException, ExecutionException, IOException {
		final FreecycleConfig config = new FreecycleConfig();
		config.setSearchPeriod(14);
		config.setSearchTerms("sofa");
		config.setSearchGroupNames("kingston,epsom");
		config.setToEmail("someone@dummy.email");

		config.setEmailUser("test");
		config.setEmailServer("testserver");
		config.setEmailPassword("pwd");
		config.setEmailPort("123");

		config.setResultsPerPage(1);

		final Formatter formatter = new HtmlFormatter();
		final EntityManager em = Mockito.mock(EntityManager.class);
		final EntityTransaction transaction = Mockito.mock(EntityTransaction.class);
		Mockito.when(em.getTransaction()).thenReturn(transaction);

		final QueryReporter reporter = new QueryReporter();
		final boolean failIfEmpty = true;
		final EmailSender emailSender = Mockito.mock(EmailSender.class);
		final StringBuilder emailBody = this.searchReporter.generateSearch(config, formatter, em,
		        reporter, failIfEmpty, emailSender);
		SearchReporterIT.LOGGER.info("Report: " + emailBody);

	}

	/**
	 * Test get latest post.
	 */
	@Test
	public final void testGetLatestPost() {

	}

	/**
	 * Test include post.
	 */
	@Test
	public final void testIncludePost() {

	}

	/**
	 * Test process all groups.
	 */
	@Test
	public final void testProcessAllGroups() {

	}

	/**
	 * Test process individual post.
	 */
	@Test
	public final void testProcessIndividualPost() {

	}

	/**
	 * Test process one group.
	 */
	@Test
	public final void testProcessOneGroup() {

	}

	/**
	 * Test send email.
	 */
	@Test
	public final void testSendEmail() {

	}

	/**
	 * Test should be reported.
	 */
	@Test
	public final void testShouldBeReported() {

	}

}
