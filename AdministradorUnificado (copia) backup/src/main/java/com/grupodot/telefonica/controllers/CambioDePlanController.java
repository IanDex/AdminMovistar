package com.grupodot.telefonica.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CambioDePlanController extends GenericController{
	protected String instancia;
	protected String username;
	protected String password;

	public CambioDePlanController(){
		super();
	}
	
	public CambioDePlanController(String dbName, String tableName, String headers){
		super(dbName, tableName, headers);
	}
	
	public CambioDePlanController(String instance, String dbName, String tableName, String username, String password, String headers){
		super(instance, dbName, tableName, username, password, headers);
		this.instancia = instance;
		this.username = username;
		this.password = password;
	}
	
	@Override
	public String listar(String limit, String interval, String palabra) {
	  	String JsonStr = "", response = "[";
    	boolean sucess = false;
    	try {
    		con = dataSource.getConnection(this.instancia, super.dbName, this.username, this.password);
            String consulta = "SELECT * "
            		+ "FROM " + super.tableName + 
                        " ORDER BY fecha_registro desc"+
                        " LIMIT ?, ?"; 
            pst = con.prepareStatement(consulta);
            pst.setInt(1, Integer.parseInt(interval));
            pst.setInt(2, Integer.parseInt(limit));
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	        	sucess = true;
	        	if(super.tableName.equalsIgnoreCase("formulario_cambio_plan_pre_pos")) {
		        	response += "[" 
		        			+ "\"" + rs.getObject("id")
		                    + "\" ," + "\"" + rs.getString("fecha_registro")
		                    + "\" ," + "\"" + rs.getObject("numero_celular")
		                    + "\" ," + "\"" + rs.getObject("email")
		                    + "\" ," + "\"" + rs.getObject("servicio_mas_consumido")
		                    + "\" ," + "\"" + rs.getObject("valor_recarga_mensual")		       
		                    + "\""
		                    + "],";	 
	        	}else {	
		        	response += "[" 
		        			+ "\"" + rs.getObject("id")
		                    + "\" ," + "\"" + rs.getString("fecha_registro")
		                    + "\" ," + "\"" + rs.getObject("numero_celular")
		                    + "\" ," + "\"" + rs.getObject("cedula")
		                    + "\" ," + "\"" + rs.getObject("email")
		                    + "\""
		                    + "],";	 
	        	}
	        }
	        if(response.length() > 10) {
	        	sucess = true;
	        	response = response.substring(0, response.length()-1);
	        	response += "]";
	        }else {
	        	response="[]";
	        }
	        System.out.println(response);
	        JsonStr = "[{"
	   			 + "\"success\":\""+sucess+"\"," 
	   			 + "\"numRows\":\""+this.count()+"\"," 
	   		   	 + "\"headers\":"+super.headers+"," 
	   			 + "\"response\":"+response
	   			 + "}]";
			System.out.println(JsonStr);
	        dataSource.closeConnection(con);
	    } catch (SQLException f) {
	        f.printStackTrace();
	    } catch (Exception f) {
	        f.printStackTrace();
	    }
        return JsonStr;
	}
	
	@Override
	public String listar(String limite, String intervalo, String palabra, String fechaInicio, String fechaFin) {
    	String JsonStr = "", response = "[";
    	boolean sucess = false;
    	try {
    		con = dataSource.getConnection(this.instancia, super.dbName, this.username, this.password);
            String consulta = "SELECT * "
            		+ "FROM " + super.tableName 
				+ " WHERE fecha_registro BETWEEN ? AND ?"
				+ " LIMIT ?, ?";	    	
            pst = con.prepareStatement(consulta);
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);
            pst.setInt(3, Integer.parseInt(intervalo));
            pst.setInt(4, Integer.parseInt(limite));
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	        	sucess = true;
	        	if(super.tableName.equalsIgnoreCase("formulario_cambio_plan_pre_pos")) {
		        	response += "[" 
		        			+ "\"" + rs.getObject("id")
		                    + "\" ," + "\"" + rs.getString("fecha_registro")
		                    + "\" ," + "\"" + rs.getObject("numero_celular")
		                    + "\" ," + "\"" + rs.getObject("email")
		                    + "\" ," + "\"" + rs.getObject("servicio_mas_consumido")
		                    + "\" ," + "\"" + rs.getObject("valor_recarga_mensual")		       
		                    + "\""
		                    + "],";	 
	        	}else {	        	
		        	response += "[" 
		        			+ "\"" + rs.getObject("id")
		                    + "\" ," + "\"" + rs.getString("fecha_registro")
		                    + "\" ," + "\"" + rs.getObject("numero_celular")
		                    + "\" ," + "\"" + rs.getObject("email")
		                    + "\" ," + "\"" + rs.getObject("cedula")
		                    + "\" ," + "\"" + rs.getObject("estado")
		                    + "\""
		                    + "],";	 
	        	}       	
	        }
	        if(sucess) {
	        	response = response.substring(0, response.length()-1);
	        	response += "]";
	        }else {
	        	response="[]";
	        }

			JsonStr = "[{"
			 + "\"success\":\""+sucess+"\"," 
			 + "\"numRows\":\""+this.count(fechaInicio, fechaFin)+"\"," 
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
	public String exportar() {
		String JsonStr = "["+super.headers+",";
    	boolean sucess = false;
    	try {
    		con = dataSource.getConnection(this.instancia, super.dbName, this.username, this.password);
            String consulta = "SELECT * "
            		+ "FROM " + super.tableName;
            pst = con.prepareStatement(consulta);
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	        	sucess = true;	        	
	        	if(super.tableName.equalsIgnoreCase("formulario_cambio_plan_pre_pos")) {
	        		JsonStr += "[" 
		        			+ "\"" + rs.getObject("id")
		                    + "\" ," + "\"" + rs.getString("fecha_registro")
		                    + "\" ," + "\"" + rs.getObject("numero_celular")
		                    + "\" ," + "\"" + rs.getObject("email")
		                    + "\" ," + "\"" + rs.getObject("servicio_mas_consumido")
		                    + "\" ," + "\"" + rs.getObject("valor_recarga_mensual")		       
		                    + "\""
		                    + "],";	 
	        	}else {	        	
	        		JsonStr += "[" 
		        			+ "\"" + rs.getObject("id")
		                    + "\" ," + "\"" + rs.getString("fecha_registro")
		                    + "\" ," + "\"" + rs.getObject("numero_celular")
		                    + "\" ," + "\"" + rs.getObject("email")
		                    + "\" ," + "\"" + rs.getObject("cedula")
		                    + "\" ," + "\"" + rs.getObject("estado")
		                    + "\""
		                    + "],";	 
	        	}

	        	System.out.println(rs.getObject("id"));
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
    	System.out.println(JsonStr);
        return JsonStr;
	}
	
	@Override
	public String exportar(String fechaInicio, String fechaFin) {
		String JsonStr = "["+super.headers+",";
    	boolean sucess = false;
    	try {
    		con = dataSource.getConnection(this.instancia, super.dbName, this.username, this.password);
            String consulta = "SELECT * "
            		+ "FROM " + super.tableName 
				+ " WHERE fecha_registro BETWEEN ? AND ?";
            pst = con.prepareStatement(consulta);
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	        	sucess = true;
	        	if(super.tableName.equalsIgnoreCase("formulario_cambio_plan_pre_pos")) {
	        		JsonStr += "[" 
		        			+ "\"" + rs.getObject("id")
		                    + "\" ," + "\"" + rs.getString("fecha_registro")
		                    + "\" ," + "\"" + rs.getObject("numero_celular")
		                    + "\" ," + "\"" + rs.getObject("email")
		                    + "\" ," + "\"" + rs.getObject("servicio_mas_consumido")
		                    + "\" ," + "\"" + rs.getObject("valor_recarga_mensual")		       
		                    + "\""
		                    + "],";	 
	        	}else {	        	
	        		JsonStr += "[" 
		        			+ "\"" + rs.getObject("id")
		                    + "\" ," + "\"" + rs.getString("fecha_registro")
		                    + "\" ," + "\"" + rs.getObject("numero_celular")
		                    + "\" ," + "\"" + rs.getObject("email")
		                    + "\" ," + "\"" + rs.getObject("cedula")
		                    + "\" ," + "\"" + rs.getObject("estado")
		                    + "\""
		                    + "],";	 
	        	}

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
    		con = dataSource.getConnection(this.instancia, super.dbName, this.username, this.password);
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
    		con = dataSource.getConnection(this.instancia, super.dbName, this.username, this.password);
            String consulta = "SELECT count(*) As cantidad from "+super.tableName+" WHERE fecha_registro BETWEEN ? AND ?";            
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
}
