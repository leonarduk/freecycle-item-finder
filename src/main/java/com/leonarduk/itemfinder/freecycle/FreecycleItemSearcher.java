/**
 * 
 */
package com.leonarduk.itemfinder.freecycle;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

import com.adamshone.freecycle.Post;
import com.leonarduk.freecycle.FreecycleItemScraper;
import com.leonarduk.freecycle.FreecycleQueryBuilder;
import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.itemfinder.interfaces.Item.Condition;
import com.leonarduk.itemfinder.interfaces.ItemSearcher;

/**
 * @author stephen
 *
 */
public class FreecycleItemSearcher implements ItemSearcher {

	private String town;

	public FreecycleItemSearcher(String town) {
		this.town = town;
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
	public List<Item> findItems(String searchTerm) throws ItemFinderException {

		List<Item> items = new ArrayList<Item>();
		try {
			String url = new FreecycleQueryBuilder(town).setSearchWords(
					searchTerm).build();
			getPosts(items, url);
		} catch (ParserException e) {
			throw new ItemFinderException(e.getMessage());
		}
		return items;
	}

	public void getPosts(List<Item> items, String url) throws ParserException {
		Parser parser = new Parser(url);
		FreecycleItemScraper postProvider = new FreecycleItemScraper(parser);
		List<Post> posts = postProvider.getPosts();
		for (Post post : posts) {
			items.add(new FreecycleItem(postProvider.getFullPost(post)));
		}
	}

}
