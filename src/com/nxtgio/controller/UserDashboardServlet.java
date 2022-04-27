package com.nxtgio.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.nxtgio.dao.UserDashboardDao;
import com.nxtgio.model.RegionModel;
import com.nxtgio.model.UserDashboardModel;
import com.nxtgio.model.UserModel;

/**
 * Servlet implementation class UserDashboardServlet
 */
@WebServlet("/UserDashboardServlet")
public class UserDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String Userid = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDashboardServlet() {
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
		Userid = (String) session.getAttribute("UID");   /* UID from userloginservlet */ 
		/* end */
if (Userid == null) {
			
			request.getRequestDispatcher("/UserLogin.jsp").include(request, response);
			request.setAttribute("errorMessage", "Successfully Logeed Out");
			session.invalidate();
}else {
		/* to get region and locality id for adding admin_id_fk */
		try (PrintWriter out = response.getWriter()) {
							
			UserModel userModelObject = UserDashboardDao.getuserdetails(Userid);			
			request.setAttribute("userModelStr" ,userModelObject);
			
			List<UserDashboardModel> userDashboardModelList = UserDashboardDao.getorderdeatails(Userid);			
			request.setAttribute("userDashboardModel" ,userDashboardModelList);
			
			
/*			if(request.getParameter("operation")!=null){
				String op = request.getParameter("operation");
				
				if(!op.equalsIgnoreCase(null))
				{
					if (op.equals("OrderSelect")) {
						int SelectedId = Integer.parseInt(request.getParameter("id"));
						UserDashboardModel userDashboardModelNew = userDashboardModelList.get(SelectedId);	
						request.setAttribute("userDashboardModelSelected" ,userDashboardModelNew);
						RequestDispatcher dispSuccess = request.getRequestDispatcher("/UserDashboard.jsp");
						
						request.setAttribute("operation", null);
						doGet(request, response);
						//response.sendRedirect(request.getContextPath() + "/UserDashboardServlet");

																	
					}
				}
			}*/
			RequestDispatcher dispSuccess = request.getRequestDispatcher("/UserDashboard.jsp");
			dispSuccess.forward(request, response);
		
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	}

}
