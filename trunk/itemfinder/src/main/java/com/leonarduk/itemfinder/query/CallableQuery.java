/*
 *
 */
package com.leonarduk.itemfinder.query;

import java.util.Set;
import java.util.concurrent.Callable;

import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.itemfinder.interfaces.ItemSearcher;

// TODO: Auto-generated Javadoc

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
	private final ItemSearcher	itemSearcher;

	/** The query builder. */
	private final QueryBuilder	queryBuilder;

	/**
	 * Instantiates a new callable query.
	 *
	 * @param itemSearcher
	 *            the item searcher
	 * @param queryBuilder
	 *            the query builder
	 */
	public CallableQuery(final ItemSearcher itemSearcher, final QueryBuilder queryBuilder) {
		super();
		this.itemSearcher = itemSearcher;
		this.queryBuilder = queryBuilder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Set<Item> call() throws Exception {
		return this.itemSearcher.findItems(this.queryBuilder);
	}
}
