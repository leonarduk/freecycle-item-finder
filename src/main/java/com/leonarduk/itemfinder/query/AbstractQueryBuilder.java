/**
 *
 */
package com.leonarduk.itemfinder.query;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.htmlparser.util.ParserException;

import com.leonarduk.itemfinder.html.HtmlParser;

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
abstract public class AbstractQueryBuilder<T extends AbstractQueryBuilder<T>> {

	/** The use post. */
	private boolean	usePost;

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
	protected HtmlParser getGETConnection(final String urlString,
			final Map<String, String> parameters) throws IOException, ParserException {
		final StringBuilder builder = new StringBuilder(urlString + "?");
		for (final Entry<String, String> entry : parameters.entrySet()) {
			builder.append(entry.getKey() + "=");
			builder.append(entry.getValue() + "&");
		}
		final String url = builder.toString();
		System.out.println("URL:" + url);
		return new HtmlParser(url);
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
	protected HtmlParser getPOSTConnection(final String urlString,
			final Map<String, String> parameters) throws IOException, ParserException {
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

		return new HtmlParser(connection);

		// PrintWriter out = new PrintWriter(connection.getOutputStream());
		// out.print(buffer);
		// out.close();
	}

	/**
	 * Use get.
	 *
	 * @return the t
	 */
	public T useGet() {
		this.usePost = false;
		return (T) this;
	}

	/**
	 * Use post.
	 *
	 * @return the t
	 */
	public T usePost() {
		this.usePost = false;
		return (T) this;
	}

	/**
	 * Uses post method.
	 *
	 * @return true, if successful
	 */
	public boolean usesPOSTMethod() {
		return this.usePost;
	}

}
