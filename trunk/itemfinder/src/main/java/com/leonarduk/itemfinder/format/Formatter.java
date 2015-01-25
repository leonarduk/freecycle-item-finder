package com.leonarduk.itemfinder.format;

public interface Formatter {

	String getNewLine();

	String getNewSection();

	String formatLink(String link, String name);

	String formatHeader(String header);

}
