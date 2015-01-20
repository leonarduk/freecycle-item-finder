package com.leonarduk.itemfinder.interfaces;

import java.util.List;

import com.leonarduk.itemfinder.ItemFinderException;

public interface ItemSearcher {

	public List<Item> findItems(String searchTerm) throws ItemFinderException;

}
