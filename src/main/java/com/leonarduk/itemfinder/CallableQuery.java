package com.leonarduk.itemfinder;

import java.util.List;
import java.util.concurrent.Callable;

import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.itemfinder.interfaces.ItemSearcher;

public class CallableQuery implements Callable<List<Item>> {

	private ItemSearcher itemSearcher;
	private QueryBuilder queryBuilder;

	public CallableQuery(ItemSearcher itemSearcher, QueryBuilder queryBuilder) {
		super();
		this.itemSearcher = itemSearcher;
		this.queryBuilder = queryBuilder;
	}

	@Override
	public List<Item> call() throws Exception {
		return itemSearcher.findItems(queryBuilder);
	}
}
