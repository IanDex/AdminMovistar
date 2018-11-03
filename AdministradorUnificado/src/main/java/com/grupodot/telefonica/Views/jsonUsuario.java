package com.grupodot.telefonica.Views;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.grupodot.telefonica.Controllers.SuperAdminController;

/**
 * Servlet implementation class jsonUsuario
 */
@WebServlet("/jsonUsuario")
public class jsonUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SuperAdminController superAdmin = new SuperAdminController();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public jsonUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(superAdmin.listUser()).append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
