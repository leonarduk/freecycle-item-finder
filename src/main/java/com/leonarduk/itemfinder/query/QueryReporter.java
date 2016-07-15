/**
 *
 */
package com.leonarduk.itemfinder.query;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.webscraper.core.format.Formatter;

/**
 * The Class QueryReporter.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 28 Jan 2015
 */
public class QueryReporter {

	/** The log. */
	private static final Logger LOG = Logger.getLogger(QueryReporter.class);

	/**
	 * Instantiates a new query reporter.
	 */
	public QueryReporter() {
	}

	/**
	 * Adds the post details.
	 *
	 * @param formatter
	 *            the formatter
	 * @param item
	 *            the item
	 * @return the string
	 */
	final String addPostDetails(final Formatter formatter, final Item item) {
		final String spacer = formatter.getNewLine();
		final StringBuilder emailBodyBuilder = new StringBuilder(spacer);
		emailBodyBuilder.append(formatter.getNewSection());
		emailBodyBuilder.append(spacer);

		final String header = formatter.formatLink(item.getLink(), item.getName()) + " - "
		        + item.getLocation() + " Posted: " + item.getPostedDate().toString();

		emailBodyBuilder.append(formatter.formatSubHeader(header));
		emailBodyBuilder.append(spacer);

		emailBodyBuilder.append(item.getDescription());

		emailBodyBuilder.append(item.getExtraHtml());
		emailBodyBuilder.append(spacer);
		return emailBodyBuilder.toString();
	}

	/**
	 * Convert results map to string.
	 *
	 * @param resultsSet
	 *            the results map
	 * @param formatter
	 *            the formatter
	 * @return the string
	 */
	final String convertResultsSetToString(final Set<Item> resultsSet, final Formatter formatter) {

		final Set<String> uniqueitems = new HashSet<>();
		final StringBuilder emailBodyBuilder = new StringBuilder();

		for (final Item item : resultsSet) {
			if (uniqueitems.contains(item.getLink())) {
				QueryReporter.LOG.info("skip duplicate " + item);
				continue;
			}
			uniqueitems.add(item.getLink());
			emailBodyBuilder.append(this.addPostDetails(formatter, item));
		}
		return emailBodyBuilder.toString();
	}

}
