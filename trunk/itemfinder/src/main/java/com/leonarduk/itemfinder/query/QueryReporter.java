/**
 *
 */
package com.leonarduk.itemfinder.query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
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
	public String addPostDetails(final Formatter formatter, final Item item) {
		final String spacer = formatter.getNewLine();
		final StringBuilder emailBodyBuilder = new StringBuilder(spacer);
		emailBodyBuilder.append(formatter.getNewSection());
		emailBodyBuilder.append(spacer);

		final LocalDate res = LocalDateTime.ofInstant(item.getPostedDate().toInstant(),
		        ZoneId.systemDefault()).toLocalDate();
		final String header = formatter.formatLink(item.getLink(), item.getName()) + " - "
		        + item.getLocation() + " Posted: " + res.toString();

		emailBodyBuilder.append(formatter.formatSubHeader(header));
		emailBodyBuilder.append(spacer);

		emailBodyBuilder.append(item.getDescription());

		emailBodyBuilder.append(spacer);
		return header;
	}

	/**
	 * Convert results map to string.
	 *
	 * @param resultsMap
	 *            the results map
	 * @param formatter
	 *            the formatter
	 * @return the string
	 */
	public String convertResultsMapToString(final Map<String, Set<Item>> resultsMap,
	        final Formatter formatter) {
		final Set<String> uniqueitems = new HashSet<>();
		final Set<Entry<String, Set<Item>>> keys = resultsMap.entrySet();
		final StringBuilder emailBodyBuilder = new StringBuilder();
		for (final Entry<String, Set<Item>> entry : keys) {
			System.out.println(entry.getKey());
			final Set<Item> entries = entry.getValue();
			System.out.println(entries);
			if (entries.size() > 0) {
				for (final Item item : entries) {
					if (uniqueitems.contains(item.getLink())) {
						QueryReporter.LOG.info("skip duplicate " + item);
						continue;
					}
					uniqueitems.add(item.getLink());
					emailBodyBuilder.append(this.addPostDetails(formatter, item));
				}
			}
		}
		return emailBodyBuilder.toString();
	}

	/**
	 * Gets the email body.
	 *
	 * @param searches
	 *            the searches
	 * @param groups
	 *            the groups
	 * @param formatter
	 *            the formatter
	 * @param queryBuilder
	 *            the query builder
	 * @param resultsMap
	 *            the results map
	 * @return the email body
	 */
	public final String getEmailBody(final String[] searches, final FreecycleGroups[] groups,
	        final Formatter formatter, final FreecycleQueryBuilder queryBuilder,
	        final Map<String, Set<Item>> resultsMap) {
		final String heading = "Searched " + Arrays.asList(groups) + " for "
		        + queryBuilder.getSearchCriteria() + " " + Arrays.asList(searches);
		final StringBuilder emailBody = new StringBuilder(formatter.formatHeader(heading));
		final String results = this.convertResultsMapToString(resultsMap, formatter);
		emailBody.append(results);

		if (results.trim().length() > 0) {
			emailBody.append(results);
		}
		else {
			emailBody.append("Found nothing");
		}
		return emailBody.toString();
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
	public Map<String, Set<Item>> runQueries(final String[] searches,
	        final FreecycleQueryBuilder queryBuilder, final FreecycleGroups[] groups,
	        final EntityManager em) throws InterruptedException, ExecutionException {

		final FreecycleItemSearcher searcher = new FreecycleItemSearcher(em);

		final ExecutorService executor = Executors.newFixedThreadPool(20);
		final Map<String, Set<Item>> resultsMap = new HashMap<>();

		for (final String filter : searches) {
			final Set<Item> items = new HashSet<Item>();

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
				items.addAll(futureTask.get());
			}

			resultsMap.put(filter, items);

		}
		executor.shutdown();
		return resultsMap;
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
	 * @return the string
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ExecutionException
	 *             the execution exception
	 */
	public String runReport(final String[] searches, final FreecycleGroups[] groups,
	        final int timeperiod, final Formatter formatter, final EntityManager em)
	        throws InterruptedException, ExecutionException {

		final FreecycleQueryBuilder queryBuilder = new FreecycleQueryBuilder()
		        .setDateStart(LocalDate.now().minus(timeperiod, ChronoUnit.DAYS));
		final Map<String, Set<Item>> resultsMap = this.runQueries(searches, queryBuilder, groups,
		        em);
		return this.getEmailBody(searches, groups, formatter, queryBuilder, resultsMap);
	}

}
