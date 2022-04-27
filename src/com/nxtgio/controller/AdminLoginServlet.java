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

import com.nxtgio.dao.AdminDao;
import com.nxtgio.dao.DBCon;
import com.nxtgio.dao.UserDao;
import com.nxtgio.model.AdminModel;

/**
 * Servlet implementation class AdminLoginServlet
 */
@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		RequestDispatcher dispatcherFail = request.getRequestDispatcher("/AdminLogin.jsp");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		AdminModel admin = new AdminModel();
		admin.setEmail(email);
		admin.setPassword(password);
		
try {
			
			Connection connection = DBCon.getCon();
			if(connection != null){
				if ((email.length() > 0) && (password.length() > 0)){ 	// to check whether region and locality is selected
					
					int result = AdminDao.loginAdmin(admin);			
					if(result == 1){						//Database no insertion error
						
						/*session creation to get userId from user login form   */
						String adminid = AdminDao.getAdminId(admin.getEmail());
						
						 HttpSession session=request.getSession();  
					     session.setAttribute("AID",adminid); 
						
						/*RequestDispatcher dispSuccess = request.getRequestDispatcher("/AdminDashboard.jsp");
						dispSuccess.forward(request, response);*/
					     response.sendRedirect(request.getContextPath() + "/AdminDashboardServlet");
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

