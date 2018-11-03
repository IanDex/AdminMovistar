package com.grupodot.telefonica.views;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.grupodot.telefonica.models.Usuario;

/**
 * 
 * @author Julian Cuestas
 * @Date 21-08-2018
 */
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		request.getSession().invalidate();
		System.out.println("REDIRECT "+request.getServletPath()+":"+request.getLocalPort());
		response.sendRedirect("/html/Admin/loginAdmin.jsp");
	}
	
}
