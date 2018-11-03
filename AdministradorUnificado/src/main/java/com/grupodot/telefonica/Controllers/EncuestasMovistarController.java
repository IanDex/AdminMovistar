package com.grupodot.telefonica.Controllers;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EncuestasMovistarController extends GenericController {
	
	public EncuestasMovistarController() {
		super();
	}
	
	public EncuestasMovistarController(String dbName, String tableName, String headers, String tipoEncuesta){
		super(dbName, tableName, headers, tipoEncuesta);
	}
	
	/**
	 * @param palabra --> en este caso es el filtro de tipo de encuesta
	 * (SOPORTE_MOVIL, SOPORTE_FIJO, ATENCION_MASIVA, SATISFACCION_PYMES, SATISFACCION_CORPORACIONES)
	 */
	@Override
	public String listar(String limit, String interval, String palabra) {
		String JsonStr = "", response = "[";
    	boolean sucess = false;
    	try {
    		con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT id, numero_linea AS número_asociado,"+
            		" satisfaccion AS satisfacción, tiempo, resuelta, amabilidad,"+
            		" comentarios, fecha FROM " + this.tableName + "" +
            		" WHERE tipo_encuesta = ?"+
                    " ORDER BY fecha desc"+ 
                    " limit ?, ?";
            	pst = con.prepareStatement(consulta);
            	pst.setString(1, super.filtroTable);
                pst.setInt(2, Integer.parseInt(interval));
                pst.setInt(3, Integer.parseInt(limit));
	        ResultSet rs = pst.executeQuery();
	        response = IterateResultSet(rs, this.headers);
	        if(response.length() > 10) {
	        	sucess = true;
	        	response = response.substring(0, response.length()-1);
	        	response += "]";
	        }else {
	        	response="[]";
	        }
			JsonStr = "[{"
			 + "\"success\":\""+sucess+"\"," 
			 + "\"numRows\":\""+this.count()+"\"," 
		   	 + "\"headers\":"+this.headers+"," 
		   	 + "\"response\":["+response
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
    		con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT id, numero_linea AS número_asociado," + 
            		" satisfaccion AS satisfacción, tiempo, resuelta, amabilidad," + 
            		" comentarios, fecha FROM " + this.tableName +
            		" WHERE fecha BETWEEN ? AND ? AND tipo_encuesta = ?"+
                    " ORDER BY fecha desc"+
                    " LIMIT ?, ?";
            pst = con.prepareStatement(consulta);
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);
            pst.setString(3, super.filtroTable);
            pst.setInt(4, Integer.parseInt(intervalo));
            pst.setInt(5, Integer.parseInt(limite));
	        ResultSet rs = pst.executeQuery();
	        
	        response = IterateResultSet(rs, this.headers);
	        if(response.length() > 10) {
	        	sucess = true;
	        	response = response.substring(0, response.length()-1);
	        	response += "]";
	        }else {
	        	response="[]]";//added   " ]  "
	        }

			JsonStr = "[{"
			 + "\"success\":\""+sucess+"\"," 
			 + "\"numRows\":\""+count(fechaInicio, fechaFin)+"\"," 
		   	 + "\"headers\":"+this.headers+"," 
			 + "\"response\":["+response
			 + "}]"; 
	        
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
            String consulta = "SELECT count(*) As cantidad from "+super.tableName +" WHERE tipo_encuesta = ?";            
            pst = con.prepareStatement(consulta);
            pst.setString(1, super.filtroTable);
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
    		con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT count(*) As cantidad from " + this.tableName +
            		" WHERE fecha BETWEEN ? AND ? AND tipo_encuesta = ?";  
            pst = con.prepareStatement(consulta);
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);
            pst.setString(3, super.filtroTable);
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
			String consulta = "SELECT id, numero_linea AS número_asociado,"+
            		" satisfaccion AS satisfacción, tiempo, resuelta, amabilidad,"+
            		" comentarios, fecha FROM " + this.tableName + "" +
            		" WHERE tipo_encuesta = ?"+
                    " ORDER BY fecha desc";
	        pst = con.prepareStatement(consulta);
	        pst.setString(1, super.filtroTable);
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
    		String consulta = "SELECT id, numero_linea AS número_asociado," + 
            		" satisfaccion AS satisfacción, tiempo, resuelta, amabilidad," + 
            		" comentarios, fecha FROM " + this.tableName +
            		" WHERE fecha BETWEEN ? AND ? AND tipo_encuesta = ?"+
                    " ORDER BY fecha desc";
            pst = con.prepareStatement(consulta);
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);
            pst.setString(3, super.filtroTable);
           
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
