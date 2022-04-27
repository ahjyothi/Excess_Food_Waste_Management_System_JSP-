package com.nxtgio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nxtgio.model.UserCreateOrderModel;
import com.nxtgio.model.UserModel;

public class UserCreateOrderDao {

	public static int orderDetails(UserCreateOrderModel orderdetails) throws ClassNotFoundException {
		String INSERT_OrderDetails_SQL = "INSERT INTO [order_details]"
				+ "  (user_id_fk, admin_id_fk, order_details, status) VALUES " + " ( ?, ?, ?, ?);";

		int result = 0;

		try {
			Connection connection = DBCon.getCon();

			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_OrderDetails_SQL);
			preparedStatement.setString(1, orderdetails.getUser_id_fk());
			preparedStatement.setString(2, orderdetails.getAdmin_id_fk());
			preparedStatement.setString(3, orderdetails.getOrder_details());
			preparedStatement.setString(4, orderdetails.getStatus());

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			result = preparedStatement.executeUpdate();
			
			/* mail */
			if(result == 1){
				new SendMailSSL().SendNewOrderMail(orderdetails);
			}

		} catch (SQLException e) {
			// process sql exception
			e.printStackTrace();
		}
		return result;
	}

	
}
