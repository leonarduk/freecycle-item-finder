/*
 *
 */
package com.leonarduk.itemfinder.format;

/**
 * The Class HtmlFormatter.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 28 Jan 2015
 */
public class HtmlFormatter implements Formatter {

	/**
	 * Creates the node.
	 *
	 * @param value
	 *            the value
	 * @param node
	 *            the node
	 * @return the string
	 */
	private String createNode(final String value, final String node) {
		return new StringBuilder("<").append(node).append(">").append(value).append("</")
				.append(node).append(">").toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.format.Formatter#formatHeader(java.lang.String)
	 */
	@Override
	public final String formatHeader(final String header) {
		return this.createNode(header, "h1");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.format.Formatter#formatLink(java.lang.String, java.lang.String)
	 */
	@Override
	public final String formatLink(final String link, final String name) {
		final StringBuilder linkBuilder = new StringBuilder("<a href=\"" + link + "\">");
		linkBuilder.append(name);
		linkBuilder.append("</a>");
		return linkBuilder.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.format.Formatter#formatSubHeader(java.lang.String)
	 */
	@Override
	public final String formatSubHeader(final String header) {
		return this.createNode(header, "h2");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.format.Formatter#getNewLine()
	 */
	@Override
	public final String getNewLine() {
		return "<br/>";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.itemfinder.format.Formatter#getNewSection()
	 */
	@Override
	public final String getNewSection() {
		return "<hr/>";
	}
}
