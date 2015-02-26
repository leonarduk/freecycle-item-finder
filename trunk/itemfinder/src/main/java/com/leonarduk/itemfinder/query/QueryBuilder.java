/**
 *
 */
package com.leonarduk.itemfinder.query;

import java.io.IOException;
import java.time.LocalDate;

import org.htmlparser.util.ParserException;

import com.leonarduk.itemfinder.freecycle.FreecycleGroups;
import com.leonarduk.itemfinder.freecycle.FreecycleQueryBuilder;
import com.leonarduk.itemfinder.html.HtmlParser;

/**
 * The Interface QueryBuilder.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 28 Jan 2015
 */
public interface QueryBuilder {

    /**
     * Builds the.
     *
     * @return the html parser
     * @throws ParserException
     *             the parser exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    HtmlParser build() throws ParserException, IOException;

    /**
     * Gets the group.
     *
     * @return the group
     */
    FreecycleGroups getGroup();

    /**
     * Gets the search words.
     *
     * @return the search words
     */
    String getSearchWords();

    /**
     * Sets the date end.
     *
     * @param day
     *            the day
     * @param month
     *            the month
     * @param year
     *            the year
     * @return the freecycle query builder
     */
    FreecycleQueryBuilder setDateEnd(int day, int month, int year);

    /**
     * Sets the date start.
     *
     * @param i
     *            the i
     * @param j
     *            the j
     * @param k
     *            the k
     * @return the freecycle query builder
     */
    FreecycleQueryBuilder setDateStart(int i, int j, int k);

    /**
     * Sets the date start.
     *
     * @param date
     *            the date
     * @return the freecycle query builder
     */
    FreecycleQueryBuilder setDateStart(LocalDate date);

    /**
     * Sets the search words.
     *
     * @param filter
     *            the filter
     * @return the freecycle query builder
     */
    FreecycleQueryBuilder setSearchWords(String filter);

}
