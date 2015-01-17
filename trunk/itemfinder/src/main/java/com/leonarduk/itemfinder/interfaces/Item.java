package com.leonarduk.itemfinder.interfaces;

public interface Item {
	public int getQuantity();

	public double getPrice();

	public String getName();

	public String getDescription();

	public Condition getCondition();

	public enum Condition {
		NEW, USED, REFURBISHED, BROKEN, UNKNOWN
	}
}
