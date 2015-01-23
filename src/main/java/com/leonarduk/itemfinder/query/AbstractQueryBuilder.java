package com.leonarduk.itemfinder.query;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.htmlparser.util.ParserException;

import com.leonarduk.itemfinder.html.HtmlParser;

abstract public class AbstractQueryBuilder<T extends AbstractQueryBuilder<T>> {
	private boolean usePost;

	public T usePost() {
		this.usePost = false;
		return (T) this;
	}

	public T useGet() {
		this.usePost = false;
		return (T) this;
	}

	public boolean usesPOSTMethod() {
		return this.usePost;
	}

	protected HtmlParser getGETConnection(String urlString,
			Map<String, String> parameters) throws IOException, ParserException {
		StringBuilder builder = new StringBuilder(urlString + "?");
		for (Entry<String, String> entry : parameters.entrySet()) {
			builder.append(entry.getKey() + "=");
			builder.append(entry.getValue() + "&");
		}
		String url = builder.toString();
		System.out.println("URL:" + url);
		return new HtmlParser(url);
	}

	protected HtmlParser getPOSTConnection(String urlString,
			Map<String, String> parameters) throws IOException, ParserException {
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);

		// more or less of these may be required
		// see Request Header Definitions: http://www.ietf.org/rfc/rfc2616.txt
		connection.setRequestProperty("Accept-Charset", "*");
		connection.setRequestProperty("Referer", urlString);
		connection.setRequestProperty("User-Agent", "WhoIs.java/1.0");
		StringBuffer buffer = new StringBuffer(1024);
		// 'input' fields separated by ampersands (&)

		for (Entry<String, String> entry : parameters.entrySet()) {
			buffer.append(entry.getKey() + "=");
			buffer.append(entry.getValue());
		}

		return new HtmlParser(connection);

		// PrintWriter out = new PrintWriter(connection.getOutputStream());
		// out.print(buffer);
		// out.close();
	}

}
