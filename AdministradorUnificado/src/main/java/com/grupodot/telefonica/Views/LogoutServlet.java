package com.grupodot.telefonica.Views;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.grupodot.telefonica.Models.Usuario;


/**
 * 
 * @author Julian Cuestas - by @author Cristtian
 * @Date 21-08-2018
 */
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		
		request.getSession().invalidate();
		System.out.println("REDIRECT "+request.getServletPath()+":"+request.getLocalPort());
		response.sendRedirect("/");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		request.getSession().invalidate();
		System.out.println("REDIRECT "+request.getServletPath()+":"+request.getLocalPort());
		response.sendRedirect("/");
	}
	
}
