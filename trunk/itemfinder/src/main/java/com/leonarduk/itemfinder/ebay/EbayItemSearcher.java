/**
 * 
 */
package com.leonarduk.itemfinder.ebay;

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

import java.util.List;

import com.adamshone.freecycle.FreecycleFeed;
import com.adamshone.freecycle.Post;
import com.adamshone.freecycle.PostProvider;
import com.adamshone.freecycle.PostReceiver;
import com.adamshone.freecycle.impl.FreecycleNewhamScraper;
import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.itemfinder.interfaces.Item.Condition;
import com.leonarduk.itemfinder.interfaces.ItemSearcher;

/**
 * @author stephen
 *
 */
public class EbayItemSearcher implements ItemSearcher {

	public EbayItemSearcher() throws ParserException {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.leonarduk.itemfinder.interfaces.ItemSearcher#findItems(java.lang.
	 * String, double, int)
	 */
	@Override
	public List<Item> findItems(String searchTerm, double maxPrice, int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.leonarduk.itemfinder.interfaces.ItemSearcher#findItems(java.lang.
	 * String, double, int, java.util.List)
	 */
	@Override
	public List<Item> findItems(String searchTerm, double maxPrice,
			int quantity, List<Condition> conditionsAccepted) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.leonarduk.itemfinder.interfaces.ItemSearcher#findItems(java.lang.
	 * String, double, java.util.List)
	 */
	@Override
	public List<Item> findItems(String searchTerm, double maxPrice,
			List<Condition> conditionsAccepted) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.leonarduk.itemfinder.interfaces.ItemSearcher#findItems(java.lang.
	 * String, java.util.List)
	 */
	@Override
	public List<Item> findItems(String searchTerm,
			List<Condition> conditionsAccepted) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.leonarduk.itemfinder.interfaces.ItemSearcher#findItems(java.lang.
	 * String)
	 */
	@Override
	public List<Item> findItems(String searchTerm) {
		// TODO Auto-generated method stub
		return null;
	}

}