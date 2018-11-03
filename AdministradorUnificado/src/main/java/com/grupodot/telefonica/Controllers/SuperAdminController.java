package com.grupodot.telefonica.Controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.grupodot.telefonica.Models.Rol;

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
	
	public ArrayList<Object> showTables() {
		ArrayList<Object> sucess = new ArrayList<Object>();
		try {
		con = dataSource.getConnection("formularios_telefonica");
        String consulta = "SHOW TABLES";
        pst = con.prepareStatement(consulta);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
        	sucess.add(rs.getString(1));
        	
        }
        con.close();
		} catch (SQLException f) {
	        f.printStackTrace();
	    }
		return sucess;
	}
	
	public ArrayList<Object> showRoles() {
		ArrayList<Object> sucess = new ArrayList<Object>();
		try {
		con = dataSource.getConnection("formularios_telefonica");
        String consulta = "Select * from rol";
        pst = con.prepareStatement(consulta);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
        	sucess.add(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3));
        }
        con.close();
		} catch (SQLException f) {
	        f.printStackTrace();
	    }
		
		return sucess;
	}

	
	public String listUser() {
		String JsonStr = "", response = "[", consulta = "";
    	boolean bool = false;
		try {
		con = dataSource.getConnection("formularios_telefonica");
		 consulta = "SELECT userr.id,userr.username, userr.nombreCompleto, CONCAT(UPPER(LEFT(tdu.nombre,1)),SUBSTR(tdu.nombre,2)) FROM usuario AS userr, tipo_de_usuario AS tdu WHERE tdu.id = userr.fkTipoUsuario order by userr.id asc";
		 
        pst = con.prepareStatement(consulta);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
        	
        	bool = true;
        	response += "[" 
        			+ "\"" + rs.getString(1)
                    + "\" ," + "\"" + rs.getString(2)
                    + "\" ," + "\"" + rs.getString(3)
                    + "\" ," + "\"" + rs.getString(4).toString().trim()
                    + "\" ," + "\"" + "<button type='button' class='btn btn-primary btn-sm'><i class='zmdi zmdi-edit zmdi-hc-fw'></i></button><button type='button' class='btn btn-danger btn-sm'><i class='zmdi zmdi-delete zmdi-hc-fw'></i></button><button type='button' class='btn btn-warning btn-sm'><i class='zmdi zmdi-eye zmdi-hc-fw'></i></button>"
                    + "\""
                    + "],";	
        }
        if(bool) {
        	response = response.substring(0, response.length()-1);
        	response += "]";
        }else {
        	response="[]";
        }
		JsonStr = "{"
	   	 + "\"data\":"+response
		 + "}"; 
        
       
        con.close();
		} catch (SQLException f) {
	        f.printStackTrace();
	    }
		
		return JsonStr;
	}
		
}
