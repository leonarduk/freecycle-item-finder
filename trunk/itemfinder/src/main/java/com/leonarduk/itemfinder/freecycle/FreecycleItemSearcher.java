/**
 * 
 */
package com.leonarduk.itemfinder.freecycle;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

import com.adamshone.freecycle.Post;
import com.leonarduk.freecycle.FreecycleItemScraper;
import com.leonarduk.itemfinder.HtmlParser;
import com.leonarduk.itemfinder.ItemFinderException;
import com.leonarduk.itemfinder.QueryBuilder;
import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.itemfinder.interfaces.ItemSearcher;

/**
 * @author stephen
 *
 */
public class FreecycleItemSearcher implements ItemSearcher {
	Logger log = Logger.getLogger(FreecycleItemSearcher.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.leonarduk.itemfinder.interfaces.ItemSearcher#findItems(java.lang.
	 * String)
	 */
	@Override
	public Set<Item> findItems(QueryBuilder queryBuilder)
			throws ItemFinderException {
		try {
			HtmlParser parser = queryBuilder.build();
			log.info("Connect to " + parser);
			return getPosts(parser, queryBuilder);
		} catch (ParserException | IOException e) {
			e.printStackTrace();
			throw new ItemFinderException(e.getMessage(),e);
		}
	}

	private Set<Item> getPosts(HtmlParser parser, QueryBuilder queryBuilder)
			throws ParserException {
		Set<Item> items = new HashSet<>();
		FreecycleItemScraper scraper = new FreecycleItemScraper(parser);
		List<Post> posts = scraper.getPosts();
		for (Post post : posts) {
			FreecycleItem fullPost = new FreecycleItem(
					scraper.getFullPost(post));
			if (fullPost.getName().toLowerCase()
					.contains(queryBuilder.getSearchWords().toLowerCase())
					|| fullPost
							.getDescription()
							.toLowerCase()
							.contains(
									queryBuilder.getSearchWords().toLowerCase())) {
				items.add(fullPost);
			}
		}
		return items;
	}

}
