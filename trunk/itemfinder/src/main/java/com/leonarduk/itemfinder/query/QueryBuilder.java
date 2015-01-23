package com.leonarduk.itemfinder.query;

import java.io.IOException;
import java.time.LocalDate;

import org.htmlparser.util.ParserException;

import com.leonarduk.itemfinder.freecycle.FreecycleQueryBuilder;
import com.leonarduk.itemfinder.html.HtmlParser;

public interface QueryBuilder {

	FreecycleQueryBuilder setDateStart(int i, int j, int k);

	FreecycleQueryBuilder setDateEnd(int day, int month, int year);

	FreecycleQueryBuilder setSearchWords(String filter);

	HtmlParser build() throws ParserException, IOException;

	FreecycleQueryBuilder setDateStart(LocalDate date);

	String getSearchWords();

}