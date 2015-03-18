/**
 * All rights reserved. @Leonard UK Ltd.
 */
package com.leonarduk.itemfinder.query;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;

import com.leonarduk.core.email.EmailSender;
import com.leonarduk.core.email.EmailSession;
import com.leonarduk.itemfinder.ItemFinderException;
import com.leonarduk.itemfinder.format.Formatter;
import com.leonarduk.itemfinder.freecycle.FreecycleConfig;
import com.leonarduk.itemfinder.freecycle.FreecycleGroup;
import com.leonarduk.itemfinder.freecycle.FreecycleItem;
import com.leonarduk.itemfinder.freecycle.FreecycleQueryBuilder;
import com.leonarduk.itemfinder.freecycle.FreecycleScraper;
import com.leonarduk.itemfinder.freecycle.LatestPost;
import com.leonarduk.itemfinder.freecycle.Post;
import com.leonarduk.itemfinder.freecycle.PostType;
import com.leonarduk.itemfinder.html.HtmlParser;
import com.leonarduk.itemfinder.interfaces.Item;

/**
 * The Class SearchReporter.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 30 Jan 2015
 */
public final class SearchReporter {

    /** The Constant LOGGER. */
    static final Logger LOGGER = Logger.getLogger(SearchReporter.class);

    /**
     * Generate report.
     *
     * @param config
     *            the config
     * @param searches
     *            the searches
     * @param groups
     *            the groups
     * @param formatter
     *            the formatter
     * @param em
     *            the em
     * @param reporter
     *            the reporter
     * @param failIfEmpty
     *            the fail if empty
     * @return the string
     * @throws InterruptedException
     *             the interrupted exception
     * @throws ExecutionException
     *             the execution exception
     * @throws ItemFinderException
     *             the item finder exception
     */
    public static String generateReport(
            final FreecycleConfig config,
            final String[] searches,
            final FreecycleGroup[] groups,
            final Formatter formatter,
            final EntityManager em,
            final QueryReporter reporter,
            final boolean failIfEmpty)
            throws InterruptedException,
            ExecutionException,
            ItemFinderException {
        final StringBuilder emailBody = new StringBuilder();

        final Integer resultsPerPage = config.getResultsPerPage();

        final String runReport =
                reporter.runReport(searches, groups,
                        config.getIntegerProperty("freecycle.search.period"),
                        formatter, em, resultsPerPage);
        if (failIfEmpty && runReport.equals(QueryReporter.NO_RESULTS)) {
            throw new ItemFinderException("No results found for " + searches);
        }
        final String heading =
                "Searched " + Arrays.asList(groups) + " for " + " "
                        + Arrays.asList(searches);
        emailBody.append(formatter.formatSubHeader(heading));
        emailBody.append(runReport);
        emailBody.append("<hr/>");
        emailBody.append(formatter.formatSubHeader("Searched "
                                                   + Arrays.asList(groups)
                                                   + " for everything"));
        emailBody.append(reporter.runReport(new String[] {
            ""
        }, groups, 1, formatter, em, resultsPerPage));
        return emailBody.toString();
    }

    /**
     * Send report.
     *
     * @param config
     *            the config
     * @param emailBody
     *            the email body
     */
    public static void sendReport(
            final FreecycleConfig config,
            final String emailBody) {
        final String[] toEmail = config.getArrayProperty("freecycle.email.to");
        final String user = config.getProperty("freecycle.email.user");
        final String server = config.getProperty("freecycle.email.server");
        final String password = config.getProperty("freecycle.email.password");
        final String port = config.getProperty("freecycle.email.port");

        final EmailSender emailSender = new EmailSender();

        final EmailSession session =
                new EmailSession(user, password, server, port);
        emailSender.sendMessage(
                config.getProperty("freecycle.email.from.email"),
                config.getProperty("freecycle.email.from.name"),
                "Matching Freecycle items found", emailBody.toString(), true,
                session, toEmail);
    }

    /** The db lock. */
    private final Object dbLock = new Object();

