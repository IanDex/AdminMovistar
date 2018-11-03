package com.grupodot.telefonica.Views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.Gson;
import com.grupodot.telefonica.Controllers.SuperAdminController;
import com.grupodot.telefonica.Models.Usuario;

/**
 * Servlet implementation class superAdminServlet
 */
@WebServlet("/superAdminServlet")
public class superAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SuperAdminController superAdmin = new SuperAdminController();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public superAdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			request.setAttribute("form", "superAdmin");
			request.getRequestDispatcher("html/superAdmin.jsp").forward(request, response);
			
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
		String str = "";
		String nomAdmin = "";
		response.setCharacterEncoding("UTF-8");
		try {
			String opcion = request.getParameter("opcion");
			String table = request.getParameter("tabla");	
			Usuario ArrUser = (Usuario) request.getSession().getAttribute("userData");
			if (ArrUser != null) {
				if (opcion.equalsIgnoreCase("changePass")) {
					String passold = request.getParameter("passold");
					String passnew = request.getParameter("passnew");
					String passoldEncrypt = DigestUtils.sha512Hex(passold);
					String passnewEncryptd = DigestUtils.sha512Hex(passnew);
					String id = (String) request.getSession(false).getAttribute("id");
					
				}else if (opcion.equalsIgnoreCase("showTables")) {
//					System.out.println("Mensaje - " + superAdmin.showTables());
					str = new Gson().toJson(superAdmin.showTables());
				}else if (opcion.equalsIgnoreCase("showPermisos")) {
//					System.out.println("Mensaje - " + superAdmin.showTables());
					str = new Gson().toJson(superAdmin.showRoles());
				}else if (opcion.equalsIgnoreCase("listar")) {
					
					str = superAdmin.listUser();
					
				}
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();

				out.print(str);
				out.flush();
				out.close();

			} else {
				System.out.println("USER IS NULL");
				response.sendRedirect("/html/Admin/loginAdmin.jsp");
			}
		} catch (Exception e) {
			System.out.println("Exception: \n");
			e.printStackTrace();
		}
	}

}
