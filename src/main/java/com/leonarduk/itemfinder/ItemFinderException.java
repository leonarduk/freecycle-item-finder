package com.leonarduk.itemfinder;

public class ItemFinderException extends Exception {

	public ItemFinderException(String message) {
		super(message);
	}

	public ItemFinderException(String message, Exception e) {
		super(message, e);
	}

}
