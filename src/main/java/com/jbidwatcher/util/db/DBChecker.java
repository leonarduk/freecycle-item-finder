package com.jbidwatcher.util.db;

import java.sql.SQLException;

public class DBChecker {
	public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
		Upgrader.upgrade();
	}
}
