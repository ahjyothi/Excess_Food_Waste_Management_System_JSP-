package com.nxtgio.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.nxtgio.dao.DBCon;
import com.nxtgio.dao.RegionDao;
import com.nxtgio.model.LocalityModel;
import com.nxtgio.model.RegionModel;

/**
 * Servlet implementation class RegionServlet
 */
@WebServlet("/RegionServlet")
public class RegionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
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
				int id = Integer.parseInt(request.getParameter("id"));
				List<LocalityModel> slist = csd.getRegionByLocalityId(id);
				Gson json = new Gson();
				String regionList = json.toJson(slist);
				response.setContentType("text/html");
				response.getWriter().write(regionList);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
