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
import org.junit.Test;
import org.mockito.Mockito;

import com.leonarduk.itemfinder.freecycle.FreecycleConfig;
import com.leonarduk.webscraper.core.email.EmailSender;
import com.leonarduk.webscraper.core.format.Formatter;
import com.leonarduk.webscraper.core.format.HtmlFormatter;

public class SearchReporterIT {
	static final Logger LOGGER = Logger.getLogger(SearchReporterIT.class);

	private SearchReporter searchReporter;

	@Before
	public void setUp() throws Exception {
		this.searchReporter = new SearchReporter();
	}

	@Test
	public final void testAddPostDetails() {

	}

	@Test
	public final void testGenerateReport() {

	}

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

	@Test
	public final void testGetLatestPost() {

	}

	@Test
	public final void testIncludePost() {

	}

	@Test
	public final void testProcessAllGroups() {

	}

	@Test
	public final void testProcessIndividualPost() {

	}

	@Test
	public final void testProcessOneGroup() {

	}

	@Test
	public final void testSendEmail() {

	}

	@Test
	public final void testShouldBeReported() {

	}

}
