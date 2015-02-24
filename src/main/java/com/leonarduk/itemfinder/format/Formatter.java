/*
 *
 */
package com.leonarduk.itemfinder.format;

/**
 * The Interface Formatter.
 */
public interface Formatter {

	/**
	 * Format header.
	 *
	 * @param header
	 *            the header
	 * @return the string
	 */
	String formatHeader(String header);

	/**
	 * Format link.
	 *
	 * @param link
	 *            the link
	 * @param name
	 *            the name
	 * @return the string
	 */
	String formatLink(String link, String name);

	/**
	 * Format sub header.
	 *
	 * @param header
	 *            the header
	 * @return the string
	 */
	String formatSubHeader(String header);

	/**
	 * Gets the new line.
	 *
	 * @return the new line
	 */
	String getNewLine();

	/**
	 * Gets the new section.
	 *
	 * @return the new section
	 */
	String getNewSection();

}
