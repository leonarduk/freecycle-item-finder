package com.leonarduk.itemfinder;

import java.io.IOException;

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

import com.leonarduk.itemfinder.freecycle.FreecycleQueryBuilder;

public interface QueryBuilder {

	FreecycleQueryBuilder setDateStart(int i, int j, int k);

	FreecycleQueryBuilder setDateEnd(int day, int month, int year);

	FreecycleQueryBuilder setSearchWords(String filter);

	Parser build() throws ParserException, IOException;

}
