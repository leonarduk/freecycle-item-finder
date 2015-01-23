package com.leonarduk.itemfinder.interfaces;

import java.util.Set;

import com.leonarduk.itemfinder.ItemFinderException;
import com.leonarduk.itemfinder.query.QueryBuilder;

public interface ItemSearcher {

	Set<Item> findItems(QueryBuilder queryBuilder) throws ItemFinderException;

}
