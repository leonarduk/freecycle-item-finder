/**
 *
 */
package com.leonarduk.itemfinder.freecycle;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;

import com.leonarduk.itemfinder.query.AbstractQueryBuilder;
import com.leonarduk.itemfinder.query.QueryBuilder;
import com.leonarduk.web.HtmlParser;

/**
 * The Class FreecycleQueryBuilder.
 */
public class FreecycleQueryBuilder extends AbstractQueryBuilder<FreecycleQueryBuilder>
        implements QueryBuilder {

	/** The Constant DEFAULT_ITEMS_PER_PAGE. */
	private static final int	DEFAULT_ITEMS_PER_PAGE	= 50;
	/** The log. */
	private final Logger		LOG						= Logger
	        .getLogger(FreecycleQueryBuilder.class);

	/** The town. */
	private FreecycleGroup		town;

	/** The filter. */
	private String				filter;

	/** The include wanted. */
	private boolean				includeWanted			= false;

	/** The include offered. */
	private boolean				includeOffered			= true;

	/** The results per page. */
	private int					resultsPerPage			= FreecycleQueryBuilder.DEFAULT_ITEMS_PER_PAGE;

	/** The page number. */
	private int					pageNumber				= 1;

	/** The date start. */
	private LocalDate			dateStart;

	/** The date end. */
	private LocalDate			dateEnd;

	/**
	 * Instantiates a new freecycle query builder.
	 */
	public FreecycleQueryBuilder() {
	}

	/**
	 * Instantiates a new freecycle query builder.
	 *
	 * @param that
	 *            the that
	 */
	public FreecycleQueryBuilder(final FreecycleQueryBuilder that) {
		super(that);
		this.town = that.town;
		this.filter = that.filter;
		this.includeOffered = that.includeOffered;
		this.includeWanted = that.includeWanted;
		this.resultsPerPage = that.resultsPerPage;
		this.pageNumber = that.pageNumber;
		this.dateEnd = that.dateEnd;
		this.dateStart = that.dateStart;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.query.QueryBuilder#build()
	 */
	@Override
	public final HtmlParser build() throws ParserException, IOException {
		this.LOG.debug("Build parser");
		final StringBuilder builder = new StringBuilder("https://groups.freecycle.org/group/");
		builder.append(this.town.url());

		builder.append("/posts/search");
		final Map<String, String> parameters = new HashMap<>();
		parameters.put("page", String.valueOf(this.pageNumber));
		parameters.put("resultsperpage", String.valueOf(this.resultsPerPage));
		if (this.includeOffered) {
			parameters.put("include_offers", "on");
		}
		if (this.includeWanted) {
			parameters.put("include_wanteds", "on");
		}
		if (null != this.dateStart) {
			parameters.put("date_start", this.dateStart.toString());
		}
		if (null != this.dateEnd) {
			parameters.put("date_end", this.dateEnd.toString());
		}

		if (this.filter != null) {
			parameters.put("search_words", this.filter);
		}
		if (this.usesPOSTMethod()) {
			parameters.put("submit", "Search for Posts");
			return this.getPOSTConnection(builder.toString(), parameters);
		}
		return this.getGETConnection(builder.toString(), parameters);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.query.QueryBuilder#getGroup()
	 */
	@Override
	public final FreecycleGroup getGroup() {
		return this.town;
	}

	/**
	 * Gets the search criteria.
	 *
	 * @return the search criteria
	 */
	public final String getSearchCriteria() {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Searched for:");
		// if (null != filter) {
		// stringBuilder.append(this.filter);
		// } else {
		// stringBuilder.append("all items");
		// }
		// stringBuilder.append(". ");

		if (null != this.dateStart) {
			stringBuilder.append("From:");
			stringBuilder.append(this.dateStart);
			stringBuilder.append(". ");
		}
		if (null != this.dateEnd) {
			stringBuilder.append("From:");
			stringBuilder.append(this.dateEnd);
			stringBuilder.append(". ");

		}
		if (this.includeOffered) {
			stringBuilder.append("Including offers. ");
		}
		if (this.includeWanted) {
			stringBuilder.append("Including wanted. ");
		}
		return stringBuilder.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.query.QueryBuilder#getSearchWords()
	 */
	@Override
	public final String getSearchWords() {
		return this.filter;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.query.QueryBuilder#setDateStart(java.time.LocalDate )
	 */
	@Override
	public final FreecycleQueryBuilder setDateStart(final LocalDate date) {
		this.dateStart = date;
		return this;
	}

	/**
	 * Sets the include offered.
	 *
	 * @param include
	 *            the include
	 * @return the freecycle query builder
	 */
	public final FreecycleQueryBuilder setIncludeOffered(final boolean include) {
		this.includeOffered = include;
		return this;
	}

	/**
	 * Sets the include wanted.
	 *
	 * @param include
	 *            the include
	 * @return the freecycle query builder
	 */
	public final FreecycleQueryBuilder setIncludeWanted(final boolean include) {
		this.includeWanted = include;
		return this;
	}

	/**
	 * Sets the results per page.
	 *
	 * @param resultsPerPageNumber
	 *            the results per page
	 * @return the freecycle query builder
	 */
	public final FreecycleQueryBuilder setResultsPerPage(final int resultsPerPageNumber) {
		this.resultsPerPage = resultsPerPageNumber;
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.query.QueryBuilder#setSearchWords(java.lang. String)
	 */
	@Override
	public final FreecycleQueryBuilder setSearchWords(final String filterValue) {
		this.filter = filterValue;
		return this;
	}

	/**
	 * Sets the town.
	 *
	 * @param town2
	 *            the town2
	 * @return the freecycle query builder
	 */
	public final FreecycleQueryBuilder setTown(final FreecycleGroup town2) {
		this.town = town2;
		return this;
	}

}
