/**
 * 
 */
package com.leonarduk.itemfinder.freecycle;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;

import com.leonarduk.itemfinder.ItemFinderException;
import com.leonarduk.itemfinder.html.HtmlParser;
import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.itemfinder.interfaces.ItemSearcher;
import com.leonarduk.itemfinder.query.QueryBuilder;

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
			throw new ItemFinderException(e.getMessage(), e);
		}
	}

	public Set<Item> getPosts(HtmlParser parser, QueryBuilder queryBuilder)
			throws ParserException {
		Set<Item> items = new HashSet<>();
		FreecycleScraper scraper = new FreecycleScraper(parser);
		List<Post> posts = scraper.getPosts();
		for (Post post : posts) {
			FreecycleItem fullPost = scraper.getFullPost(post);
			if (includePost(queryBuilder, fullPost)) {
				items.add(fullPost);
			}
		}
		return items;
	}

	public boolean includePost(QueryBuilder queryBuilder, FreecycleItem fullPost) {
		return fullPost.getName().toLowerCase()
				.contains(queryBuilder.getSearchWords().toLowerCase())
				|| fullPost.getDescription().toLowerCase()
						.contains(queryBuilder.getSearchWords().toLowerCase());
	}

}
