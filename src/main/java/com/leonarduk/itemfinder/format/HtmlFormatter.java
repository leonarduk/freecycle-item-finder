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
		return createNode(header, "h1");
	}

	@Override
	public String formatSubHeader(String header) {
		return createNode(header, "h2");
	}

	private String createNode(String value, String node) {
		return new StringBuilder("<").append(node).append(">").append(value)
				.append("</").append(node).append(">").toString();
	}
}
