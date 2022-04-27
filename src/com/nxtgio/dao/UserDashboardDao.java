package com.nxtgio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.nxtgio.model.UserCreateOrderModel;
import com.nxtgio.dao.DBCon;
import com.nxtgio.model.UserModel;
import com.nxtgio.model.UserDashboardModel;

public class UserDashboardDao {
	
public static UserModel getuserdetails(String userid) throws ClassNotFoundException {    /*getuserdetails(String userid) = sending method with data == parameter */
		
		String select_OrderDetails_SQL = "select * from user_register where id='"+ userid +"'";

		UserModel userModel = new UserModel(); 
		
		int result = 0;

		try {
			Connection connection = DBCon.getCon();

			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(select_OrderDetails_SQL);

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet resultset = preparedStatement.executeQuery();
			
			while(resultset.next()){
				userModel.setUsername(resultset.getString("username"));  /* adding data from resulset to usermodel */
				userModel.setEmail(resultset.getString("email"));
				userModel.setDescription(resultset.getString("description"));
			}
			
		} catch (SQLException e) {
			// process sql exception
			e.printStackTrace();
		}
		return userModel;  /* return model class */
}
public static List<UserDashboardModel> getorderdeatails(String userId) throws ClassNotFoundException {
	/* to get order details with business details admin table and order table */
	String select_UserOrderDetails_SQL = "SELECT order_details.order_details, order_details.status, order_details.Created_on, order_details.Updated_on, admin_register.first_name, admin_register.last_name, admin_register.contact,order_details.oid,order_details.user_id_fk FROM order_details INNER JOIN admin_register ON order_details.admin_id_fk = admin_register.id WHERE (order_details.user_id_fk='"+ userId +"') order by Created_on desc";

	List<UserDashboardModel> userDashboardModelList = new ArrayList<>();   /* where there is a list of data to fetch use this line of code */
	
	int result = 0;

	try {
		Connection connection = DBCon.getCon();

		// Step 2:Create a statement using connection object
		PreparedStatement preparedStatement = connection.prepareStatement(select_UserOrderDetails_SQL);

		System.out.println(preparedStatement);
		// Step 3: Execute the query or update query
		ResultSet resultset = preparedStatement.executeQuery();
		
		while(resultset.next()){
			
			UserDashboardModel userDashboardModel = new UserDashboardModel();
			
			userDashboardModel.setOrder_details(resultset.getString("order_details"));
			userDashboardModel.setStatus(resultset.getString("status"));
			userDashboardModel.setCreated_on(resultset.getString("Created_on"));
			userDashboardModel.setUpdated_on(resultset.getString("Updated_on"));
			userDashboardModel.setFirst_name(resultset.getString("first_name"));
			userDashboardModel.setLast_name(resultset.getString("last_name"));
			userDashboardModel.setContact(resultset.getString("contact"));
			userDashboardModel.setOid(resultset.getString("oid"));
			userDashboardModel.setUser_id_fk(resultset.getString("user_id_fk"));
			
			userDashboardModelList.add(userDashboardModel); 		/* adding each model to the list */						
		}				
	} catch (SQLException e) {
		// process sql exception
		e.printStackTrace();
	}
	return userDashboardModelList;
}

/*public static UserDashboardModel getorderdata(String userId) throws ClassNotFoundException {
	 to get order details with business details admin table and order table 
	String select_UserOrderDetails_SQL = "SELECT order_details.order_details, order_details.status, order_details.Created_on, order_details.Updated_on, admin_register.first_name, admin_register.last_name, admin_register.contact,order_details.oid,order_details.user_id_fk FROM order_details INNER JOIN admin_register ON order_details.admin_id_fk = admin_register.id WHERE (order_details.user_id_fk='"+ userId +"')";
	
	UserDashboardModel userDashboardModeldata = new UserDashboardModel();
	
	int result = 0;

	try {
		Connection connection = DBCon.getCon();

		// Step 2:Create a statement using connection object
		PreparedStatement preparedStatement = connection.prepareStatement(select_UserOrderDetails_SQL);

		System.out.println(preparedStatement);
		// Step 3: Execute the query or update query
		ResultSet resultset = preparedStatement.executeQuery();
		
		while(resultset.next()){

			userDashboardModeldata.setOrder_details(resultset.getString("order_details"));
			userDashboardModeldata.setStatus(resultset.getString("status"));
			userDashboardModeldata.setCreated_on(resultset.getString("Created_on"));
			userDashboardModeldata.setUpdated_on(resultset.getString("Updated_on"));
			userDashboardModeldata.setFirst_name(resultset.getString("first_name"));
			userDashboardModeldata.setLast_name(resultset.getString("last_name"));
			userDashboardModeldata.setContact(resultset.getString("contact"));
			userDashboardModeldata.setOid(resultset.getString("oid"));
			userDashboardModeldata.setUser_id_fk(resultset.getString("user_id_fk"));
			
			userDashboardModelList.add(userDashboardModeldata); 		 adding each model to the list 						
		}				
	} catch (SQLException e) {
		// process sql exception
		e.printStackTrace();
	}
	return userDashboardModeldata;
}*/
}
