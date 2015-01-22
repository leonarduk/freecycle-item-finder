package com.leonarduk.itemfinder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.itemfinder.interfaces.ItemSearcher;

public class CallableQuery implements Callable<Set<Item>> {

	private ItemSearcher itemSearcher;
	private QueryBuilder queryBuilder;

	public CallableQuery(ItemSearcher itemSearcher, QueryBuilder queryBuilder) {
		super();
		this.itemSearcher = itemSearcher;
		this.queryBuilder = queryBuilder;
	}

	@Override
	public Set<Item> call() throws Exception {
		return itemSearcher.findItems(queryBuilder);
	}
}
