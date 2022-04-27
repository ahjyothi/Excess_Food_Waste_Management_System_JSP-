package com.nxtgio.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.nxtgio.dao.AdminDao;
import com.nxtgio.dao.DBCon;
import com.nxtgio.dao.RegionDao;
import com.nxtgio.model.AdminModel;
import com.nxtgio.model.LocalityModel;
import com.nxtgio.model.RegionModel;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private AdminDao admindao = new AdminDao();
	
	private int localityid = 0;
	private int regionid = 0;

	/**
	 * Default constructor.
	 */
	public AdminServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				 
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		/*
		 * RequestDispatcher dispatcher =
		 * request.getRequestDispatcher("/WEB-INF/views/adminregister.jsp");
		 * dispatcher.forward(request,response);
		 */

		/* list view Region and locality */
		try (PrintWriter out = response.getWriter()) {

			RegionDao csd = new RegionDao(DBCon.getCon());

			String op = request.getParameter("operation");

			if (op.equals("region")) {
				List<RegionModel> clist = csd.getAllRegion();
				Gson json = new Gson();
				String regionList = json.toJson(clist);
				response.setContentType("text/html");
				response.getWriter().write(regionList);
			}

			if (op.equals("locality")) {
				regionid = Integer.parseInt(request.getParameter("id"));
				List<LocalityModel> slist = csd.getRegionByLocalityId(regionid);
				Gson json = new Gson();
				String regionList = json.toJson(slist);
				response.setContentType("text/html");
				response.getWriter().write(regionList);
			}
			
			if (op.equals("GetId")) {
				 localityid = Integer.parseInt(request.getParameter("id"));
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);

		RequestDispatcher dispatcherFail = request.getRequestDispatcher("/AdminRegister.jsp");
		
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String contact = request.getParameter("contact");
		String region_id_fk = "" + regionid;
		String locality_id_fk = "" +  localityid;
		String description = request.getParameter("description");

		AdminModel admin = new AdminModel();
		admin.setFirst_name(firstName);
		admin.setLast_name(lastName);
		admin.setEmail(email);
		admin.setUsername(username);
		admin.setPassword(password);
		admin.setContact(contact);
		admin.setAddress(address);
		admin.setRegion_id_fk(region_id_fk);
		admin.setLocality_id_fk(locality_id_fk);
		admin.setDescription(description);

		try {
			
			Connection connection = DBCon.getCon();
			if(connection != null){
				if ((region_id_fk.length() > 0) && (locality_id_fk.length() > 0) && !(region_id_fk.equalsIgnoreCase("0") && (locality_id_fk.equalsIgnoreCase("0"))) ){ 	// to check whether region and locality is selected
				
					int IsEmailExists = AdminDao.isAdminEmailCheck(admin);
					if (IsEmailExists == 1) {
						request.setAttribute("errorMessage", "EmailId Already Exists!");
						dispatcherFail.forward(request, response);
					} else if (IsEmailExists == 0) {
						int result = AdminDao.registerAdmin(admin);
						if (result == 1) {
							localityid = 0;
							regionid = 0;
							RequestDispatcher dispSuccess = request.getRequestDispatcher("/AdminLogin.jsp");
							dispSuccess.forward(request, response);
						} else if (result == 0) {
							request.setAttribute("errorMessage", "Cannot Create Admin at this location");
							dispatcherFail.forward(request, response);
						}else{
							request.setAttribute("errorMessage", "Please Select Region and locality");
							dispatcherFail.forward(request, response);
						}
					}
					
					
				}else {
					request.setAttribute("errorMessage", "Please Select Region and locality");
					dispatcherFail.forward(request, response);
				}
			}else {
				request.setAttribute("errorMessage", "Database Connection Failed");
				dispatcherFail.forward(request, response);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			request.setAttribute("errorMessage", e.getMessage().toString());
			dispatcherFail.forward(request, response);
		}

	}

}
