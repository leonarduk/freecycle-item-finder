/**
 *
 */
package com.leonarduk.itemfinder.query;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;

import com.leonarduk.itemfinder.html.HtmlParser;
import com.leonarduk.itemfinder.html.HtmlParserImpl;

/**
 * The Class AbstractQueryBuilder.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @param <T>
 *            the generic type
 * @since 28 Jan 2015
 */
public abstract class AbstractQueryBuilder<T extends AbstractQueryBuilder<T>> {

	/** The Constant LOG. */
	private static final Logger	LOG	= Logger.getLogger(AbstractQueryBuilder.class);
	/** The use post. */
	private boolean				usePost;

	/**
	 * Instantiates a new abstract query builder.
	 */
	public AbstractQueryBuilder() {
	}

	/**
	 * Instantiates a new abstract query builder.
	 *
	 * @param that
	 *            the that
	 */
	public AbstractQueryBuilder(final T that) {
		this.usePost = that.usesPOSTMethod();
	}

	/**
	 * Gets the gets the connection.
	 *
	 * @param urlString
	 *            the url string
	 * @param parameters
	 *            the parameters
	 * @return the gets the connection
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ParserException
	 *             the parser exception
	 */
	protected final HtmlParser getGETConnection(final String urlString,
	        final Map<String, String> parameters) throws IOException, ParserException {
		AbstractQueryBuilder.LOG.info("Attempt to get GET connection for " + urlString.toString());

		final StringBuilder builder = new StringBuilder(urlString + "?");
		for (final Entry<String, String> entry : parameters.entrySet()) {
			builder.append(entry.getKey() + "=");
			builder.append(entry.getValue() + "&");
		}
		final String url = builder.toString();
		System.out.println("URL:" + url);
		return new HtmlParserImpl(url);
	}

	/**
	 * Gets the POST connection.
	 *
	 * @param urlString
	 *            the url string
	 * @param parameters
	 *            the parameters
	 * @return the POST connection
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ParserException
	 *             the parser exception
	 */
	protected final HtmlParser getPOSTConnection(final String urlString,
	        final Map<String, String> parameters) throws IOException, ParserException {
		AbstractQueryBuilder.LOG.info("Attempt to get POST connection for " + urlString.toString());

		final URL url = new URL(urlString);
		final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);

		// more or less of these may be required
		// see Request Header Definitions: http://www.ietf.org/rfc/rfc2616.txt
		connection.setRequestProperty("Accept-Charset", "*");
		connection.setRequestProperty("Referer", urlString);
		connection.setRequestProperty("User-Agent", "WhoIs.java/1.0");
		final StringBuffer buffer = new StringBuffer(1024);
		// 'input' fields separated by ampersands (&)

		for (final Entry<String, String> entry : parameters.entrySet()) {
			buffer.append(entry.getKey() + "=");
			buffer.append(entry.getValue());
		}

		return new HtmlParserImpl(connection);

		// PrintWriter out = new PrintWriter(connection.getOutputStream());
		// out.print(buffer);
		// out.close();
	}

	/**
	 * Use get.
	 *
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	public final T useGet() {
		this.usePost = false;
		return ((T) this);
	}

	/**
	 * Use post.
	 *
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	public final T usePost() {
		this.usePost = true;
		return ((T) this);
	}

	/**
	 * Uses post method.
	 *
	 * @return true, if successful
	 */
	public final boolean usesPOSTMethod() {
		return this.usePost;
	}

}
