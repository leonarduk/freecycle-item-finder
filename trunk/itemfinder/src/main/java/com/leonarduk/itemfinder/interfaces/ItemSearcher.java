package com.leonarduk.itemfinder.interfaces;

import java.util.List;

import com.leonarduk.itemfinder.interfaces.Item.Condition;

public interface ItemSearcher {
	public List<Item> findItems(String searchTerm, double maxPrice, int quantity);

	public List<Item> findItems(String searchTerm, double maxPrice,
			int quantity, List<Condition> conditionsAccepted);

	public List<Item> findItems(String searchTerm, double maxPrice,
			List<Condition> conditionsAccepted);

	public List<Item> findItems(String searchTerm,
			List<Condition> conditionsAccepted);

	public List<Item> findItems(String searchTerm);

}
