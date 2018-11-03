package com.grupodot.telefonica.views;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.grupodot.telefonica.conexion.Utilitarios;
import com.grupodot.telefonica.controllers.GenericController;
import com.grupodot.telefonica.controllers.LoginRolesController;
import com.grupodot.telefonica.models.SqlVars;
import com.grupodot.telefonica.models.Usuario;

/**
 * 
 * @author Julian Cuestas
 * @Date 21-08-2018
 */
public class Admin  extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private GenericController genCont;
	private LoginRolesController logCont = new LoginRolesController("formularios_telefonica", "", "");
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String str = "";
		response.setCharacterEncoding("UTF-8");
		try {
			String opcion = request.getParameter("opcion");
			String table = request.getParameter("tabla");
			String fechaInicio = Utilitarios.setHoursToDateStr(request.getParameter("fechaInicio"), "00:00:00");
			String fechaFin = Utilitarios.setHoursToDateStr(request.getParameter("fechaFin"), "23:59:59");
			Usuario ArrUser = (Usuario) request.getSession().getAttribute("userData");
			if (ArrUser != null) {
				if (opcion.equalsIgnoreCase("listar")) {
					String limit = request.getParameter("limit");
					String interval = request.getParameter("interval");
					String palabra = request.getParameter("palabra");
					System.out.println("listarServeRes: " + fechaInicio + " --- " + fechaFin + " ---- " + table);
					this.genCont = SqlVars.assigntableJsName(table);
					if (fechaInicio.equalsIgnoreCase("") || fechaFin.equalsIgnoreCase("")) {
						str = genCont.listar(limit, interval, palabra);
					} else {
						str = genCont.listar(limit, interval, palabra, fechaInicio, fechaFin);
					}

				} else if (opcion.equalsIgnoreCase("editar")) {

					Map<String, Object> params = new HashMap<String, Object>();

					System.out.println("editar: " + table);

					if (table.equalsIgnoreCase("usuario")) {
						int id = Integer.parseInt(request.getParameter("usuario"));
						String nombre = request.getParameter("nombre");
						String tipousuario = request.getParameter("tipousuario");

						params.put("id", id);
						params.put("nombreCompleto", nombre);
						params.put("fkTipoUsuario", tipousuario);

						genCont = new GenericController("formularios_telefonica", table, "");

						str = genCont.update(params, id) + "";
					}

				} else if (opcion.equalsIgnoreCase("exportar")) {
					System.out.println("export: " + fechaInicio + " ---- " + fechaFin);
					genCont = SqlVars.assigntableJsName(table);

					response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + table + ".xlsx");

					File file = null;

					if (fechaInicio.equalsIgnoreCase("") || fechaFin.equalsIgnoreCase("")) {
						file = genCont.generarExcelPOI();
					} else {
						file = this.genCont.generarExcelPOI(fechaInicio, fechaFin);
					}
					if (file != null) {
						try {
							OutputStream os = response.getOutputStream();
							InputStream in = new FileInputStream(new File(file.getAbsolutePath()));
							if (in == null) {
								os.close();
							} else {
								byte[] buffer = new byte[4096];
								int len;

								while ((len = in.read(buffer)) != -1) {
									os.write(buffer, 0, len);
								}
								os.flush();
								in.close();
								os.close();
							}
						} catch (Exception e) {
							System.out.println("ERROR al realizar enviar response.");
							e.printStackTrace();
						} finally {
							response.getOutputStream().flush();
							response.getOutputStream().close();
						}
					}
					return;
				} else if (opcion.equalsIgnoreCase("exportJSON")) {
					if (fechaInicio.equalsIgnoreCase("") || fechaFin.equalsIgnoreCase("")) {
						str = genCont.exportar();
					} else {
						str = genCont.exportar(fechaInicio, fechaFin);
					}
				} else if (opcion.equalsIgnoreCase("count")) {
					System.out.println("count: " + fechaInicio + " --- " + fechaFin);

					genCont = SqlVars.assigntableJsName(table);

					if (fechaInicio.equalsIgnoreCase("") || fechaFin.equalsIgnoreCase("")) {
						str = genCont.count();
					} else {
						str = genCont.count(fechaInicio, fechaFin);
					}

				} else if (opcion.equalsIgnoreCase("registrar")) {
					Map<Integer, Object> params = new HashMap<Integer, Object>();
					System.out.println("registar --- " + table);

					genCont = SqlVars.assigntableJsName(table);

					if (table.equalsIgnoreCase("usuario")) {
						String tipousuario = request.getParameter("tipoUsuario");
						String username = request.getParameter("username");
						String password = request.getParameter("password");
						String nombre = request.getParameter("nombre");

						String passEncrypt = DigestUtils.sha512Hex(password);

						params.put(1, username);
						params.put(2, passEncrypt);
						params.put(3, nombre);
						params.put(4, tipousuario);

						if (logCont.validaUsername(username)) {
							str = "duplicated";
						} else {
							str = genCont.insert(params, params.size(), true) + "";
						}

					} else if (table.equalsIgnoreCase("rol")) {
						String nombre = request.getParameter("nombre");
						String descripcion = request.getParameter("descripcion");

						params.put(1, nombre);
						params.put(2, descripcion);

						if (logCont.validaRol(nombre)) {
							str = "duplicated";
						} else {
							str = genCont.insert(params, params.size(), false) + "";
						}

					} else if (table.equalsIgnoreCase("usuario_rol")) {
						int usuario = Integer.parseInt(request.getParameter("usuario"));
						int rol = Integer.parseInt(request.getParameter("rol"));
						String descripcion = request.getParameter("descripcion");

						params.put(1, usuario);
						params.put(2, rol);
						params.put(3, descripcion);

						if (logCont.validaRolPorUsuario(usuario, rol)) {
							str = "duplicated";
						} else {
							str = genCont.insert(params, params.size(), false) + "";
						}
					}

				} else if (opcion.equalsIgnoreCase("getRolesUsuario")) {
					int usuario = Integer.parseInt(request.getParameter("usuario"));
					System.out.println("getroles --- " + usuario);
					str = logCont.getRolesArray(logCont.getRoles(usuario));

				} else if (opcion.equalsIgnoreCase("getAllRoles")) {
					System.out.println("get all roles");
					// ArrUser = (Usuario) request.getSession().getAttribute("userData");
					if (ArrUser != null) {
						str = logCont.getRolesArray(ArrUser.getRoles());
					} else {
						str = logCont.getRolesArray(logCont.getAllRoles());
					}
				} else if (opcion.equalsIgnoreCase("delete")) {
					System.out.println("getroles --- " + table);

					if (table.equalsIgnoreCase("usuario_rol")) {
						int usuario = Integer.parseInt(request.getParameter("usuario"));
						String rol = request.getParameter("rol");
						if (rol.equalsIgnoreCase("") || rol.isEmpty()) {
							str = logCont.DeleteAllRol(usuario) + "";
						} else {
							str = logCont.DeleteRol(usuario, Integer.parseInt(rol)) + "";
						}

					} else {
						str = "false";
					}
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
