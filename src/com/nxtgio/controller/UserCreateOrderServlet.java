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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.nxtgio.dao.DBCon;
import com.nxtgio.dao.RegionDao;
import com.nxtgio.dao.UserCreateOrderDao;
import com.nxtgio.model.LocalityModel;
import com.nxtgio.model.RegionModel;
import com.nxtgio.model.UserCreateOrderModel;

/**
 * Servlet implementation class UserOrderDetailsServlet
 */
@WebServlet("/UserCreateOrderServlet")
public class UserCreateOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int localityid = 0;
	private int regionid = 0;
	private String Adminid = null;
	private String Userid = null;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserCreateOrderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		/* to create seession to get user email id */
		HttpSession session = request.getSession(false);  /* dont create session but get session data from userloginservlet */
		Userid = (String) session.getAttribute("UID");   /* UID from userloginservlet */ 
		/* end */
		
		/* to get region and locality id for adding admin_id_fk */
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
				List<LocalityModel> slist = csd.getRegisteredRegionByLocalityId(regionid);
				Gson json = new Gson();
				String regionList = json.toJson(slist);
				response.setContentType("text/html");
				response.getWriter().write(regionList);
			}
			
			if (op.equals("GetId")) {
				 localityid = Integer.parseInt(request.getParameter("id"));  /* to get regionid and localityid from form */
				 Adminid = csd.getAdminId(localityid);
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

		RequestDispatcher dispatcherFail = request.getRequestDispatcher("/CreateOrder.jsp");

		String user_id_fk = Userid;
		String admin_id_fk = Adminid;
		String order_details = request.getParameter("order_details");
		String status = request.getParameter("status");

		UserCreateOrderModel orderdetails = new UserCreateOrderModel();
		orderdetails.setUser_id_fk(user_id_fk);
		orderdetails.setAdmin_id_fk(admin_id_fk);
		orderdetails.setOrder_details(order_details);
		orderdetails.setStatus("Pending");

		try {

			Connection connection = DBCon.getCon();
			if (connection != null) {
				if ((user_id_fk.length() > 0) && (admin_id_fk.length() > 0)) { 		// to check whether region and locality is selected

					int result = UserCreateOrderDao.orderDetails(orderdetails);
					if (result == 1) { // Database no insertion error
						request.setAttribute("SuccessMessage", "Order Created Sucessfully");
						response.sendRedirect(request.getContextPath() + "/UserDashboardServlet"); 
					} else {
						request.setAttribute("errorMessage", "Invalid Credentials");
						dispatcherFail.forward(request, response);
					}
				} else {
					request.setAttribute("errorMessage", "Invalid Region and Locality");
					dispatcherFail.forward(request, response);
				}
			} else {
				request.setAttribute("errorMessage", "Database Connection Failed");
				dispatcherFail.forward(request, response);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			request.setAttribute("errorMessage", e.getMessage().toString());
			dispatcherFail.forward(request, response);
		}

	}

}
