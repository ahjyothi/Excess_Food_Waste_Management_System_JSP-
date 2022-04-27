package com.nxtgio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nxtgio.model.AdminModel;
import com.nxtgio.model.UserModel;

public class UserDao {

	public static int registerUser(UserModel user) throws ClassNotFoundException {
		String INSERT_USERS_SQL = "INSERT INTO [user_register]" + "  (username, email, password, description, address) VALUES "
				+ " ( ?, ?, ?, ?, ?);";

		int result = 0;

		try {
			Connection connection = DBCon.getCon();

			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getDescription());
			preparedStatement.setString(5, user.getAddress());

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// process sql exception
			e.printStackTrace();
		}
		return result;
	}

	// method for email checking
	public static int isUserEmailCheck(UserModel user) throws ClassNotFoundException {

		int IsEmail = 0;
		String select_email_sql = "select COUNT(*) AS IsEmail from [user_register] where email = ?";
		ResultSet result;

		try {
			Connection connection = DBCon.getCon();

			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(select_email_sql);
			preparedStatement.setString(1, user.getEmail());

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			result = preparedStatement.executeQuery(); // returns full table

			while (result.next()) {
				IsEmail = result.getInt("IsEmail"); // to fetch only email column
			}

		} catch (SQLException e) {
			// process sql exception
			e.printStackTrace();
			IsEmail = 0;

		}
		return IsEmail;

	}

	//method for user login 
	public static int loginUser(UserModel user) throws ClassNotFoundException {
		int IsPresent = 0;

		String select_login_sql = "select COUNT(*) AS IsPresent from [user_register] where email = ? and password = ?";
		ResultSet result;

		try {
			Connection connection = DBCon.getCon();

			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(select_login_sql);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getPassword());

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			result = preparedStatement.executeQuery();

			while (result.next()) {
				IsPresent = result.getInt(1);
			}

		} catch (SQLException e) {
			// process sql exception
			e.printStackTrace();
		}
		return IsPresent;
	}
	
	public static String getUserId(String emailid) {
		String UserId = null;
		ResultSet result;
		String query;
		
		try {
			Connection connection = DBCon.getCon();
			query = " select id from user_register where email=?";    /* to select email for logged in user   */
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, emailid);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				UserId = result.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return UserId;
	}
}
