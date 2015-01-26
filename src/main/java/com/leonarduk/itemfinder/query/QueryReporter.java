package com.leonarduk.itemfinder.query;

import java.time.LocalDate;
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

import org.apache.log4j.Logger;

import com.adamshone.freecycle.impl.FreecycleNewhamScraper;
import com.leonarduk.itemfinder.format.Formatter;
import com.leonarduk.itemfinder.freecycle.FreecycleGroups;
import com.leonarduk.itemfinder.freecycle.FreecycleItemSearcher;
import com.leonarduk.itemfinder.freecycle.FreecycleQueryBuilder;
import com.leonarduk.itemfinder.interfaces.Item;

public class QueryReporter {
	static Logger log = Logger.getLogger(QueryReporter.class);

	public static Map<String, Set<Item>> runQueries(String[] searches,
			FreecycleQueryBuilder queryBuilder, FreecycleGroups[] groups)
			throws InterruptedException, ExecutionException {
		FreecycleItemSearcher searcher = new FreecycleItemSearcher();

		ExecutorService executor = Executors.newFixedThreadPool(20);
		Map<String, Set<Item>> resultsMap = new HashMap<>();

		for (String filter : searches) {
			Set<Item> items = new HashSet<Item>();

			Set<FutureTask<Set<Item>>> tasks = new HashSet<>();
			for (FreecycleGroups freecycleGroups : groups) {
				FreecycleQueryBuilder queryBuilderCopy = new FreecycleQueryBuilder(
						queryBuilder);
				queryBuilderCopy.setSearchWords(filter.toLowerCase()).setTown(
						freecycleGroups);
				CallableQuery query = new CallableQuery(searcher,
						queryBuilderCopy);
				FutureTask<Set<Item>> futureTask = new FutureTask<>(query);
				tasks.add(futureTask);
				executor.execute(futureTask);
			}
			for (FutureTask<Set<Item>> futureTask : tasks) {
				items.addAll(futureTask.get());
			}

			resultsMap.put(filter, items);

		}
		executor.shutdown();
		return resultsMap;
	}

	public static String convertResultsMapToString(
			Map<String, Set<Item>> resultsMap, Formatter formatter) {
		Set<String> uniqueitems = new HashSet<>();
		Set<Entry<String, Set<Item>>> keys = resultsMap.entrySet();
		StringBuilder emailBodyBuilder = new StringBuilder();
		for (Entry<String, Set<Item>> entry : keys) {
			System.out.println(entry.getKey());
			Set<Item> entries = entry.getValue();
			System.out.println(entries);
			if (entries.size() > 0) {
				for (Item item : entries) {
					if (!uniqueitems.contains(item.getLink())) {
						log.info("skip duplicate " + item);
						continue;
					}
					uniqueitems.add(item.getLink());
					String spacer = formatter.getNewLine();
					emailBodyBuilder.append(spacer);
					emailBodyBuilder.append(formatter.getNewSection());
					emailBodyBuilder.append(spacer);
					String header = formatter.formatLink(item.getLink(),
							item.getName())
							+ " - " + item.getLocation();
					emailBodyBuilder.append(formatter.formatSubHeader(header));
					emailBodyBuilder.append(spacer);

					emailBodyBuilder.append(item.getDescription());

					emailBodyBuilder.append(spacer);
				}
			}
		}
		return emailBodyBuilder.toString();
	}

	public static String runReport(String[] searches, FreecycleGroups[] groups,
			int timeperiod, Formatter formatter) throws InterruptedException,
			ExecutionException {

		FreecycleQueryBuilder queryBuilder = new FreecycleQueryBuilder()
				.setDateStart(LocalDate.now()
						.minus(timeperiod, ChronoUnit.DAYS));
		Map<String, Set<Item>> resultsMap = runQueries(searches, queryBuilder,
				groups);
		String heading = "Searched " + Arrays.asList(groups) + " for "
				+ queryBuilder.getSearchCriteria() + " "
				+ Arrays.asList(searches);
		StringBuilder emailBody = new StringBuilder(
				formatter.formatHeader(heading));
		String results = convertResultsMapToString(resultsMap, formatter);
		emailBody.append(results);

		if (results.trim().length() > 0) {
			emailBody.append(results);
		} else {
			emailBody.append("Found nothing");
		}
		return emailBody.toString();
	}

}
