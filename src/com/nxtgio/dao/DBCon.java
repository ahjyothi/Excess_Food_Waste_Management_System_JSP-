package com.nxtgio.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {

	private static Connection con;

	public static Connection getCon() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection("jdbc:sqlserver://192.168.1.121:1433;databaseName=sptesting",
					"jyothi", "12345");
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}
		return con;
	}
}
