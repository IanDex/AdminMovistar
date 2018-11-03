package com.grupodot.telefonica.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.grupodot.telefonica.Conexion.ConexionDataSource;



public class Controller {
	
	protected ConexionDataSource dataSource = new ConexionDataSource();
	protected Connection con = null;
	protected PreparedStatement pst = null;
	protected String headers = "";
	protected String tableName = "";
	protected String dbName = "";
	
	public Controller(){
	}
	
	public Controller(String dbName, String tableName, String headers){
		try {
			this.dbName = dbName;
			this.tableName = tableName;
			this.con = dataSource.getConnection(dbName);
			if(headers.equalsIgnoreCase("")) {
				this.headers = getHeaders();
			} else {
				this.headers = headers;
			}
		} catch (SQLException e) {
			System.out.println("SQLException");
			e.toString();
		} catch (Exception e) {
			System.out.println("Exception");
			e.toString();
		}
	}
	
	public Controller(String instance, String dbName, String tableName, String username, String password, String headers){
		try {
			this.dbName = dbName;
			this.tableName = tableName;
			if(headers.equalsIgnoreCase("")) {
				this.headers = getHeaders();
			} else {
				this.headers = headers;
			}
			
			this.con = dataSource.getConnection(instance, dbName, username, password);
		} catch (SQLException e) {
			System.out.println("SQLException");
			e.toString();
		} catch (Exception e) {
			System.out.println("Exception");
			e.toString();
		}
	}

