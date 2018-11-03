package com.grupodot.telefonica.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SuperAdminController extends GenericController{
	
	public SuperAdminController() {
		super();
	}

	public SuperAdminController(String dbName, String tableName, String headers) {
		super(dbName, tableName, headers);
	}
	
	@Override
	public String exportar() {
		return "[]";
	}
	
	@Override
	public String exportar(String fechaInicio, String fechaFin) {
        return "[]";
	}
	
	@Override
	public String listar(String limite, String intervalo, String palabra) {
    	String JsonStr = "", response = "[", consulta = "";
    	boolean sucess = false;
    	
    	try {
    		con = dataSource.getConnection(this.dbName);
            
            if(this.tableName.equalsIgnoreCase("usuario")) {
                consulta = "SELECT "
                			+ "usuario.id AS id,"
                			+ "usuario.nombreCompleto AS nombreCompleto,"
                			+ "usuario.username AS user,"
                			+ "tipo_de_usuario.nombre AS tipousuario "
                		+ " FROM "
                			+ "usuario, tipo_de_usuario "
                		+ " WHERE "
                			+ "usuario.fkTipoUsuario = tipo_de_usuario.id"
                		+ " LIMIT ?,?";
                
            } else if(this.tableName.equalsIgnoreCase("usuario_rol")) {
            	consulta = "SELECT "
            			+ "rol.id AS idRol,"
            			+ "rol.nombreTabla AS nombreTabla,"
            			+ "rol.descripcion AS rolDesc,"
            			+ "usuario_rol.descripcion AS asigRol" 
            		+ " FROM "
            			+ "usuario_rol, rol, usuario"
            		+ " WHERE "
            			+ "rol.id = usuario_rol.idRol"
            		+ " AND "
            			+ "usuario_rol.idUsuario = usuario.id"
            		+ " LIMIT ?,?";
            	
            } else if(this.tableName.equalsIgnoreCase("rol")) {
            	consulta = "SELECT "
            				+ "*"
            			+ " FROM "
            				+ "rol"
            			+ " LIMIT ?,?";
            }
            
            pst = con.prepareStatement(consulta);
            pst.setInt(1, Integer.parseInt(intervalo));
            pst.setInt(2, Integer.parseInt(limite));
            
            ResultSet rs = pst.executeQuery();
            
            if(this.tableName.equalsIgnoreCase("usuario")) {
            	while (rs.next()) {
    	        	sucess = true;
    	        	response += "[" 
    	        			+ "\"" + rs.getObject("id")
    	                    + "\" ," + "\"" + rs.getObject("nombreCompleto")
    	                    + "\" ," + "\"" + rs.getObject("user")
    	                    + "\" ," + "\"" + rs.getObject("tipousuario")
    	                    + "\""
    	                    + "],";	        	
    	        }
            } else if(this.tableName.equalsIgnoreCase("usuario_rol")) {
            	while (rs.next()) {
    	        	sucess = true;
    	        	response += "[" 
    	        			+ "\"" + rs.getObject("idRol")
    	                    + "\" ," + "\"" + rs.getObject("nombreTabla")
    	                    + "\" ," + "\"" + rs.getObject("rolDesc")
    	                    + "\" ," + "\"" + rs.getObject("asigRol")
    	                    + "\""
    	                    + "],";	        	
    	        }
            } else if(this.tableName.equalsIgnoreCase("rol")) {
            	while (rs.next()) {
    	        	sucess = true;
    	        	response += "[" 
    	        			+ "\"" + rs.getObject("id")
    	                    + "\" ," + "\"" + rs.getObject("nombreTabla")
    	                    + "\" ," + "\"" + rs.getObject("descripcion")
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
			 + "\"numRows\":\""+count()+"\"," 
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
	public String listar(String limite, String intervalo, String palabra, String fechaInicio, String fechaFin) {
    	String JsonStr = "[{"
			 + "\"success\":\"false\"," 
			 + "\"numRows\":\""+count(fechaInicio, fechaFin)+"\"," 
		   	 + "\"headers\":"+this.headers+"," 
			 + "\"response\":[]"
			 + "}]"; 
        return JsonStr;
	}
	
	@Override
	public String count() {
		String str = "";
    	try {
    		con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT count(*) As cantidad from " + this.tableName;            
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
        return "0";
	}
}
