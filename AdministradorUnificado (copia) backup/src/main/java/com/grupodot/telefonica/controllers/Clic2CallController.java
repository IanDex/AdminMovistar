package com.grupodot.telefonica.controllers;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Clic2CallController extends GenericController{

	public Clic2CallController(){
		super();
	}
	
	public Clic2CallController(String dbName, String tableName, String headers){
		super(dbName, tableName, headers);
	}
	
	@Override
	public String listar(String limit, String interval, String palabra) {
		String JsonStr = "", response = "[";
    	boolean sucess = false;
    	try {
			con = dataSource.getConnection(super.dbName);
	        String consulta = "SELECT"
					+ " id AS id,"
				    + " new_cod_plan AS productos,"
				    + " fecha_creacion AS fecha,"
				    + " linea_cambio AS telefono,"
				    + " id_campana AS campania,"
				    + " origen AS origen,"
				    + " mail AS mail,"
				    + " retornoWS AS WS"
				    + " FROM"
					+ " pos_pos_formulario_tarifa"
					+ " GROUP BY linea_cambio"
					+ " LIMIT ?, ?";	    	
	        pst = con.prepareStatement(consulta);
	        pst.setInt(1, Integer.parseInt(interval));
	        pst.setInt(2, Integer.parseInt(limit));
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	        	sucess = true;
	        	response += "[" 
	        			+ "\"" + rs.getObject("id")
	                    + "\" ," + "\"" + rs.getObject("productos")
	                    + "\" ," + "\"" + rs.getObject("fecha")
	                    + "\" ," + "\"" + rs.getObject("telefono")
	                    + "\" ," + "\"" + rs.getObject("campania")
	                    + "\" ," + "\"" + rs.getObject("origen")
	                    + "\" ," + "\"" + rs.getObject("mail")
	                    + "\" ," + "\"" + rs.getObject("WS")
	                    + "\""
	                    + "],";	        	
	        }
	        if(sucess) {
	        	response = response.substring(0, response.length()-1);
	        	response += "]";
	        }else {
	        	response="[]";
	        }
			
			JsonStr = "[{"
					 + "\"success\":\""+sucess+"\"," 
					 + "\"numRows\":\""+count()+"\"," 
				   	 + "\"headers\":"+super.headers+"," 		   	
					 + "\"response\":"+response
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
    		System.out.println("Listar Click2Call");
    		con = dataSource.getConnection(super.dbName);
            String consulta = "SELECT"
					+ " id AS id,"
				    + " new_cod_plan AS productos,"
				    + " fecha_creacion AS fecha,"
				    + " linea_cambio AS telefono,"
				    + " id_campana AS campania,"
				    + " origen AS origen,"
				    + " mail AS mail,"
				    + " retornoWS AS WS"
				+ " FROM"
					+ " pos_pos_formulario_tarifa"
				+ " WHERE"
					+ " fecha_creacion BETWEEN ? AND ?"
				+ " GROUP BY linea_cambio"
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
	        			+ "\"" + rs.getObject("id")
	                    + "\" ," + "\"" + rs.getObject("productos")
	                    + "\" ," + "\"" + rs.getObject("fecha")
	                    + "\" ," + "\"" + rs.getObject("telefono")
	                    + "\" ," + "\"" + rs.getObject("campania")
	                    + "\" ," + "\"" + rs.getObject("origen")
	                    + "\" ," + "\"" + rs.getObject("mail")
	                    + "\" ," + "\"" + rs.getObject("WS")
	                    + "\""
	                    + "],";	        	
	        }
	        if(sucess) {
	        	response = response.substring(0, response.length()-1);
	        	response += "]";
	        }else {
	        	response="[]";
	        }

			JsonStr = "[{"
			 + "\"success\":\""+sucess+"\"," 
			 + "\"numRows\":\""+count(fechaInicio, fechaFin)+"\"," 
		   	 + "\"headers\":"+this.headers+"," 
			 + "\"response\":"+response
			 + "}]"; 
			dataSource.closeConnection(con);
	    } catch (SQLException f) {
	        f.printStackTrace();
	    }
        return JsonStr;
	}
	
	@Override
	public String exportar() {
		System.out.println("listar tarifas");
        return "[]";
	}
	
	@Override
	public String exportar(String fechaInicio, String fechaFin) {
		String JsonStr = "["+super.headers+",";
    	boolean sucess = false;
    	try {
    		con = dataSource.getConnection(super.dbName);
            String consulta = "SELECT"
					+ " id AS id,"
				    + " new_cod_plan AS productos,"
				    + " fecha_creacion AS fecha,"
				    + " linea_cambio AS telefono,"
				    + " id_campana AS campania,"
				    + " origen AS origen,"
				    + " mail AS mail,"
				    + " retornoWS AS WS"
				+ " FROM"
					+ " pos_pos_formulario_tarifa"
				+ " WHERE"
					+ " fecha_creacion BETWEEN ? AND ?"
				+ " GROUP BY linea_cambio";     	
            pst = con.prepareStatement(consulta);
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	        	sucess = true;
	        	JsonStr += "[" 
	        			+ "\"" + rs.getObject("id")
	                    + "\" ," + "\"" + rs.getObject("productos")
	                    + "\" ," + "\"" + rs.getObject("fecha")
	                    + "\" ," + "\"" + rs.getObject("telefono")
	                    + "\" ," + "\"" + rs.getObject("campania")
	                    + "\" ," + "\"" + rs.getObject("origen")
	                    + "\" ," + "\"" + rs.getObject("mail")
	                    + "\" ," + "\"" + rs.getObject("WS")
	                    + "\""
	                    + "],";
	        }
	        if(sucess) {
	        	JsonStr = JsonStr.substring(0, JsonStr.length()-1);
	        }else {
	        	JsonStr="[]";
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
            String consulta = "SELECT count(*) As cantidad from "+super.tableName;            
            pst = con.prepareStatement(consulta);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	        	str = rs.getInt("cantidad")+"";
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
            String consulta = "SELECT count(*) As cantidad from "+super.tableName+" WHERE fecha_creacion BETWEEN ? AND ?";            
            pst = con.prepareStatement(consulta);
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	        	str = rs.getInt("cantidad")+"";
	        }
	        dataSource.closeConnection(con);
	    } catch (SQLException f) {
	        f.printStackTrace();
	    }
        return str;
	}
	
	@Override
	public File generarExcelPOI() {
		long startTime = System.nanoTime();
		try {
			con = dataSource.getConnection(this.dbName);
			
			String consulta = "SELECT"
					+ " id AS id,"
				    + " new_cod_plan AS productos,"
				    + " fecha_creacion AS fecha,"
				    + " linea_cambio AS telefono,"
				    + " id_campana AS campania,"
				    + " origen AS origen,"
				    + " mail AS mail,"
				    + " retornoWS AS WS"
				    + " FROM"
					+ " pos_pos_formulario_tarifa"
					+ " GROUP BY linea_cambio";	
	        pst = con.prepareStatement(consulta);
	        ResultSet rs = pst.executeQuery();
	        if(rs.next()) {
	        	File file = super.generarExcel(rs);
	        	System.out.println("TIEMPO DE EJECUCION "+(System.nanoTime()-startTime));
	        	return file;
	        }
	        return null;
		} catch (Exception e1) {
			System.out.println("ERROR: al realizar consula a la BDs.");
			dataSource.closeConnection(con);
			e1.printStackTrace();
			return null;
		}
	}
	
	@Override
	public File generarExcelPOI(String fechaInicio, String fechaFin) {
		long startTime = System.nanoTime();
    	try {
    		con = dataSource.getConnection(this.dbName);
    		
    		String consulta = "SELECT"
					+ " id AS id,"
				    + " new_cod_plan AS productos,"
				    + " fecha_creacion AS fecha,"
				    + " linea_cambio AS telefono,"
				    + " id_campana AS campania,"
				    + " origen AS origen,"
				    + " mail AS mail,"
				    + " retornoWS AS WS"
				+ " FROM"
					+ " pos_pos_formulario_tarifa"
				+ " WHERE"
					+ " fecha_creacion BETWEEN ? AND ?"
				+ " GROUP BY linea_cambio";     	
            pst = con.prepareStatement(consulta);
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);
           
	        ResultSet rs = pst.executeQuery();
	        if(rs.next()) {
	        	File file = super.generarExcel(rs);
	        	System.out.println("TIEMPO DE EJECUCION "+(System.nanoTime()-startTime));
	        	return file;
	        }
	        return null;
		} catch (Exception e1) {
			System.out.println("ERROR: al realizar consula a la BDs.");
			dataSource.closeConnection(con);
			e1.printStackTrace();
			return null;
		}
	}
}
