/**
 * 
 */
package com.leonarduk.itemfinder.freecycle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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
	Logger log = Logger.getLogger(FreecycleItemSearcher.class);

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
			Parser parser = new FreecycleQueryBuilder(town)
					.setSearchWords(searchTerm).useGet().build();
			log.info("Connect to " + parser);
			getPosts(items, parser);
		} catch (ParserException  | IOException e) {
			throw new ItemFinderException(e.getMessage());
		}
		return items;
	}

	public void getPosts(List<Item> items, Parser parser)
			throws ParserException {
		FreecycleItemScraper postProvider = new FreecycleItemScraper(parser);
		List<Post> posts = postProvider.getPosts();
		for (Post post : posts) {
			items.add(new FreecycleItem(postProvider.getFullPost(post)));
		}
	}

}
