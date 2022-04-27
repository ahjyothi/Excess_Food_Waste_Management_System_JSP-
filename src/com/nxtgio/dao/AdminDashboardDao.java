package com.nxtgio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nxtgio.model.AdminDashboardModel;
import com.nxtgio.model.AdminModel;

public class AdminDashboardDao {
	
public static AdminModel getadmindetails(String adminid) throws ClassNotFoundException {    /*getuserdetails(String userid) = sending method with data == parameter */
		
		String select_OrderDetails_SQL = "select * from admin_register where id='"+ adminid +"'";

		AdminModel adminModel = new AdminModel(); 
		
		int result = 0;

		try {
			Connection connection = DBCon.getCon();

			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(select_OrderDetails_SQL);

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet resultset = preparedStatement.executeQuery();
			
			while(resultset.next()){
				adminModel.setUsername(resultset.getString("username"));  /* adding data from resulset to usermodel */
				adminModel.setEmail(resultset.getString("email"));
				adminModel.setFirst_name(resultset.getString("first_name"));
				adminModel.setLast_name(resultset.getString("last_name"));
				adminModel.setAddress(resultset.getString("address"));
				adminModel.setContact(resultset.getString("contact"));
				adminModel.setDescription(resultset.getString("description"));
			}
			
		} catch (SQLException e) {
			// process sql exception
			e.printStackTrace();
		}
		return adminModel;  /* return model class */
}
public static List<AdminDashboardModel> getorderdeatails(String adminId) throws ClassNotFoundException {
	 /*to get order details with business details admin table and order table */
	String select_AdminOrderDetails_SQL = "SELECT dbo.order_details.oid, dbo.order_details.admin_id_fk, dbo.order_details.order_details, dbo.order_details.status, dbo.order_details.Created_on, dbo.admin_register.first_name, dbo.admin_register.last_name,dbo.admin_register.email, dbo.admin_register.username, dbo.admin_register.address, dbo.admin_register.contact, dbo.locality.name, dbo.region.region_name, dbo.admin_register.id, dbo.user_register.username AS Expr1, dbo.order_details.user_id_fk, dbo.user_register.description FROM dbo.admin_register INNER JOIN dbo.order_details ON dbo.admin_register.id = dbo.order_details.admin_id_fk INNER JOIN dbo.locality ON dbo.admin_register.locality_id_fk = dbo.locality.id INNER JOIN  dbo.region ON dbo.admin_register.region_id_fk = dbo.region.region_id AND dbo.locality.region_id_fk = dbo.region.region_id INNER JOIN dbo.user_register ON dbo.order_details.user_id_fk = dbo.user_register.id WHERE (dbo.admin_register.id = '"+ adminId +"') order by Created_on desc";


	List<AdminDashboardModel> adminDashboardModelList = new ArrayList<>();    /*where there is a list of data to fetch use this line of code */
	
	int result = 0;

	try {
		Connection connection = DBCon.getCon();

		// Step 2:Create a statement using connection object
		PreparedStatement preparedStatement = connection.prepareStatement(select_AdminOrderDetails_SQL);

		System.out.println(preparedStatement);
		// Step 3: Execute the query or update query
		ResultSet resultset = preparedStatement.executeQuery();
		
		while(resultset.next()){
			
			AdminDashboardModel adminDashboardModel = new AdminDashboardModel();
			
			adminDashboardModel.setOrder_details(resultset.getString("order_details"));
			adminDashboardModel.setStatus(resultset.getString("status"));
			adminDashboardModel.setCreated_on(resultset.getString("Created_on"));
			adminDashboardModel.setFirst_name(resultset.getString("first_name"));
			adminDashboardModel.setLast_name(resultset.getString("last_name"));
			adminDashboardModel.setContact(resultset.getString("contact"));
			adminDashboardModel.setOid(resultset.getString("oid"));
			adminDashboardModel.setUser_id_fk(resultset.getString("user_id_fk"));
			adminDashboardModel.setDescription(resultset.getString("description"));
			
			adminDashboardModelList.add(adminDashboardModel); 		/* adding each model to the list 		*/				
		}				
	} catch (SQLException e) {
		// process sql exception
		e.printStackTrace();
	}
	return adminDashboardModelList;
}
/*
 * Order status update code not working check it  */
public static int setorderstatus(AdminDashboardModel adminDashboardModel) throws ClassNotFoundException {  
	
	String update_OrderStatus_SQL = "update order_details set status='"+ adminDashboardModel.getStatus() +"' where oid='"+ adminDashboardModel.getOid() +"'";

	AdminModel adminModel = new AdminModel(); 
	
	int result = 0;

	try {
		Connection connection = DBCon.getCon();

		// Step 2:Create a statement using connection object
		PreparedStatement preparedStatement = connection.prepareStatement(update_OrderStatus_SQL);
		System.out.println(preparedStatement);
		result = preparedStatement.executeUpdate();
		
		/* mail send to user */
		if(result == 1){
			new SendMailSSL().SendUpdatedOrderMail(adminDashboardModel);
		}
		
	} catch (SQLException e) {
		// process sql exception
		e.printStackTrace();
	}
	return result;  
}
}
