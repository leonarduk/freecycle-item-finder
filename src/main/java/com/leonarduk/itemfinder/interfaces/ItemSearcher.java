package com.leonarduk.itemfinder.interfaces;

import java.util.List;

import com.leonarduk.itemfinder.ItemFinderException;
import com.leonarduk.itemfinder.QueryBuilder;

public interface ItemSearcher {

	List<Item> findItems(QueryBuilder queryBuilder) throws ItemFinderException;

}
