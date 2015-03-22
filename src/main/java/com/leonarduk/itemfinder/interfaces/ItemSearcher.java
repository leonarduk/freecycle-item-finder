/*
 *
 */
package com.leonarduk.itemfinder.interfaces;

import java.util.Set;

import com.leonarduk.itemfinder.ItemFinderException;
import com.leonarduk.itemfinder.query.QueryBuilder;

/**
 * The Interface ItemSearcher.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 28 Jan 2015
 */
public interface ItemSearcher {

    /**
     * Find items.
     *
     * @param queryBuilder
     *            the query builder
     * @return the sets the
     * @throws ItemFinderException
     *             the item finder exception
     */
    Set<Item> findItems(QueryBuilder queryBuilder) throws ItemFinderException;

}
