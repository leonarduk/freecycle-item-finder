package com.leonarduk.itemfinder.freecycle;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

import com.leonarduk.itemfinder.AbstractQueryBuilder;

public class FreecycleQueryBuilder extends AbstractQueryBuilder<FreecycleQueryBuilder>{
	private String town;
	private String filter;
	private boolean includeWanted = false;
	private boolean includeOffered = true;
	private boolean includeReceived = false;
	private boolean includeTaken = false;
	private int resultsPerPage = 50;
	private int pageNumber = 1;
	private LocalDate dateStart;
	private Object dateEnd;

	public FreecycleQueryBuilder(String town) {
		setTown(town);
	}

	public FreecycleQueryBuilder setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		return this;
	}

	public FreecycleQueryBuilder setTown(String town) {
		this.town = town;
		return this;
	}

	public FreecycleQueryBuilder setResultsPerPage(int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
		return this;
	}

	public FreecycleQueryBuilder setIncludeWanted(boolean include) {
		this.includeWanted = include;
		return this;
	}

	public FreecycleQueryBuilder setIncludeOffered(boolean include) {
		this.includeWanted = include;
		return this;
	}

	public FreecycleQueryBuilder setSearchWords(String filter) {
		this.filter = filter;
		return this;
	}

	public Parser build() throws ParserException, IOException {
		StringBuilder builder = new StringBuilder(
				"https://groups.freecycle.org/group/freecycle-");
		builder.append(town);

		builder.append("/posts/search");
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("page", String.valueOf(pageNumber));
		parameters.put("resultsperpage", String.valueOf(resultsPerPage));
		if (includeOffered) {
			parameters.put("include_offers", "on");
		}
		if (includeWanted) {
			parameters.put("include_wanteds", "on");
		}
		if (null != dateStart) {
			parameters.put("date_start", dateStart);
		}
		if (null != dateEnd) {
			parameters.put("date_end", dateEnd);
		}
		
		dd/mm/yyyy
		if (this.filter != null) {
			parameters.put("search_words", this.filter);
		}
		if (usesPOSTMethod()) {
			parameters.put("submit", "Search for Posts");
			return getPOSTConnection(builder.toString(), parameters);
		} else {
			return getGETConnection(builder.toString(), parameters);
		}
	}

}
