package com.leonarduk.itemfinder.freecycle;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

import com.leonarduk.itemfinder.AbstractQueryBuilder;
import com.leonarduk.itemfinder.QueryBuilder;

/**
 * use
 * http://www.freemesa.org/post.php?topic=124814&distance=124.27423844747&city
 * =252713&type=Offer&ret=view&item=&skip=2 instead?
 * 
 * 
 *
 * @author Stephen Leonard
 * @since 21 Jan 2015
 *
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 *
 */
public class FreecycleQueryBuilder extends
		AbstractQueryBuilder<FreecycleQueryBuilder> implements QueryBuilder {
	private FreecycleGroups town;
	private String filter;
	private boolean includeWanted = false;
	private boolean includeOffered = true;
	private int resultsPerPage = 50;
	private int pageNumber = 1;
	private LocalDate dateStart;
	private LocalDate dateEnd;

	public FreecycleQueryBuilder(FreecycleGroups town) {
		setTown(town);
	}

	@Override
	public FreecycleQueryBuilder setDateEnd(int day, int month, int year) {
		this.dateEnd = LocalDate.of(year, month, day);
		return this;
	}

	@Override
	public FreecycleQueryBuilder setDateStart(int day, int month, int year) {
		this.dateStart = LocalDate.of(year, month, day);
		return this;
	}

	@Override
	public FreecycleQueryBuilder setDateStart(LocalDate date) {
		this.dateStart = date;
		return this;
	}

	public FreecycleQueryBuilder setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		return this;
	}

	public FreecycleQueryBuilder setTown(FreecycleGroups town2) {
		this.town = town2;
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

	@Override
	public FreecycleQueryBuilder setSearchWords(String filter) {
		this.filter = filter;
		return this;
	}

	@Override
	public Parser build() throws ParserException, IOException {
		StringBuilder builder = new StringBuilder(
				"https://groups.freecycle.org/group/");
		builder.append(town.url());

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
			parameters.put("date_start", dateStart.toString());
		}
		if (null != dateEnd) {
			parameters.put("date_end", dateEnd.toString());
		}

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

	@Override
	public String getSearchWords() {
		return this.filter;
	}

}
