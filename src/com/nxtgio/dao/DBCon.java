package com.nxtgio.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {

	private static Connection con;

	public static Connection getCon() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection("jdbc:sqlserver://IP:port;databaseName=dbname",
					"username", "password");
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}
		return con;
	}
}
