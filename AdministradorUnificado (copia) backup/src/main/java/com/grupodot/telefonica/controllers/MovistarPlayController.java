package com.grupodot.telefonica.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovistarPlayController extends GenericController {

	public MovistarPlayController() {
		super();
	}

	public MovistarPlayController(String dbName, String tableName, String headers) {
		super(dbName, tableName, headers);
	}

	@Override
	public String listar(String limit, String interval, String palabra) {
		String JsonStr = "", response = "[";
		boolean sucess = false;
		try {
			con = dataSource.getConnection(super.dbName);
			String consulta = 
					"SELECT " 
							+ "personas.nombre AS nombre," 
							+ "personas.cedula AS cedula,"
							+ "respuestas.respuesta1 AS respuesta1," 
							+ "respuestas.respuesta2 AS respuesta2,"
							+ "respuestas.respuesta3 AS respuesta3," 
							+ "respuestas.respuesta4 AS respuesta4,"
							+ "respuestas.respuesta5 AS respuesta5," 
							+ "respuestas.respuesta6 AS respuesta6,"
							+ "respuestas.respuesta7 AS tiempo" 
					+ " FROM "
						+ "formularios_telefonica.formulario_the_son_personas personas,"
						+ "formularios_telefonica.formulario_the_son_respuestas respuestas" 
					+ " WHERE "
						+ "respuestas.cc_Persona = personas.cedula COLLATE utf8_unicode_ci" 
					+ " order by " 
						+ "personas.fecha" 
					+ " LIMIT ?, ?";
			pst = con.prepareStatement(consulta);
			pst.setInt(1, Integer.parseInt(interval));
			pst.setInt(2, Integer.parseInt(limit));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				sucess = true;
				response += "[" 
							+ "\"" + rs.getObject("nombre") + "\" ," 
							+ "\"" + rs.getObject("cedula") + "\" ," 
							+ "\"" + rs.getObject("respuesta1") + "\" ," 
							+ "\"" + rs.getObject("respuesta2") + "\" ," 
							+ "\"" + rs.getObject("respuesta3") + "\" ," 
							+ "\"" + rs.getObject("respuesta4") + "\" ," 
							+ "\"" + rs.getObject("respuesta5") + "\" ," 
							+ "\"" + rs.getObject("respuesta6") + "\" ," 
							+ "\"" + rs.getObject("tiempo") + "\"" 
						+ "],";
			}
			if (sucess) {
				response = response.substring(0, response.length() - 1);
				response += "]";
			} else {
				response = "[]";
			}

			JsonStr = "[{" 
				+ "\"success\":\"" + sucess + "\"," 
				+ "\"numRows\":\"" + count() + "\","
				+ "\"headers\":" + headers + "," 
				+ "\"response\":" + response 
			+ "}]";
			dataSource.closeConnection(con);
		} catch (SQLException f) {
			f.printStackTrace();
		}
		return JsonStr;
	}

	@Override
	public String listar(String limite, String intervalo, String palabra, String fechaInicio, String fechaFin) {
		String JsonStr = "", response = "[";
		boolean sucess = false;
		try {
			con = dataSource.getConnection(super.dbName);
			String consulta = 
					"SELECT " 
							+ "personas.nombre AS nombre," 
							+ "personas.cedula AS cedula,"
							+ "respuestas.respuesta1 AS respuesta1," 
							+ "respuestas.respuesta2 AS respuesta2,"
							+ "respuestas.respuesta3 AS respuesta3," 
							+ "respuestas.respuesta4 AS respuesta4,"
							+ "respuestas.respuesta5 AS respuesta5," 
							+ "respuestas.respuesta6 AS respuesta6,"
							+ "respuestas.respuesta7 AS tiempo" 
					+ " FROM "
						+ "formularios_telefonica.formulario_the_son_personas personas,"
						+ "formularios_telefonica.formulario_the_son_respuestas respuestas" 
					+ " WHERE "
						+ "respuestas.cc_Persona = personas.cedula COLLATE utf8_unicode_ci" 
					+ " AND " 
						+ " personas.fecha BETWEEN ? AND ?"
					+ " order by " 
						+ "personas.fecha" 
					+ " LIMIT ?, ?";
			pst = con.prepareStatement(consulta);
			pst.setString(1, fechaInicio);
			pst.setString(2, fechaFin);
			pst.setInt(3, Integer.parseInt(intervalo));
			pst.setInt(4, Integer.parseInt(limite));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				sucess = true;
				response += "[" 
							+ "\"" + rs.getObject("nombre") + "\" ," 
							+ "\"" + rs.getObject("cedula") + "\" ," 
							+ "\"" + rs.getObject("respuesta1") + "\" ," 
							+ "\"" + rs.getObject("respuesta2") + "\" ," 
							+ "\"" + rs.getObject("respuesta3") + "\" ," 
							+ "\"" + rs.getObject("respuesta4") + "\" ," 
							+ "\"" + rs.getObject("respuesta5") + "\" ," 
							+ "\"" + rs.getObject("respuesta6") + "\" ," 
							+ "\"" + rs.getObject("tiempo") + "\"" 
						+ "],";
			}
			if (sucess) {
				response = response.substring(0, response.length() - 1);
				response += "]";
			} else {
				response = "[]";
			}

			JsonStr = "[{" 
				+ "\"success\":\"" + sucess + "\"," 
				+ "\"numRows\":\"" + count(fechaInicio, fechaFin) + "\","
				+ "\"headers\":" + headers + "," 
				+ "\"response\":" + response 
			+ "}]";
			dataSource.closeConnection(con);
		} catch (SQLException f) {
			f.printStackTrace();
		}
		return JsonStr;
	}

	@Override
	public String exportar() {
		String JsonStr = "[" + super.headers + ",";
		boolean sucess = false;
		try {
			con = dataSource.getConnection(super.dbName);
			String consulta = 
					"SELECT " 
							+ "personas.nombre AS nombre," 
							+ "personas.cedula AS cedula,"
							+ "respuestas.respuesta1 AS respuesta1," 
							+ "respuestas.respuesta2 AS respuesta2,"
							+ "respuestas.respuesta3 AS respuesta3," 
							+ "respuestas.respuesta4 AS respuesta4,"
							+ "respuestas.respuesta5 AS respuesta5," 
							+ "respuestas.respuesta6 AS respuesta6,"
							+ "respuestas.respuesta7 AS tiempo" 
					+ " FROM "
						+ "formularios_telefonica.formulario_the_son_personas personas,"
						+ "formularios_telefonica.formulario_the_son_respuestas respuestas" 
					+ " WHERE "
						+ "respuestas.cc_Persona = personas.cedula COLLATE utf8_unicode_ci"
					+ " order by " 
						+ "personas.fecha";
			pst = con.prepareStatement(consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				sucess = true;
				JsonStr += "[" 
						+ "\"" + rs.getObject("nombre") + "\" ," 
						+ "\"" + rs.getObject("cedula") + "\" ," 
						+ "\"" + rs.getObject("respuesta1") + "\" ," 
						+ "\"" + rs.getObject("respuesta2") + "\" ," 
						+ "\"" + rs.getObject("respuesta3") + "\" ," 
						+ "\"" + rs.getObject("respuesta4") + "\" ," 
						+ "\"" + rs.getObject("respuesta5") + "\" ," 
						+ "\"" + rs.getObject("respuesta6") + "\" ," 
						+ "\"" + rs.getObject("tiempo") + "\"" 
					+ "],";
			}
			if (sucess) {
				JsonStr = JsonStr.substring(0, JsonStr.length() - 1);
			} else {
				JsonStr = "[]";
			}
			JsonStr += "]";
			dataSource.closeConnection(con);
		} catch (SQLException f) {
			f.printStackTrace();
		}
		return JsonStr;
	}

	@Override
	public String exportar(String fechaInicio, String fechaFin) {
		String JsonStr = "[" + super.headers + ",";
		boolean sucess = false;
		try {
			con = dataSource.getConnection(super.dbName);
			String consulta = 
					"SELECT " 
							+ "personas.nombre AS nombre," 
							+ "personas.cedula AS cedula,"
							+ "respuestas.respuesta1 AS respuesta1," 
							+ "respuestas.respuesta2 AS respuesta2,"
							+ "respuestas.respuesta3 AS respuesta3," 
							+ "respuestas.respuesta4 AS respuesta4,"
							+ "respuestas.respuesta5 AS respuesta5," 
							+ "respuestas.respuesta6 AS respuesta6,"
							+ "respuestas.respuesta7 AS tiempo" 
					+ " FROM "
						+ "formularios_telefonica.formulario_the_son_personas personas,"
						+ "formularios_telefonica.formulario_the_son_respuestas respuestas" 
					+ " WHERE "
						+ "respuestas.cc_Persona = personas.cedula COLLATE utf8_unicode_ci" 
					+ " AND " 
						+ " personas.fecha BETWEEN ? AND ?"
					+ " order by " 
						+ "personas.fecha";
			pst = con.prepareStatement(consulta);
			pst.setString(1, fechaInicio);
			pst.setString(2, fechaFin);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				sucess = true;
				JsonStr += "[" 
						+ "\"" + rs.getObject("nombre") + "\" ," 
						+ "\"" + rs.getObject("cedula") + "\" ," 
						+ "\"" + rs.getObject("respuesta1") + "\" ," 
						+ "\"" + rs.getObject("respuesta2") + "\" ," 
						+ "\"" + rs.getObject("respuesta3") + "\" ," 
						+ "\"" + rs.getObject("respuesta4") + "\" ," 
						+ "\"" + rs.getObject("respuesta5") + "\" ," 
						+ "\"" + rs.getObject("respuesta6") + "\" ," 
						+ "\"" + rs.getObject("tiempo") + "\"" 
					+ "],";
			}
			if (sucess) {
				JsonStr = JsonStr.substring(0, JsonStr.length() - 1);
			} else {
				JsonStr = "[]";
			}
			JsonStr += "]";
			dataSource.closeConnection(con);
		} catch (SQLException f) {
			f.printStackTrace();
		}
		return JsonStr;
	}

	@Override
	public String count() {
		String str = "";
		try {
			con = dataSource.getConnection(super.dbName);
			String consulta = "SELECT count(*) As cantidad from " + super.tableName;
			pst = con.prepareStatement(consulta);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				str = rs.getInt("cantidad") + "";
			}
			dataSource.closeConnection(con);
		} catch (SQLException f) {
			f.printStackTrace();
		}
		return str;
	}

	@Override
	public String count(String fechaInicio, String fechaFin) {
		String str = "";
		try {
			con = dataSource.getConnection(super.dbName);
			String consulta = "SELECT count(*) As cantidad from " + super.tableName
					+ " WHERE fecha BETWEEN ? AND ?";
			pst = con.prepareStatement(consulta);
			pst.setString(1, fechaInicio);
			pst.setString(2, fechaFin);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				str = rs.getInt("cantidad") + "";
			}
			dataSource.closeConnection(con);
		} catch (SQLException f) {
			f.printStackTrace();
		}
		return str;
	}
}