    /**
     * Instantiates a new search reporter.
     */
    public SearchReporter() {
    }

    /**
     * Adds the post details.
     *
     * @param formatter
     *            the formatter
     * @param item
     *            the item
     * @return the string
     */
    public String addPostDetails(final Formatter formatter, final Item item) {
        final String spacer = formatter.getNewLine();
        final StringBuilder emailBodyBuilder = new StringBuilder(spacer);
        emailBodyBuilder.append(formatter.getNewSection());
        emailBodyBuilder.append(spacer);

        final String header =
                formatter.formatLink(item.getLink(), item.getName()) + " - "
                        + item.getLocation() + " Posted: "
                        + item.getPostedDate().toString();

        emailBodyBuilder.append(formatter.formatSubHeader(header));
        emailBodyBuilder.append(spacer);

        emailBodyBuilder.append(item.getDescription());

        emailBodyBuilder.append(item.getExtraHtml());
        emailBodyBuilder.append(spacer);
        return emailBodyBuilder.toString();
    }

    /**
     * Generate search.
     *
     * @param config
     *            the config
     * @param searches
     *            the searches
     * @param groups
     *            the groups
     * @param formatter
     *            the formatter
     * @param em
     *            the em
     * @param reporter
     *            the reporter
     * @param failIfEmpty
     *            the fail if empty
     * @throws InterruptedException
     *             the interrupted exception
     * @throws ExecutionException
     *             the execution exception
     * @throws ParserException
     *             the parser exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void generateSearch(
            final FreecycleConfig config,
            final String[] searches,
            final FreecycleGroup[] groups,
            final Formatter formatter,
            final EntityManager em,
            final QueryReporter reporter,
            final boolean failIfEmpty)
            throws InterruptedException,
            ExecutionException,
            ParserException,
            IOException {
        SearchReporter.LOGGER.info("generateSearch:" + Arrays.asList(searches)
                                   + " - " + Arrays.asList(groups));

        final EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            final long timeperiod =
                    config.getIntegerProperty("freecycle.search.period");
            final int resultsPerPageNumber = config.getResultsPerPage();
            final FreecycleQueryBuilder queryBuilder =
                    new FreecycleQueryBuilder()
                            .setIncludeWanted(false)
                            .setIncludeOffered(true)
                            .setDateStart(
                                    LocalDate.now().minus(timeperiod,
                                            ChronoUnit.DAYS)).useGet()
                            .setResultsPerPage(resultsPerPageNumber);
            final StringBuilder wantedItems = new StringBuilder();
            final StringBuilder otherItems = new StringBuilder();

            for (final FreecycleGroup freecycleGroup : groups) {

                final FreecycleQueryBuilder queryBuilderCopy =
                        new FreecycleQueryBuilder(queryBuilder);
                queryBuilderCopy.setTown(freecycleGroup);
                final HtmlParser parser = queryBuilderCopy.build();

                final FreecycleScraper scraper =
                        new FreecycleScraper(parser, freecycleGroup);
                final LatestPost latest =
                        this.getLatestPost(freecycleGroup, em);
                int lastIndex = latest.getLatestPostNumber();
                final List<Post> posts = scraper.getPosts();

                for (final Post post : posts) {
                    lastIndex =
                            this.processIndividualPost(searches, formatter,
                                    wantedItems, otherItems, scraper, latest,
                                    lastIndex, post);
                }
                this.saveLatestPost(latest, em);

            }

            final StringBuilder emailBody = new StringBuilder();

            if (failIfEmpty && (wantedItems.length() == 0)) {
                SearchReporter.LOGGER.info("No results - not sending message");

            }
            else {
                final String heading =
                        "Searched " + Arrays.asList(groups) + " for " + " "
                                + Arrays.asList(searches);
                emailBody.append(formatter.formatSubHeader(heading));
                emailBody.append(wantedItems);
                emailBody.append("<hr/>");
                emailBody
                        .append(formatter.formatSubHeader("Searched "
                                                          + Arrays.asList(groups)
                                                          + " for everything"));
                emailBody.append(otherItems);

                final String text = emailBody.toString();
                SearchReporter.sendReport(config, text);
            }
            tx.commit();
        }
        catch (final Throwable e) {
            tx.rollback();
            SearchReporter.LOGGER.fatal("Unhandled error:", e);
        }
    }

    /**
     * Gets the latest post.
     *
     * @param groups
     *            the groups
     * @param em
     *            the em
     * @return the latest post
     */
    public LatestPost getLatestPost(
            final FreecycleGroup groups,
            final EntityManager em) {
        LatestPost latest = em.find(LatestPost.class, groups);
        if (latest == null) {
            synchronized (this.dbLock) {
                latest = new LatestPost(groups);
                latest = this.saveLatestPost(latest, em);
            }
            SearchReporter.LOGGER.info("persist successful");
        }
        return latest;

    }

