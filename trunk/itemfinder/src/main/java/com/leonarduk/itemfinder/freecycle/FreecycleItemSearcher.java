/**
 *
 */
package com.leonarduk.itemfinder.freecycle;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;

import com.leonarduk.itemfinder.ItemFinderException;
import com.leonarduk.itemfinder.html.HtmlParser;
import com.leonarduk.itemfinder.interfaces.Item;
import com.leonarduk.itemfinder.interfaces.ItemSearcher;
import com.leonarduk.itemfinder.query.QueryBuilder;

/**
 * The Class FreecycleItemSearcher.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 29 Jan 2015
 */
public class FreecycleItemSearcher implements ItemSearcher {

	/** The log. */
	private final Logger log = Logger.getLogger(FreecycleItemSearcher.class);

	/** The em. */
	private final EntityManager em;

	/**
	 * Instantiates a new freecycle item searcher.
	 *
	 * @param entityManager
	 *            the entity manager
	 */
	public FreecycleItemSearcher(final EntityManager entityManager) {
		this.em = entityManager;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.interfaces.ItemSearcher#findItems(java.lang. String)
	 */
	@Override
	public final Set<Item> findItems(final QueryBuilder queryBuilder) throws ItemFinderException {
		try {
			final HtmlParser parser = queryBuilder.build();
			this.log.info("Connect to " + parser);
			return this.getPosts(parser, queryBuilder);
		}
		catch (ParserException | IOException e) {
			e.printStackTrace();
			throw new ItemFinderException(e.getMessage(), e);
		}
	}

	/**
	 * Gets the posts.
	 *
	 * @param parser
	 *            the parser
	 * @param queryBuilder
	 *            the query builder
	 * @return the posts
	 * @throws ParserException
	 *             the parser exception
	 */
	public final Set<Item> getPosts(final HtmlParser parser, final QueryBuilder queryBuilder)
			throws ParserException {
		final Set<Item> items = new HashSet<>();
		final FreecycleScraper scraper = new FreecycleScraper(parser);
		final List<Post> posts = scraper.getPosts();
		for (final Post post : posts) {
			if (this.shouldBeReported(post.getLink())) {
				final FreecycleItem fullPost = scraper.getFullPost(post);
				if (this.includePost(queryBuilder, fullPost)) {
					final EntityTransaction tx = this.em.getTransaction();
					tx.begin();
					this.em.persist(fullPost);
					items.add(fullPost);
					tx.commit();
				}
			}
		}
		return items;
	}

	/**
	 * This will query if this is included in the search terms and if an entry has been created on
	 * the DB or not, creating a {@link ReportableItem} entry if we are to send this one out.
	 *
	 * @param queryBuilder
	 *            the query builder
	 * @param post
	 *            the full post
	 * @return true, if successful
	 */
	public final boolean includePost(final QueryBuilder queryBuilder, final FreecycleItem post) {
		return post.getName().toLowerCase().contains(queryBuilder.getSearchWords().toLowerCase())
				|| post.getDescription().toLowerCase()
				.contains(queryBuilder.getSearchWords().toLowerCase());
	}

	/**
	 * Should be reported.
	 *
	 * @param link
	 *            the link
	 * @return true, if successful
	 */
	public final synchronized boolean shouldBeReported(final String link) {
		final EntityTransaction tx = this.em.getTransaction();
		ReportableItem test = this.em.find(ReportableItem.class, link);
		if (test == null) {
			try {
				tx.begin();
				test = new ReportableItem(link, false);
				this.em.persist(test);
				tx.commit();
				this.log.debug("persist successful");
				return true;
			}
			catch (final RuntimeException re) {
				this.log.error("persist failed", re);
				if (tx.isActive()) {
					tx.rollback();
				}
				throw re;
			}
		}
		return false;
	}

}
