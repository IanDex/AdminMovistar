package com.grupodot.telefonica.Views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.apache.commons.codec.digest.DigestUtils;

import com.grupodot.telefonica.Controllers.LoginRolesController;
import com.grupodot.telefonica.Models.Usuario;

/**
 * 
 * @author Julian Cuestas - By @author Cristtian
 * @Date 21-08-2018
 */
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private LoginRolesController logCont = new LoginRolesController("formularios_telefonica", "", "");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			request.setAttribute("form", "/");
			request.getRequestDispatcher("html/login.jsp").forward(request, response);
		} catch (IOException e) {
			System.out.println("IOException: \n");
			e.printStackTrace();
		} catch (ServletException e) {
			System.out.println("ServletException: \n");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception: \n");
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			response.setContentType("text/plain");
			session.setAttribute("usuario", "Cristtian");
			String passEncrypt = DigestUtils.sha512Hex(password);
//			System.out.println("Encrytp "+passEncrypt);
			Usuario userLogin = this.logCont.login(username, passEncrypt);
			if (userLogin != null && session.getAttribute("userData") == null) {
				System.out.println("user "+userLogin);
				session.setAttribute("userData", userLogin);
				String res = this.validarRedirecTipoUser(userLogin,response);
				session.setAttribute("rol", res);
				session.setAttribute("id", userLogin.getId());
				response.getWriter().write(res);
				
			}else {
				System.out.println("DATOS INCORRECTOS");
				request.getSession().setAttribute("message", "Incorrecto");
				response.getWriter().write("/");
			}
			
		} catch (Exception e) {
			System.out.println("Exception: \n");
			e.printStackTrace();
			response.getWriter().write("Exception" + e);
		}
	
	}
	
	private String validarRedirecTipoUser(Usuario userLogin, HttpServletResponse response) {
		try {
			System.out.println(userLogin.getTipousuario().equalsIgnoreCase("superUser") + " ----");
			if(userLogin.getTipousuario().equalsIgnoreCase("superUser")) {
				return "superAdmin";
			} else {
				return "administrador";
			}
			
		} catch (Exception e) {
			System.out.println("ERROR al intentar redirecionar.");
			e.printStackTrace();
		}
		return null;
	}

}
