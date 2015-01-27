package com.leonarduk.itemfinder;

public class ItemFinderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6957476799043277085L;

	public ItemFinderException(String message) {
		super(message);
	}

	public ItemFinderException(String message, Exception e) {
		super(message, e);
	}

}
