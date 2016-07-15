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

import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;

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
import com.leonarduk.webscraper.core.email.EmailException;
import com.leonarduk.webscraper.core.email.EmailSender;
import com.leonarduk.webscraper.core.email.EmailSession;
import com.leonarduk.webscraper.core.email.impl.EmailSessionImpl;
import com.leonarduk.webscraper.core.format.Formatter;
import com.sun.mail.smtp.SMTPSendFailedException;

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
	private static final int	MAX_POSTS_PER_SEARCH	= 100;
	/** The Constant LOGGER. */
	private static final Logger	LOGGER					= Logger.getLogger(SearchReporter.class);

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
	String addPostDetails(final Formatter formatter, final Item item) {
		final String spacer = formatter.getNewLine();
		final StringBuilder emailBodyBuilder = new StringBuilder(spacer);
		emailBodyBuilder.append(formatter.getNewSection());
		emailBodyBuilder.append(spacer);

		final String header = formatter.formatLink(item.getLink(), item.getName()) + " - "
		        + item.getLocation() + " Posted: " + item.getPostedDate().toString();

		emailBodyBuilder.append(formatter.formatSubHeader(header));
		emailBodyBuilder.append(spacer);

		emailBodyBuilder.append(item.getDescription());

		emailBodyBuilder.append(item.getExtraHtml());
		emailBodyBuilder.append(spacer);
		return emailBodyBuilder.toString();
	}

	/**
	 * Format email.
	 *
	 * @param searches
	 *            the searches
	 * @param groups
	 *            the groups
	 * @param formatter
	 *            the formatter
	 * @param failIfEmpty
	 *            the fail if empty
	 * @param wantedItems
	 *            the wanted items
	 * @param otherItems
	 *            the other items
	 * @return the string builder
	 */
	private StringBuilder formatEmail(final String[] searches, final FreecycleGroup[] groups,
	        final Formatter formatter, final boolean failIfEmpty, final StringBuffer wantedItems,
	        final StringBuffer otherItems) {
		final StringBuilder emailBody = new StringBuilder();

		if (failIfEmpty && (wantedItems.length() == 0)) {
			SearchReporter.LOGGER.info("No results - not sending message");
			return null;
		}
		emailBody.append(wantedItems);
		emailBody.append("<hr/>");
		final String heading = "Searched " + Arrays.asList(groups) + " for " + " "
		        + Arrays.asList(searches);
		emailBody.append(formatter.formatSmall(heading));
		emailBody.append("<hr/>");
		emailBody.append(otherItems);
		return emailBody;
	}

	/**
	 * Generate search.
	 *
	 * @param config
	 *            the config
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
	 * @return the string builder
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ExecutionException
	 *             the execution exception
	 * @throws ParserException
	 *             the parser exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public StringBuilder generateSearch(final FreecycleConfig config, final Formatter formatter,
	        final EntityManager em, final QueryReporter reporter, final boolean failIfEmpty,
	        final EmailSender emailSender)
	                throws InterruptedException, ExecutionException, ParserException, IOException {
		final String[] searches = config.getSearchTerms();

		final String[] groupNames = config.getSearchGroupNames();
		final FreecycleGroup[] groups = new FreecycleGroup[groupNames.length];
		for (int i = 0; i < groups.length; i++) {
			groups[i] = FreecycleGroup.valueOf(groupNames[i]);
		}

		SearchReporter.LOGGER
		        .info("generateSearch:" + Arrays.asList(searches) + " - " + Arrays.asList(groups));

		final StringBuffer wantedItems = new StringBuffer();
		final StringBuffer otherItems = new StringBuffer();

		this.processAllGroups(config, searches, groups, formatter, em, wantedItems, otherItems);

		final StringBuilder emailBody = this.formatEmail(searches, groups, formatter, failIfEmpty,
		        wantedItems, otherItems);

		return emailBody;
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
	private LatestPost getLatestPost(final FreecycleGroup groups, final EntityManager em) {
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
	 * Include post.
	 *
	 * @param searches
	 *            the searches
	 * @param fullPost
	 *            the full post
	 * @return true, if successful
	 */
	private boolean includePost(final String[] searches, final FreecycleItem fullPost) {
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
	private void processAllGroups(final FreecycleConfig config, final String[] searches,
	        final FreecycleGroup[] groups, final Formatter formatter, final EntityManager em,
	        final StringBuffer wantedItems, final StringBuffer otherItems) {
		final int timeperiod = config.getSearchPeriod().intValue();
		final int resultsPerPageNumber = config.getResultsPerPage().intValue();
		final FreecycleQueryBuilder queryBuilder = new FreecycleQueryBuilder()
		        .setIncludeWanted(false).setIncludeOffered(true)
		        .setDateStart(LocalDate.now().minus(timeperiod, ChronoUnit.DAYS)).useGet()
		        .setResultsPerPage(resultsPerPageNumber);

		final Integer processed = Integer.valueOf(0);
		for (final FreecycleGroup freecycleGroup : groups) {
			LatestPost latest = null;
			try {
				latest = this.processOneGroup(searches, formatter, em, queryBuilder, wantedItems,
				        otherItems, freecycleGroup, processed.intValue());
			}
			catch (final Throwable e) {
				SearchReporter.LOGGER.error("Error with " + freecycleGroup.name(), e);
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
	 * @return the int
	 * @throws ParserException
	 *             the parser exception
	 */
	private int processIndividualPost(final String[] searches, final Formatter formatter,
	        final StringBuffer wantedItems, final StringBuffer otherItems,
	        final FreecycleScraper scraper, final LatestPost latest, final int lastIndex,
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
	private LatestPost processOneGroup(final String[] searches, final Formatter formatter,
	        final EntityManager em, final FreecycleQueryBuilder queryBuilder,
	        final StringBuffer wantedItems, final StringBuffer otherItems,
	        final FreecycleGroup freecycleGroup, final int processed)
	                throws ParserException, IOException {
		LatestPost latest;
		final FreecycleQueryBuilder queryBuilderCopy = new FreecycleQueryBuilder(queryBuilder);
		queryBuilderCopy.setTown(freecycleGroup);
		final HtmlParser parser = queryBuilderCopy.build();

		final FreecycleScraper scraper = new FreecycleScraper(parser, freecycleGroup);
		latest = this.getLatestPost(freecycleGroup, em);
		int lastIndex = latest.getLatestPostNumber();
		final List<Post> posts = scraper.getPosts();

		int currentProcessedTotal = processed;
		for (final Post post : posts) {
			if (currentProcessedTotal > SearchReporter.MAX_POSTS_PER_SEARCH) {
				return latest;
			}
			lastIndex = this.processIndividualPost(searches, formatter, wantedItems, otherItems,
			        scraper, latest, lastIndex, post);
			currentProcessedTotal++;

		}
		return latest;
	}

	/**
	 * Send email.
	 *
	 * @param config
	 *            the config
	 * @param emailSender
	 *            the email sender
	 * @param emailBody
	 *            the email body
	 * @throws EmailException
	 *             the email exception
	 */
	public void sendEmail(final FreecycleConfig config, final EmailSender emailSender,
	        final StringBuilder emailBody) throws EmailException {
		final String[] toEmails = config.getToEmail();

		final String user = config.getEmailUser();
		final String server = config.getEmailServer();
		final String password = config.getEmailPassword();
		final String port = config.getEmailPort();
		final boolean sendAsHtml = config.isSendAsHtml();

		final EmailSession session = new EmailSessionImpl(user, password, server, port);

		for (final String toEmail : toEmails) {
			try {
				emailSender.sendMessage(config.getFromEmail(), config.getFromName(),
				        "Matching Freecycle items found", emailBody.toString(), sendAsHtml, session,
				        new String[] { toEmail });
			}
			catch (final EmailException e) {
				if (e.getCause() instanceof SMTPSendFailedException) {
					SearchReporter.LOGGER.error("Failed to send email to " + toEmail, e.getCause());
					emailSender.sendMessage(config.getFromEmail(), config.getFromName(),
					        "Failed to send to " + toEmail, emailBody.toString(), true, session,
					        new String[] { config.getFromEmail() });
				}
			}
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
	private boolean shouldBeReported(final Post post, final LatestPost latest) {
		final String link = post.getLink();
		if (post.getPostType() != PostType.OFFER) {
			return false;
		}
		final boolean shouldBeReported = post.getPostId() > latest.getLatestPostNumber();

		post.getFreecycleGroup();
		SearchReporter.LOGGER.info("report?: " + shouldBeReported + " ID: " + post.getPostId()
		        + " Last: " + latest.getLatestPostNumber() + " URL:" + link);

		return shouldBeReported;
	}

}
