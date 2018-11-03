package com.grupodot.telefonica.views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import com.grupodot.telefonica.controllers.LoginRolesController;
import com.grupodot.telefonica.models.Usuario;

/**
 * 
 * @author Julian Cuestas
 * @Date 21-08-2018
 */
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginRolesController logCont = new LoginRolesController("formularios_telefonica", "", "");
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
        String username, password;
        username = request.getParameter("username");
        password = request.getParameter("password");
        String passEncrypt = DigestUtils.sha512Hex(password);
//		System.out.println("Encrytp "+passEncrypt);
		Usuario userLogin = this.logCont.login(username, passEncrypt);
		if (userLogin != null && session.getAttribute("userData") == null) {
			System.out.println("user "+userLogin);
			session.setAttribute("userData", userLogin);
			System.out.println("hola Estoy en "+request.getContextPath());
			this.validarRedirecTipoUser(userLogin,response);
		}else {
			System.out.println("DATOS INCORRECTOS");
			request.getSession().setAttribute("message", "Incorrecto");
			response.sendRedirect("/html/Admin/loginAdmin.jsp");
		}
	}
	
	private void validarRedirecTipoUser(Usuario userLogin, HttpServletResponse response) {
		try {
			if(userLogin.getTipousuario().equalsIgnoreCase("superUser")) {
				response.sendRedirect("/html/Admin/superUser.jsp");
			} else {
				response.sendRedirect("/html/Admin/admin.jsp");
			}
		} catch (Exception e) {
			System.out.println("ERROR al intentar redirecionar.");
			e.printStackTrace();
		}
	}
}
