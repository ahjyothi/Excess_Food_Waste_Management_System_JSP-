package com.nxtgio.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nxtgio.dao.DBCon;
import com.nxtgio.dao.UserDao;
import com.nxtgio.model.UserModel;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String Userid = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		RequestDispatcher dispatcherFail = request.getRequestDispatcher("/UserLogin.jsp");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserModel user = new UserModel();
		user.setEmail(email);
		user.setPassword(password);
		
try {
			
			Connection connection = DBCon.getCon();
			if(connection != null){
				if ((email.length() > 0) && (password.length() > 0)){ 	// to check whether region and locality is selected
					
					int result = UserDao.loginUser(user);			
					if(result == 1){						//Database no insertion error
						
						/*session creation to get userId from user login form   */
						String userid = UserDao.getUserId(user.getEmail());
						
						 HttpSession session=request.getSession();  
					     session.setAttribute("UID",userid); 
						
					     response.sendRedirect(request.getContextPath() + "/UserDashboardServlet");   /* forwarding UserLoginServlet to UserDashboardServlet     */
					}
					else {
						request.setAttribute("errorMessage", "Invalid Credentials");
						dispatcherFail.forward(request, response);
					}
				}
				else {
					request.setAttribute("errorMessage", "Invalid Email and Password");
					dispatcherFail.forward(request, response);
				}
			}
			else {
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
