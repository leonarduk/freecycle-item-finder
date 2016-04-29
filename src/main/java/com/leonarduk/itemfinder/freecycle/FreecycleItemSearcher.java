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

    /** The monitor. */
    private final Object dbLock = new Object();

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
     * @see
     * com.leonarduk.itemfinder.interfaces.ItemSearcher#findItems(java.lang.
     * String)
     */
    @Override
    public final Set<Item> findItems(
            final QueryBuilder queryBuilder,
            Integer limit) throws ItemFinderException {
        try {
            final HtmlParser parser = queryBuilder.build();
            this.log.info("Connect to " + parser);
            return this.getPosts(parser, queryBuilder, limit);
        }
        catch (ParserException | IOException e) {
            e.printStackTrace();
            throw new ItemFinderException(e.getMessage(), e);
        }
    }

    /**
     * Gets the latest post.
     *
     * @param groups
     *            the groups
     * @return the latest post
     */
    public final LatestPost getLatestPost(final FreecycleGroup groups) {
        LatestPost latest = this.em.find(LatestPost.class, groups);
        if (latest == null) {
            synchronized (this.dbLock) {
                latest = new LatestPost(groups);
                latest = this.saveLatestPost(groups, latest);
            }
            this.log.info("persist successful");
        }
        return latest;

    }

    /**
     * Gets the posts.
     *
     * @param parser
     *            the parser
     * @param queryBuilder
     *            the query builder
     * @param limit
     * @return the posts
     * @throws ParserException
     *             the parser exception
     */
    public final Set<Item> getPosts(
            final HtmlParser parser,
            final QueryBuilder queryBuilder,
            Integer limit) throws ParserException {
        final Set<Item> items = new HashSet<>();
        final FreecycleScraper scraper =
                new FreecycleScraper(parser, queryBuilder.getGroup());
        final LatestPost latest = this.getLatestPost(queryBuilder.getGroup());
        int lastIndex = latest.getLatestPostNumber();

        final List<Post> posts = scraper.getPosts();
        for (final Post post : posts) {
            if (limit < 1) {
                break;
            }
            limit--;
            if (post.getPostId() > lastIndex) {
                lastIndex = post.getPostId();
            }
            log.info("Process " + post.getText());
            if (this.shouldBeReported(post, latest)) {

                final FreecycleItem fullPost = scraper.getFullPost(post);
                if (this.includePost(queryBuilder, fullPost)) {
                    items.add(fullPost);
                }
            }
        }
        this.saveLatestPost(queryBuilder.getGroup(), latest);
        return items;
    }

    /**
     * This will query if this is included in the search terms and if an entry
     * has been created on the DB or not, creating a {@link ReportableItem}
     * entry if we are to send this one out.
     *
     * @param queryBuilder
     *            the query builder
     * @param post
     *            the full post
     * @return true, if successful
     */
    public final boolean includePost(
            final QueryBuilder queryBuilder,
            final FreecycleItem post) {
        return post.getName().toLowerCase()
                .contains(queryBuilder.getSearchWords().toLowerCase())
               || post.getDescription().toLowerCase()
                       .contains(queryBuilder.getSearchWords().toLowerCase());
    }

    /**
     * Save latest post.
     *
     * @param groups
     *            the groups
     * @param latest
     *            the latest
     * @return the latest post
     */
    private LatestPost saveLatestPost(
            final FreecycleGroup groups,
            final LatestPost latest) {
        final EntityTransaction tx = this.em.getTransaction();
        try {
            tx.begin();
            this.em.persist(latest);
            tx.commit();
        }
        catch (final RuntimeException re) {
            this.log.error("persist failed", re);
            if (tx.isActive()) {
                tx.rollback();
            }
            throw re;
        }
        return latest;
    }

    /**
     * Should be reported.
     *
     * @param post
     *            the link
     * @param latest
     *            the latest
     * @return true, if successful
     */
    public final boolean shouldBeReported(
            final Post post,
            final LatestPost latest) {
        final String link = post.getLink();
        final boolean shouldBeReported =
                post.getPostId() > latest.getLatestPostNumber();

        post.getFreecycleGroup();
        this.log.info("report?: " + shouldBeReported + " ID: "
                      + post.getPostId() + " Last: "
                      + latest.getLatestPostNumber() + " URL:" + link);

        return shouldBeReported;
    }
}