	public boolean Insert(Map<Integer,Object> params, int numRows) {
		boolean sucess = false;
        int updateQuery = 0;
        try {
        	con = dataSource.getConnection(this.dbName);
            String consulta = "INSERT INTO " + this.tableName + " VALUES " + this.GenerateInsertValues(numRows);
            pst = con.prepareStatement(consulta);
            for(Integer key : params.keySet()){
            	pst.setObject(key, params.get(key));
            }
            updateQuery = pst.executeUpdate();
            if (updateQuery != 0) {
                sucess = true;
            }
            dataSource.closeConnection(con);
        } catch (SQLException e) {
        	e.printStackTrace();
        } catch (NumberFormatException e) {
        	e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sucess;
	}
	
	//funcional - casi terminado
	public boolean update(Map<String,Object> params, String campo, String valor) {
		boolean sucess = false;
        int updateQuery = 0;
        try {
        	con = dataSource.getConnection(this.dbName);
            String consulta = "UPDATE " + this.tableName + " SET "+GenerateUpdateSet(params)+" WHERE " + campo +" = ?";
            
            pst = con.prepareStatement(consulta);
            pst.setObject(1, valor);
            updateQuery = pst.executeUpdate();
            if (updateQuery != 0) {
                sucess = true;
            }
            dataSource.closeConnection(con);
        } catch (SQLException e) {
        	e.printStackTrace();
        } catch (NumberFormatException e) {
        	e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sucess;
	}
	
	//falta terminar
	public boolean Delete(Map<String,Object> params) {
		boolean sucess = false;
        int updateQuery = 0;
        try {
        	con = dataSource.getConnection(this.dbName);
            String consulta = "DELETE FROM " + this.tableName + " WHERE " + GenerateConditional(params);

            pst = con.prepareStatement(consulta);

            updateQuery = pst.executeUpdate();
            if (updateQuery != 0) {
                sucess = true;
            }
            dataSource.closeConnection(con);
        } catch (SQLException e) {
        	e.printStackTrace();
        } catch (NumberFormatException e) {
        	e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sucess;
	}
	
	public String Export() {
		String JsonStr = "", response = "";
    	try {
    		con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT * FROM " + this.tableName;	
            pst = con.prepareStatement(consulta);
	        ResultSet rs = pst.executeQuery();
	        response = IterateResultSet(rs, this.headers);
	        if(response.length() > 0) {
	        	response = response.substring(0, response.length()-1);
	        	response += "]";
	        }else {
	        	JsonStr="[]";
	        }
	        JsonStr = "["+this.headers+","+response+"";
	        dataSource.closeConnection(con);
	    } catch (SQLException f) {
	        f.printStackTrace();
	    } catch (Exception f) {
	        f.printStackTrace();
	    }
        return JsonStr;
	}
	
	public String ExportByDate(String fechaInicio, String fechaFin) {
		String JsonStr = "["+this.headers+",", response;
    	try {
    		con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT * FROM " + this.tableName + " WHERE fecha BETWEEN ? AND ?";
            if(this.dbName.equalsIgnoreCase("cambio_de_plan")) {
            	consulta = "SELECT * FROM " + this.tableName + 
                		" WHERE fecha_registro BETWEEN ? AND ?"+
                        " LIMIT ?, ?"; 
            }
            pst = con.prepareStatement(consulta);
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);
	        ResultSet rs = pst.executeQuery();
	        response = IterateResultSet(rs, this.headers);
	        if(response.length() > 0) {
	        	response = response.substring(0, response.length()-1);
	        }else {
	        	response="[]";
	        }
	        JsonStr = "["+this.headers+","+response+"]";
	        dataSource.closeConnection(con);
	    } catch (SQLException f) {
	        f.printStackTrace();
	    } catch (Exception f) {
	        f.printStackTrace();
	    }
        return JsonStr;
	}
	
	public String Select(String limite, String intervalo, String palabra, String orderBy, String fechaInicio, String fechaFin) {
    	String JsonStr = "", response = "[";
    	boolean sucess = false;
    	try {
    		con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT * FROM " + this.tableName + "" + 
                    " ORDER BY fecha desc"+ 
                    " limit ?, ?";
            	pst = con.prepareStatement(consulta);
                pst.setInt(1, Integer.parseInt(intervalo));
                pst.setInt(2, Integer.parseInt(limite));
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
			 + "\"numRows\":\""+Count("", "")+"\"," 
		   	 + "\"headers\":"+this.headers+"," 
		   	 + "\"response\":["+response
			 + "}]"; 
	        
	        dataSource.closeConnection(con);
	    } catch (SQLException f) {
	        f.printStackTrace();
	    }
        return JsonStr;
	}
	
	public String SelectByDate(String limite, String intervalo, String palabra, String fechaInicio, String fechaFin) {
    	String JsonStr = "", response = "[";
    	boolean sucess = false;
    	try {
    		con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT * FROM " + this.tableName + 
            		" WHERE fecha BETWEEN ? AND ?"+
                    " ORDER BY fecha desc"+
                    " LIMIT ?, ?";
            pst = con.prepareStatement(consulta);
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);
            pst.setInt(3, Integer.parseInt(intervalo));
            pst.setInt(4, Integer.parseInt(limite));
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
			 + "\"numRows\":\""+Count(fechaInicio, fechaFin)+"\"," 
		   	 + "\"headers\":"+this.headers+"," 
			 + "\"response\":["+response
			 + "}]"; 
	        
	        dataSource.closeConnection(con);
	    } catch (SQLException f) {
	        f.printStackTrace();
	    }
        return JsonStr;
	}
	
	public String Count(String fechaInicio, String fechaFin) {
		String str = "", byDate="";
		if(fechaInicio.isEmpty() || fechaFin.isEmpty()) {
			byDate = " WHERE fecha BETWEEN ? AND ?";
		}
    	try {
    		con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT COUNT(*) As cantidad from " + this.tableName + "";  
            pst = con.prepareStatement(consulta);
            if(!byDate.equalsIgnoreCase("")) {
            	pst.setString(1, fechaInicio);
                pst.setString(2, fechaFin);
            }
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
	
	public boolean validaCampo(String valor, String campo) {
    	boolean sucess = false;
    	try {
    		con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT * FROM " + this.tableName + " WHERE " + campo + " = ?";	    	
            pst = con.prepareStatement(consulta);
            pst.setString(1, valor);
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	        	sucess = true;
	        }
	        dataSource.closeConnection(con);
	    } catch (SQLException f) {
	        f.printStackTrace();
	    }
        return sucess;
	}
	
	protected String getHeaders() {
		String strHeaders = "[";
		String consulta = "SELECT COLUMN_NAME AS col FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";	    	
		try {
			con = dataSource.getConnection(this.dbName);
			pst = con.prepareStatement(consulta);
			pst.setObject(1, this.dbName);
			pst.setObject(2, tableName);
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	        	strHeaders += "\"" + rs.getObject("col")+"\",";
	        }
	        if(!strHeaders.equalsIgnoreCase("")) {
		        strHeaders = strHeaders.substring(0, strHeaders.length()-1);
		        strHeaders += "]";
	        }
	        else {
	        	strHeaders = "[]";
	        }
	        dataSource.closeConnection(con);
		} catch (SQLException f) {
	        f.printStackTrace();
	    }
		return strHeaders;
	} 
	
	protected String IterateResultSet(ResultSet rs, String headers) throws SQLException {
		String response = "";
		String[] headersSplit = cleanHeadersIterator(headers).split(",");
		 while (rs.next()) {
			 response += "[";
			 for(String auxIt: headersSplit) {
				 response += "\"" + rs.getObject(auxIt) + "\",";
			 }
			 response = response.substring(0, response.length()-1);
			 response += "],";
	     }
		 return response;
	}
	
	protected String GenerateInsertValues(int numRows){
		String response = "(NULL, ";
		for(int i=0; i<numRows; i++) {
			response += "?, ";
		}
		response = response.substring(0, response.length()-2);
		response += ")";
		return response;
	}
	
	protected String GenerateUpdateSet(Map<String,Object> params){
		String response = "";
		for(String key : params.keySet()){
			response += key+"=\""+params.get(key)+"\",";
        }
		response = response.substring(0, response.length()-1);
		return response;
	}
	
	protected String GenerateConditional(Map<String,Object> params){
		String response = "";
		for(String key : params.keySet()){
			response += key+"=\""+params.get(key)+"\" AND";
        }
		response = response.substring(0, response.length()-4);
		return response;
	}
	
	protected String cleanHeadersIterator(String headers) {
		headers = headers.replace("\"", "");
		headers = headers.replace("[", "");
		headers = headers.replace("]", "");
		headers = headers.replace(" ", "");
		
		return headers;
	}
}