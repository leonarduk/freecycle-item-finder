/**
 * All rights reserved. @Leonard UK Ltd.
 */
package com.leonarduk.itemfinder.freecycle;

import java.io.IOException;

import com.leonarduk.core.config.Config;

/**
 * The Class FreecycleConfig.
 *
 * @author stephen
 * @version $Author: $: Author of last commit
 * @version $Rev: $: Revision of last commit
 * @version $Date: $: Date of last commit
 * @since 23 Feb 2015
 */
public class FreecycleConfig extends Config {

    /** The Constant FREECYCLE_SEARCH_PERIOD. */
    private static final String FREECYCLE_SEARCH_PERIOD =
            "freecycle.search.period";

    /** The Constant FREECYCLE_SEARCH_RESULTSPERPAGE. */
    private static final String FREECYCLE_SEARCH_RESULTSPERPAGE =
            "freecycle.search.resultsperpage";

    private static final String FREECYCLE_SEARCH_MAX_LIMIT =
            "freecycle.search.limit";

    /** The Constant MAX_RESULTS_PER_PAGE. */
    public static final int MAX_RESULTS_PER_PAGE = 100;

    /** The Constant MIN_RESULTS_PER_PAGE. */
    public static final int MIN_RESULTS_PER_PAGE = 10;

    /**
     * Instantiates a new freecycle config.
     */
    public FreecycleConfig() {
        super();
        this.setResultsPerPage(FreecycleConfig.MAX_RESULTS_PER_PAGE);
    }

    /**
     * Instantiates a new freecycle config.
     *
     * @param configFile
     *            the config file
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public FreecycleConfig(final String configFile) throws IOException {
        super(configFile);
        this.setResultsPerPage(FreecycleConfig.MAX_RESULTS_PER_PAGE);
    }

    /**
     * Gets the results per page.
     *
     * @return the results per page
     */
    public final int getResultsPerPage() {
        return this
                .getIntegerProperty(FreecycleConfig.FREECYCLE_SEARCH_RESULTSPERPAGE);
    }

    /**
     * Sets the results per page.
     *
     * @param resultsPerPage
     *            the new results per page
     */
    public final void setResultsPerPage(final int resultsPerPage) {
        this.setProperty(FreecycleConfig.FREECYCLE_SEARCH_RESULTSPERPAGE,
                String.valueOf(resultsPerPage));
    }

    /**
     * Sets the search period.
     *
     * @param period
     *            the new search period
     */
    public final void setSearchPeriod(final int period) {
        this.setProperty(FreecycleConfig.FREECYCLE_SEARCH_PERIOD,
                String.valueOf(period));
    }

    public Integer getSearchItemLimit() {
        return this
                .getIntegerProperty(FreecycleConfig.FREECYCLE_SEARCH_MAX_LIMIT);

    }
}