    /**
     * This will query if this is included in the search terms and if an entry
     * has been created on the DB or not, creating a ReportableItem entry if we
     * are to send this one out.
     *
     * @param queryBuilder
     *            the query builder
     * @param post
     *            the full post
     * @return true, if successful
     */
    public boolean includePost(
            final QueryBuilder queryBuilder,
            final FreecycleItem post) {
        return post.getName().toLowerCase()
                .contains(queryBuilder.getSearchWords().toLowerCase())
               || post.getDescription().toLowerCase()
                       .contains(queryBuilder.getSearchWords().toLowerCase());
    }

    /**
     * Include post.
     *
     * @param searches
     *            the searches
     * @param fullPost
     *            the full post
     * @return true, if successful
     */
    private boolean includePost(
            final String[] searches,
            final FreecycleItem fullPost) {
        for (final String term : searches) {
            if (fullPost.toString().toLowerCase().contains(term.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Process individual post.
     *
     * @param searches
     *            the searches
     * @param formatter
     *            the formatter
     * @param wantedItems
     *            the wanted items
     * @param otherItems
     *            the other items
     * @param scraper
     *            the scraper
     * @param latest
     *            the latest
     * @param lastIndex
     *            the last index
     * @param post
     *            the post
     * @return the int
     * @throws ParserException
     *             the parser exception
     */
    public int processIndividualPost(
            final String[] searches,
            final Formatter formatter,
            final StringBuilder wantedItems,
            final StringBuilder otherItems,
            final FreecycleScraper scraper,
            final LatestPost latest,
            final int lastIndex,
            final Post post) throws ParserException {
        if (this.shouldBeReported(post, latest)) {
            final FreecycleItem fullPost = scraper.getFullPost(post);
            if (this.includePost(searches, fullPost)) {
                wantedItems.append(this.addPostDetails(formatter, fullPost));
                if (post.getPostId() > lastIndex) {
                    latest.setLatestPostNumber(post.getPostId());
                }
            }
            else {
                otherItems.append(this.addPostDetails(formatter, fullPost));
            }
        }
        return lastIndex;
    }

    /**
     * Save latest post.
     *
     * @param latest
     *            the latest
     * @param em
     *            the em
     * @return the latest post
     */
    private LatestPost saveLatestPost(
            final LatestPost latest,
            final EntityManager em) {
        final EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(latest);
            tx.commit();
        }
        catch (final RuntimeException re) {
            SearchReporter.LOGGER.error("persist failed", re);
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
     *            the post
     * @param latest
     *            the latest
     * @return true, if successful
     */
    public boolean shouldBeReported(final Post post, final LatestPost latest) {
        final String link = post.getLink();
        if (post.getPostType() != PostType.OFFER) {
            return false;
        }
        final boolean shouldBeReported =
                post.getPostId() > latest.getLatestPostNumber();

        post.getFreecycleGroup();
        SearchReporter.LOGGER.info("report?: " + shouldBeReported + " ID: "
                                   + post.getPostId() + " Last: "
                                   + latest.getLatestPostNumber() + " URL:"
                                   + link);

        return shouldBeReported;
    }

}
