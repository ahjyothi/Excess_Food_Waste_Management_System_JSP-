package com.nxtgio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nxtgio.model.AdminModel;

public class AdminDao {
	public static int registerAdmin(AdminModel admin) throws ClassNotFoundException {
		String INSERT_USERS_SQL = "INSERT INTO [admin_register]"
				+ "  ( first_name, last_name, email, username, password, address, contact, region_id_fk, locality_id_fk, description) VALUES "
				+ " ( ?, ?, ?, ?,?,?, ?, ?, ?,?);";

		int result = 0;

		try {
			Connection connection = DBCon.getCon();

			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
			// preparedStatement.setInt(1, 1);
			preparedStatement.setString(1, admin.getFirst_name());
			preparedStatement.setString(2, admin.getLast_name());
			preparedStatement.setString(3, admin.getEmail());
			preparedStatement.setString(4, admin.getUsername());
			preparedStatement.setString(5, admin.getPassword());
			preparedStatement.setString(6, admin.getAddress());
			preparedStatement.setString(7, admin.getContact());
			preparedStatement.setString(8, admin.getRegion_id_fk());
			preparedStatement.setString(9, admin.getLocality_id_fk());
			preparedStatement.setString(10, admin.getDescription());

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			result = preparedStatement.executeUpdate();
			
			//Update Locality Id after Admin Reistratio for selected locality id
			if(result==1){
				String UPDATE_LOCALITY = "UPDATE  [locality] set IsOccupied=1 where id=?";
				preparedStatement = connection.prepareStatement(UPDATE_LOCALITY);
				preparedStatement.setString(1, admin.getLocality_id_fk());
				System.out.println(preparedStatement);
				result = preparedStatement.executeUpdate();
			}
			
			

		} catch (SQLException e) {
			// process sql exception
			e.printStackTrace();
			result = 2; // if any errors
		}
		return result;
	}

	public static int loginAdmin(AdminModel admin) throws ClassNotFoundException {
		int IsPresent = 0;

		String select_login_sql = "select COUNT(*) AS IsPresent from [admin_register] where email = ? and password = ?";
		ResultSet result;

		try {
			Connection connection = DBCon.getCon();

			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(select_login_sql);
			preparedStatement.setString(1, admin.getEmail());
			preparedStatement.setString(2, admin.getPassword());

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

	//method for email checking
	public static int isAdminEmailCheck(AdminModel admin) throws ClassNotFoundException {

		int IsEmail = 0;
		String select_email_sql = "select COUNT(*) AS IsEmail from [admin_register] where email = ?";
		ResultSet result;

		try {
			Connection connection = DBCon.getCon();

			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(select_email_sql);
			preparedStatement.setString(1, admin.getEmail());

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			result = preparedStatement.executeQuery();     // returns full table 

			while (result.next()) {
				IsEmail = result.getInt("IsEmail");   // to fetch only email column
			}

		} catch (SQLException e) {
			// process sql exception
			e.printStackTrace();
			 IsEmail = 0;
			
		}
		return IsEmail;

	}
	
	public static String getAdminId(String emailid) {
		String AdminId = null;
		ResultSet result;
		String query;
		
		try {
			Connection connection = DBCon.getCon();
			query = " select id from admin_register where email=?";    /* to select email for logged in user   */
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, emailid);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				AdminId = result.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return AdminId;
	}

}


