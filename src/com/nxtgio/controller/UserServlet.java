package com.nxtgio.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nxtgio.dao.AdminDao;
import com.nxtgio.dao.DBCon;
import com.nxtgio.dao.UserDao;
import com.nxtgio.model.UserModel;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);

		RequestDispatcher dispatcherFail = request.getRequestDispatcher("/UserRegister.jsp");

		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String description = request.getParameter("description");
		String address = request.getParameter("address");

		UserModel user = new UserModel();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setDescription(description);
		user.setAddress(address);

		try {

			Connection connection = DBCon.getCon();
			if (connection != null) {
				int IsEmailExists = UserDao.isUserEmailCheck(user);
				if (IsEmailExists == 1) {
					request.setAttribute("errorMessage", "EmailId Already Exists!");
					dispatcherFail.forward(request, response);
				} else if (IsEmailExists == 0) {
					int result = UserDao.registerUser(user);
					if (result == 1) {
						RequestDispatcher dispSuccess = request.getRequestDispatcher("/UserLogin.jsp");
						dispSuccess.forward(request, response);
					} else if (result == 0) {
						request.setAttribute("errorMessage", "Cannot Create User at this location");
						dispatcherFail.forward(request, response);
					}
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
