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

import com.leonarduk.core.email.EmailException;
import com.leonarduk.core.email.EmailSender;
import com.leonarduk.core.email.EmailSession;
import com.leonarduk.core.format.Formatter;
import com.leonarduk.itemfinder.ItemFinderException;
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

    /** The Constant MAX_POSTS_PER_SEARCH. */
    public static final int MAX_POSTS_PER_SEARCH = 100;
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
                        formatter, em, resultsPerPage,
                        config.getSearchItemLimit());
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
        }, groups, 1, formatter, em, resultsPerPage,
                config.getSearchItemLimit()));
        return emailBody.toString();
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
     * @param emailSender
     *            the email sender
     * @param session
     *            the session
     * @param toEmail
     *            the to email
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
            final boolean failIfEmpty,
            EmailSender emailSender,
            EmailSession session,
            String[] toEmail)
            throws InterruptedException,
            ExecutionException,
            ParserException,
            IOException {
        SearchReporter.LOGGER.info("generateSearch:" + Arrays.asList(searches)
                                   + " - " + Arrays.asList(groups));

        final EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            final StringBuffer wantedItems = new StringBuffer();
            final StringBuffer otherItems = new StringBuffer();

            processAllGroups(config, searches, groups, formatter, em,
                    wantedItems, otherItems);

            sendEmail(config, searches, groups, formatter, failIfEmpty,
                    emailSender, session, toEmail, wantedItems, otherItems);
            tx.commit();
        }
        catch (final Throwable e) {
            tx.rollback();
            SearchReporter.LOGGER.fatal("Unhandled error:", e);
            throw new RuntimeException("Error in search", e);
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
                em.persist(latest);
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
     * Process all groups.
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
     * @param wantedItems
     *            the wanted items
     * @param otherItems
     *            the other items
     */
    public void processAllGroups(
            final FreecycleConfig config,
            final String[] searches,
            final FreecycleGroup[] groups,
            final Formatter formatter,
            final EntityManager em,
            final StringBuffer wantedItems,
            final StringBuffer otherItems) {
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

        Integer processed = 0;
        for (final FreecycleGroup freecycleGroup : groups) {
            LatestPost latest = null;
            try {
                latest =
                        processOneGroup(searches, formatter, em, queryBuilder,
                                wantedItems, otherItems, freecycleGroup,
                                processed);
            }
            catch (Throwable e) {
                LOGGER.error("Error with " + freecycleGroup.name(), e);
            }
            if (null != latest) {
                em.persist(latest);
            }
        }
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
     * @param processed
     *            the processed
     * @return the int
     * @throws ParserException
     *             the parser exception
     */
    public int processIndividualPost(
            final String[] searches,
            final Formatter formatter,
            final StringBuffer wantedItems,
            final StringBuffer otherItems,
            final FreecycleScraper scraper,
            final LatestPost latest,
            final int lastIndex,
            final Post post,
            Integer processed) throws ParserException {
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
     * Process one group.
     *
     * @param searches
     *            the searches
     * @param formatter
     *            the formatter
     * @param em
     *            the em
     * @param queryBuilder
     *            the query builder
     * @param wantedItems
     *            the wanted items
     * @param otherItems
     *            the other items
     * @param freecycleGroup
     *            the freecycle group
     * @param processed
     *            the processed
     * @return the latest post
     * @throws ParserException
     *             the parser exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public LatestPost processOneGroup(
            final String[] searches,
            final Formatter formatter,
            final EntityManager em,
            final FreecycleQueryBuilder queryBuilder,
            final StringBuffer wantedItems,
            final StringBuffer otherItems,
            final FreecycleGroup freecycleGroup,
            Integer processed) throws ParserException, IOException {
        LatestPost latest;
        final FreecycleQueryBuilder queryBuilderCopy =
                new FreecycleQueryBuilder(queryBuilder);
        queryBuilderCopy.setTown(freecycleGroup);
        final HtmlParser parser = queryBuilderCopy.build();

        final FreecycleScraper scraper =
                new FreecycleScraper(parser, freecycleGroup);
        latest = this.getLatestPost(freecycleGroup, em);
        int lastIndex = latest.getLatestPostNumber();
        final List<Post> posts = scraper.getPosts();

        for (final Post post : posts) {
            if (processed > MAX_POSTS_PER_SEARCH) {
                return latest;
            }
            lastIndex =
                    this.processIndividualPost(searches, formatter,
                            wantedItems, otherItems, scraper, latest,
                            lastIndex, post, processed);
            processed++;

        }
        return latest;
    }

    /**
     * Send email.
     *
     * @param config
     *            the config
     * @param searches
     *            the searches
     * @param groups
     *            the groups
     * @param formatter
     *            the formatter
     * @param failIfEmpty
     *            the fail if empty
     * @param emailSender
     *            the email sender
     * @param session
     *            the session
     * @param toEmail
     *            the to email
     * @param wantedItems
     *            the wanted items
     * @param otherItems
     *            the other items
     * @throws EmailException
     *             the email exception
     */
    public void sendEmail(
            final FreecycleConfig config,
            final String[] searches,
            final FreecycleGroup[] groups,
            final Formatter formatter,
            final boolean failIfEmpty,
            EmailSender emailSender,
            EmailSession session,
            String[] toEmail,
            final StringBuffer wantedItems,
            final StringBuffer otherItems) throws EmailException {
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
            emailBody.append(formatter.formatSubHeader("Searched "
                                                       + Arrays.asList(groups)
                                                       + " for everything"));
            emailBody.append(otherItems);

            emailSender.sendMessage(
                    config.getProperty("freecycle.email.from.email"),
                    config.getProperty("freecycle.email.from.name"),
                    "Matching Freecycle items found", emailBody.toString(),
                    true, session, toEmail);

        }
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
