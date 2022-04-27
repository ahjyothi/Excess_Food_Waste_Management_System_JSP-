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

import com.nxtgio.dao.AdminDashboardDao;
import com.nxtgio.dao.DBCon;
import com.nxtgio.model.AdminDashboardModel;
import com.nxtgio.model.AdminModel;

/**
 * Servlet implementation class AdminDashboardServlet
 */
@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String Adminid = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		/* to create seession to get user email id */
		HttpSession session = request.getSession(false);  /* dont create session but get session data from userloginservlet */
		Adminid = (String) session.getAttribute("AID");   /* UID from userloginservlet */ 
		/* end */
		
		if (Adminid == null) {
			
			request.getRequestDispatcher("/AdminLogin.jsp").include(request, response);
			request.setAttribute("errorMessage", "Successfully Logeed Out");
			session.invalidate();
		}else {
			try (PrintWriter out = response.getWriter()) {
				
				AdminModel adminModelObject = AdminDashboardDao.getadmindetails(Adminid);			
				request.setAttribute("adminModelStr" ,adminModelObject);
				
				List<AdminDashboardModel> adminDashboardModelList = AdminDashboardDao.getorderdeatails(Adminid);			
				request.setAttribute("adminDashboardModel" ,adminDashboardModelList);
				
				RequestDispatcher dispSuccess = request.getRequestDispatcher("/AdminDashboard.jsp");
				dispSuccess.forward(request, response);
			
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		/* to get region and locality id for adding admin_id_fk */
/*		try (PrintWriter out = response.getWriter()) {
							
			AdminModel adminModelObject = AdminDashboardDao.getadmindetails(Adminid);			
			request.setAttribute("adminModelStr" ,adminModelObject);
			
			List<AdminDashboardModel> adminDashboardModelList = AdminDashboardDao.getorderdeatails(Adminid);			
			request.setAttribute("adminDashboardModel" ,adminDashboardModelList);
			
			RequestDispatcher dispSuccess = request.getRequestDispatcher("/AdminDashboard.jsp");
			dispSuccess.forward(request, response);
		
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	/* Order status update code not working check it  */	
		RequestDispatcher dispatcherFail = request.getRequestDispatcher("/AdminDashboard.jsp");
		
		String oid = request.getParameter("oid");
		String status = request.getParameter("status");

		AdminDashboardModel admin = new AdminDashboardModel();
		admin.setOid(oid);
		admin.setStatus(status);

		try {

			Connection connection = DBCon.getCon();
			if (connection != null) {
				int result = AdminDashboardDao.setorderstatus(admin);
				if(result == 1){
					response.sendRedirect(request.getContextPath() + "/AdminDashboardServlet");
					/*request.setAttribute("UIMessage", "Order Status Updated");
					dispatcherFail.forward(request, response);*/
					//doGet(request, response);
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			request.setAttribute("UIMessage", e.getMessage().toString());
			dispatcherFail.forward(request, response);
		}

	}

}
