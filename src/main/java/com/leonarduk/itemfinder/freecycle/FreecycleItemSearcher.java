/**
 * 
 */
package com.leonarduk.itemfinder.freecycle;

import java.util.ArrayList;
import java.util.List;

import org.apache.derby.tools.sysinfo;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

import com.adamshone.freecycle.Post;
import com.adamshone.freecycle.PostProvider;
import com.adamshone.freecycle.impl.FreecycleNewhamScraper;
import com.leonarduk.freecycle.FreecycleItemScraper;
import com.leonarduk.freecycle.FullPost;
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

	public FreecycleItemScraper getFreecycleParser() throws ParserException {

		String freecycleUrl = "http://groups.freecycle.org/freecycle-"
				+ town
				+ "/posts/all?page=1&amp;resultsperpage=50&amp;showall=off&amp;include_offers=off&amp;include_wanteds=off&amp;include_receiveds=off&amp;include_takens=on";
		Parser parser = new Parser(freecycleUrl);
		FreecycleItemScraper postProvider = new FreecycleItemScraper(parser);
		return postProvider;
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
		FreecycleItemScraper parser;
		List<Item> items = new ArrayList<Item>();
		try {
			parser = getFreecycleParser();
			List<Post> posts = parser.getPosts();
			for (Post post : posts) {
				items.add(new FreecycleItem(parser.getFullPost(post)));
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}

}
