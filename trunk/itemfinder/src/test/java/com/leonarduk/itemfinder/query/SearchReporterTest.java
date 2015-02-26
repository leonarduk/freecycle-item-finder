/**
 * All rights reserved. @Leonard UK Ltd.
 */
package com.leonarduk.itemfinder.query;

import java.util.concurrent.ExecutionException;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.itemfinder.ItemFinderException;
import com.leonarduk.itemfinder.MockObjectGenerator;
import com.leonarduk.itemfinder.format.Formatter;
import com.leonarduk.itemfinder.format.HtmlFormatter;
import com.leonarduk.itemfinder.freecycle.FreecycleConfig;
import com.leonarduk.itemfinder.freecycle.FreecycleGroup;

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
    private FreecycleConfig config;
    private String[] searches;
    private FreecycleGroup[] groups;
    private Formatter formatter;
    private EntityManager em;
    private QueryReporter reporter;
    private boolean failIfEmpty;

    /**
     * Sets the up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {
        this.config = MockObjectGenerator.createFreecycleConfig();
        this.searches = new String[] {
                "bed", "desk"
        };
        this.groups = new FreecycleGroup[] {
                FreecycleGroup.kingston
        };
        this.reporter = new QueryReporter();
        this.failIfEmpty = false;
        this.formatter = new HtmlFormatter();
        this.em = MockObjectGenerator.getMockEntityManager();

    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test generate report.
     *
     * @throws ItemFinderException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public final void testGenerateReport()
            throws InterruptedException,
            ExecutionException,
            ItemFinderException {
        SearchReporter.generateReport(this.config, this.searches, this.groups,
                this.formatter, this.em, this.reporter, this.failIfEmpty);

    }

    /**
     * Test generate search.
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    // @Test
    // public final void testGenerateSearch()
    // throws InterruptedException,
    // ExecutionException {
    // SearchReporter.generateSearch(this.config, this.searches, this.groups,
    // this.formatter, this.em, this.reporter, this.failIfEmpty);
    // }

    /**
     * Test send report.
     */
    @Test
    public final void testSendReport() {
        final String emailBody = "test email text";
        SearchReporter.sendReport(this.config, emailBody);
    }

}
