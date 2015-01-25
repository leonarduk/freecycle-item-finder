package com.leonarduk.itemfinder.format;

public class HtmlFormatter implements Formatter {

	@Override
	public String getNewLine() {
		return "<br/>";
	}

	@Override
	public String getNewSection() {
		return "<hr/>";
	}

	@Override
	public String formatLink(String link, String name) {
		StringBuilder linkBuilder = new StringBuilder("<a href=\"" + link
				+ "\">");
		linkBuilder.append(name);
		linkBuilder.append("</a>");
		return linkBuilder.toString();
	}

	@Override
	public String formatHeader(String header) {
		return "<h1>" + header + "</h1>";
	}
}
