package com.nxtgio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.nxtgio.model.AdminDashboardModel;
import com.nxtgio.model.UserCreateOrderModel;
/*import com.sun.org.apache.bcel.internal.Constants;*/


public class SendMailSSL {
	
	private Connection dbCon = null;
	private String query = null;
	private ResultSet rs;
	private String response;
	private PreparedStatement ps;
	
	public String HOST = "smtp.gmail.com";	
	public String USERNAME_FROM = "admin@gmail.com";   /* ADMIN email paste here */
	public String PASSWORD = "password";
	public String SPORT = "465";


	public String SendNewOrderMail(UserCreateOrderModel orderdetails) {
		
		String Result = "";
		String toadminemail = "";
		String adminname = "";
		String fromuseremail ="";
		String username ="";
		String useraddress ="";
		
		Connection connection = DBCon.getCon();
		if (connection != null) {
			query = "SELECT email, username FROM dbo.admin_register WHERE (id = '"+ orderdetails.getAdmin_id_fk() +"')";
			System.out.println(query);
			try{
				ps = connection.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					toadminemail = rs.getString("email");
					adminname = rs.getString("username");
				}
				query = "SELECT username, email, [address] FROM dbo.user_register WHERE (id ='"+ orderdetails.getUser_id_fk() +"')";
				System.out.println(query);
				ps = connection.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					username = rs.getString("username");
					fromuseremail = rs.getString("email");
					useraddress = rs.getString("address");
				}
				
			}catch (SQLException e) {
				System.out.println(e.getMessage().toString());			
			} finally {

				try {
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage().toString());					
				}
			}
		}
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", SPORT);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", SPORT);

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME_FROM, PASSWORD);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME_FROM));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(fromuseremail));
			/*message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(toadminemail));*/
			message.setSubject("Excess Food Available");						
			message.setContent("<html> <body> <p>Dear "+ adminname +" </p> <p>I am <b>"+ username +"</b> here, Need to donate food that can serve<br><b>"+ orderdetails.getOrder_details() +"</b> </p> <p>Thanks and Regards,<br>Food Donation Team<br> </body> </html>","text/html"); 						
			Transport.send(message);
			Result = "1";

			System.out.println("Done");

		} catch (MessagingException e) {
			Result = e.getMessage().toString();
			//throw new RuntimeException(e);
		}
		return Result;
	}

public String SendUpdatedOrderMail(AdminDashboardModel adminDashboardModel) {
		
	String Result = "";
	String toadminemail = "";
	String adminname = "";
	String fromuseremail ="";
	String username ="";
	String description = "";
	String address = "";
	
	Connection connection = DBCon.getCon();
	if (connection != null) {
		query = "SELECT dbo.user_register.username AS USERNAME, dbo.user_register.email AS USEREMAIL, dbo.admin_register.email AS ADMINEMAIL, dbo.user_register.address AS USERADDRESS, dbo.admin_register.username AS ADMINNAME, dbo.order_details.oid, dbo.order_details.order_details AS DESCRIPTION FROM dbo.order_details INNER JOIN dbo.admin_register ON dbo.order_details.admin_id_fk = dbo.admin_register.id INNER JOIN dbo.user_register ON dbo.order_details.user_id_fk = dbo.user_register.id WHERE(dbo.order_details.oid = '"+ adminDashboardModel.getOid()+"')";
		System.out.println(query);
		try{
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				username = rs.getString("USERNAME");
				fromuseremail = rs.getString("ADMINEMAIL");
				toadminemail = rs.getString("USEREMAIL");
				adminname = rs.getString("ADMINNAME");
				description = rs.getString("DESCRIPTION");
				//address = rs.getString("USERADDRESS");				
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage().toString());			
		} finally {

			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage().toString());					
			}
		}
	}
	
	Properties props = new Properties();
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.socketFactory.port", SPORT);
	props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.port", SPORT);

	Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(USERNAME_FROM, PASSWORD);
		}
	});

	try {

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(USERNAME_FROM));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(fromuseremail));
	/*	message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(toadminemail));*/
		message.setSubject("Order Status Mail");						
		message.setContent("<html> <body> <p>Dear "+ username +" </p> <p>Order Status has been updated. <br> Description: <b>"+ description +"</b> <br> Status: <b>"+ adminDashboardModel.getStatus() +"</b> </p> <p>Thanks and Regards,<br>Food Donation Team<br><br></body> </html>","text/html"); 
					
		Transport.send(message);
		Result = "1";

		System.out.println("Done");

	} catch (MessagingException e) {
		Result = e.getMessage().toString();
		//throw new RuntimeException(e);
	}
	return Result;
}
}