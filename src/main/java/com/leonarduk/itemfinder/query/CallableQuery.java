/*
 *
 */
package com.leonarduk.itemfinder.query;

import java.util.Set;
import java.util.concurrent.Callable;

import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.itemfinder.interfaces.ItemSearcher;

/**
 * The Class CallableQuery.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 28 Jan 2015
 */
public class CallableQuery implements Callable<Set<Item>> {

	/** The item searcher. */
	private final ItemSearcher itemSearcher;

	/** The query builder. */
	private final QueryBuilder queryBuilder;

	/** The limit. */
	private final Integer limit;

	/**
	 * Instantiates a new callable query.
	 *
	 * @param itemSearcherValue
	 *            the item searcher value
	 * @param queryBuilderInstance
	 *            the query builder instance
	 * @param aLimit
	 *            the a limit
	 */
	public CallableQuery(final ItemSearcher itemSearcherValue,
	        final QueryBuilder queryBuilderInstance, final Integer aLimit) {
		super();
		this.itemSearcher = itemSearcherValue;
		this.queryBuilder = queryBuilderInstance;
		this.limit = aLimit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public final Set<Item> call() throws Exception {
		return this.itemSearcher.findItems(this.queryBuilder, this.limit);
	}
}
