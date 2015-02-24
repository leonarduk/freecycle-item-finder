/**
 *
 */
package com.leonarduk.itemfinder.query;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.leonarduk.itemfinder.format.Formatter;
import com.leonarduk.itemfinder.freecycle.FreecycleGroups;
import com.leonarduk.itemfinder.freecycle.FreecycleItemSearcher;
import com.leonarduk.itemfinder.freecycle.FreecycleQueryBuilder;
import com.leonarduk.itemfinder.interfaces.Item;

// TODO: Auto-generated Javadoc
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

	/** The Constant NO_RESULTS. */
	public static final String NO_RESULTS = "No results";
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
	public final String addPostDetails(final Formatter formatter, final Item item) {
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
	public final String convertResultsSetToString(final Set<Item> resultsSet,
	        final Formatter formatter) {

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

	/**
	 * Run queries.
	 *
	 * @param searches
	 *            the searches
	 * @param queryBuilder
	 *            the query builder
	 * @param groups
	 *            the groups
	 * @param em
	 *            the em
	 * @return the map
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ExecutionException
	 *             the execution exception
	 */
	public final Set<Item> runQueries(final String[] searches,
	        final FreecycleQueryBuilder queryBuilder, final FreecycleGroups[] groups,
	        final EntityManager em) throws InterruptedException, ExecutionException {
		final ExecutorService executor = Executors.newFixedThreadPool(20);
		final Set<Item> resultsSet = new HashSet<>();
		try {
			final FreecycleItemSearcher searcher = new FreecycleItemSearcher(em);

			for (final String filter : searches) {

				final Set<FutureTask<Set<Item>>> tasks = new HashSet<>();
				for (final FreecycleGroups freecycleGroups : groups) {
					final FreecycleQueryBuilder queryBuilderCopy = new FreecycleQueryBuilder(
					        queryBuilder);
					queryBuilderCopy.setSearchWords(filter.toLowerCase()).setTown(freecycleGroups);
					final CallableQuery query = new CallableQuery(searcher, queryBuilderCopy);
					final FutureTask<Set<Item>> futureTask = new FutureTask<>(query);
					tasks.add(futureTask);
					executor.execute(futureTask);
				}
				for (final FutureTask<Set<Item>> futureTask : tasks) {
					resultsSet.addAll(futureTask.get());
				}

			}
		}
		catch (final Exception e) {
			QueryReporter.LOG.error("Caught error: " + e.getMessage(), e);
		}
		finally {
			executor.shutdown();
		}
		return resultsSet;
	}

	/**
	 * Run report.
	 *
	 * @param searches
	 *            the searches
	 * @param groups
	 *            the groups
	 * @param timeperiod
	 *            the timeperiod
	 * @param formatter
	 *            the formatter
	 * @param em
	 *            the em
	 * @param resultsPerPageNumber
	 *            the results per page number
	 * @return the string
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ExecutionException
	 *             the execution exception
	 */
	public final String runReport(final String[] searches, final FreecycleGroups[] groups,
	        final int timeperiod, final Formatter formatter, final EntityManager em,
			final int resultsPerPageNumber) throws InterruptedException, ExecutionException {

		final FreecycleQueryBuilder queryBuilder = new FreecycleQueryBuilder()
		.setDateStart(LocalDate.now().minus(timeperiod, ChronoUnit.DAYS)).usePost()
		        .setResultsPerPage(resultsPerPageNumber);
		final Set<Item> resultsSet = this.runQueries(searches, queryBuilder, groups, em);
		final String emailBody = this.convertResultsSetToString(resultsSet, formatter);
		if (emailBody.trim().isEmpty()) {
			return QueryReporter.NO_RESULTS;
		}
		return emailBody;
	}

}
