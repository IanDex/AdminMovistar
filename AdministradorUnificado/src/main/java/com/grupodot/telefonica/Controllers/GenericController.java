package com.grupodot.telefonica.Controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.grupodot.telefonica.Conexion.ConexionDataSource;


public class GenericController {
	
	protected ConexionDataSource dataSource = new ConexionDataSource();
	protected Connection con = null;
	protected PreparedStatement pst = null;
	protected String headers = "";
	protected String tableName = "";
	protected String dbName = "";
	protected String filtroTable = "";
	
	public GenericController(){
	}
	
	public GenericController(String dbName, String tableName, String headers){
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
	
	public GenericController(String dbName, String tableName, String headers, String filtroTable){
		try {
			this.dbName = dbName;
			this.tableName = tableName;
			this.con = dataSource.getConnection(dbName);
			if(headers.equalsIgnoreCase("")) {
				this.headers = getHeaders();
			} else {
				this.headers = headers;
			}
			this.filtroTable = filtroTable;
		} catch (SQLException e) {
			System.out.println("SQLException");
			e.toString();
		} catch (Exception e) {
			System.out.println("Exception");
			e.toString();
		}
	}
	
	public GenericController(String instance, String dbName, String tableName, String username, String password, String headers){
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

	public boolean insert(Map<Integer,Object> params, int numRows, boolean date) {
		boolean sucess = false;
        int updateQuery = 0;
        try {
        	con = dataSource.getConnection(this.dbName);
            String consulta = "INSERT INTO " + this.tableName + " VALUES " + this.GenerateIndex(numRows, date);
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
	public boolean update(Map<String,Object> params, int id) {
		boolean sucess = false;
        int updateQuery = 0;
        try {
        	con = dataSource.getConnection(this.dbName);
            String consulta = "UPDATE " + this.tableName + " SET "+GenerateSet(params)+" WHERE id = ?";
            
            pst = con.prepareStatement(consulta);
            pst.setInt(1, id);
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
	public boolean delete(Map<String,Object> params) {
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
	
	//FIXME Validar si eliminar (Método anterior para exportar excel.)
	public String exportar() {
		long startTime = System.nanoTime();
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
    	System.out.println("############");
    	System.out.println(JsonStr);
    	System.out.println("#####JsonStr#######");
    	System.out.println("TIEMPO DE EJECUCION "+(System.nanoTime()-startTime));
        return JsonStr;
	}
	
	//FIXME Validar si eliminar (Método anterior para exportar excel.)
	public String exportar(String fechaInicio, String fechaFin) {
		long startTime = System.nanoTime();
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
    	System.out.println("############");
    	System.out.println(JsonStr);
    	System.out.println("#####JsonStrFechas#######");
    	System.out.println("TIEMPO DE EJECUCION "+(System.nanoTime()-startTime));
    	return JsonStr;
	}
	

	
	public String descripcionRol(String nombreTabla) {
		String str = "";
    	try {
    		con = dataSource.getConnection(this.dbName);
    		String consulta = "SELECT descripcion FROM rol WHERE nombreTabla = ?";            
            pst = con.prepareStatement(consulta);
            	pst.setString(1, nombreTabla);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	        	str = rs.getString("descripcion")+"";
	        }
	        dataSource.closeConnection(con);
	    } catch (SQLException f) {
	        f.printStackTrace();
	    }
        return str;
	}
	
	public String listar(String limite, String intervalo, String palabra) {
    	String JsonStr = "", response = "[";
    	boolean sucess = false;
    	try {
    		System.out.println("Intervalo " + intervalo + " - Limite " + limite);
    		con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT * FROM " + this.tableName + "" + 
                    " ORDER BY fecha desc"+ 
                    " limit ?, ?";
            	pst = con.prepareStatement(consulta);
                pst.setInt(1, Integer.parseInt(intervalo));
                pst.setInt(2, Integer.parseInt(limite));
            ResultSet rs = pst.executeQuery();
	        response = IterateResultSet(rs, this.headers);
	        System.out.println(response + " Response");
	        if(response.length() > 10) {
	        	sucess = true;
	        	response = response.substring(0, response.length()-1);
	        	response += "]";
	        }else {
	        	response="[]";
	        }
			JsonStr = "[{"
			 + "\"success\":\""+sucess+"\"," 
			 + "\"numRows\":\""+count()+"\"," 
		   	 + "\"headers\":"+this.headers+"," 
		   	 + "\"response\":["+response
			 + "}]"; 
	        
	        dataSource.closeConnection(con);
	    } catch (SQLException f) {
	        f.printStackTrace();
	    }
        return JsonStr;
	}
	
	public String listar(String limite, String intervalo, String palabra, String fechaInicio, String fechaFin) {
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
	
	public String count(String fechaInicio, String fechaFin) {
		String str = "";
    	try {
    		con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT count(*) As cantidad from " + this.tableName + " WHERE fecha BETWEEN ? AND ?";  
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
	
	public boolean compararPass(String idU, String pass) {
		boolean sucess = false;
		int id = Integer.parseInt(idU);
    	try {
    		con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT password FROM usuario WHERE id = ? AND password = ?";	    	
            pst = con.prepareStatement(consulta);
            pst.setInt(1, id);
            pst.setString(2, pass);
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
	//3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79
	public boolean pass(String idP,String passnew) {
		int id = Integer.parseInt(idP);
		boolean sucess = false;
        int updateQuery = 0;
        try {
        	con = dataSource.getConnection(this.dbName);
			String consulta = "UPDATE usuario SET password = ? WHERE id = ?";			
            pst = con.prepareStatement(consulta);
            pst.setString(1, passnew);
            pst.setInt(2, id);
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
	
	public boolean validaNumero(String numero, String campo) {
    	boolean sucess = false;
    	try {
    		con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT * FROM " + this.tableName + " WHERE " + campo + " = ?";	    	
            pst = con.prepareStatement(consulta);
            pst.setString(1, numero);
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
	
	protected String GenerateIndex(int numRows, boolean date){
		String response = "(NULL, ";
		if(date) {
			response += "now(), ";
		}
		for(int i=0; i<numRows; i++) {
			response += "?, ";
		}
		response = response.substring(0, response.length()-2);
		response += ")";
		return response;
	}
	
	protected String GenerateSet(Map<String,Object> params){
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
	
	public File generarExcelPOI() {
		long startTime = System.nanoTime();
		try {
			con = dataSource.getConnection(this.dbName);
	        String consulta = "SELECT * FROM " + this.tableName;	
	        pst = con.prepareStatement(consulta);
	        ResultSet rs = pst.executeQuery();
	        if(rs.next()) {
	        	File file = this.generarExcel(rs);
	        	System.out.println("TIEMPO DE EJECUCION "+(System.nanoTime()-startTime));
	        	return file;
	        }
	        return null;
		} catch (Exception e1) {
			System.out.println("ERROR: al realizar consula a la BDs.");
			e1.printStackTrace();
			return null;
		}
	}
	
	public File generarExcelPOI(String fechaInicio, String fechaFin) {
		long startTime = System.nanoTime();
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
	        if(rs.next()) {
	        	File file = this.generarExcel(rs);
	        	System.out.println("TIEMPO DE EJECUCION "+(System.nanoTime()-startTime));
	        	return file;
	        }
	        return null;
		} catch (Exception e1) {
			System.out.println("ERROR: al realizar consula a la BDs.");
			e1.printStackTrace();
			return null;
		}
	}
	
	protected File generarExcel(ResultSet rs){
		//Configuramos el limite de registros a mantener en memoria
		int limite= 300;
		//Aplicamos el limite al constructor
		Workbook wb = new SXSSFWorkbook(limite);
		Sheet hoja = wb.createSheet();
		CellStyle styleBold = wb.createCellStyle();
		
		try {
	        String[] headersSplit = cleanHeadersIterator(headers).split(",");
	        java.util.List<Object[]> listaObj = new ArrayList<>();;
	        Object[] obj = null;
	        rs.beforeFirst();
			while(rs.next()) {
				obj = new Object[headersSplit.length];
				int i=0;
				for(String auxIt: headersSplit) {
					obj[i] = rs.getObject(auxIt);
					i++;
				}
				listaObj.add(obj);
			}
			System.out.println("TAMAÑO DE LISTA OBJ "+listaObj.size());
			Row row = hoja.createRow(0);
			for (int i = 0; i < headersSplit.length; i++) {
	            Cell tituloCell = row.createCell(i);
	            tituloCell.setCellStyle(this.generarEstilosTitulo(wb));
	            tituloCell.setCellValue(headersSplit[i]);
	            //Define ancho de columna
	            hoja.autoSizeColumn(i);
	            //Agregar Filtros
//	            hoja.setAutoFilter(new CellRangeAddress(0, headersSplit.length - 1, 0, headersSplit.length - 1));
	        }
			int rowNum = 1;
			for (Object[] l : listaObj) {
	            row = hoja.createRow(rowNum++);
	            short cellNum = 0;
	            for (Object e : l) {
	                Cell cell = row.createCell(cellNum++);
//	                cell.setCellStyle(this.generarEstilosCeldas(wb));
	                if (row.equals(1)) {
	                    cell.setCellStyle(styleBold);
	                }
	                if (e instanceof Date) {
	                    String fechaString = "";
	                    if (((Date) e).getHours() == 00 && ((Date) e).getMinutes() == 00 && ((Date) e).getSeconds() == 00) {
	                        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	                        fechaString = formato.format((Date) e);
	                    } else {
	                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	                        fechaString = format.format((Date) e);
	                    }
	                    cell.setCellValue("" + fechaString);
	                } else if (e instanceof Boolean) {
	                    cell.setCellValue((Boolean) e);
	                } else if (e instanceof Double) {
	                    cell.setCellValue((Double) e);
	                } else if (e instanceof Integer) {
	                    cell.setCellValue((Integer) e);
	                } else {
	                    if (e == null) {
	                        e = "";
	                    }
	                    cell.setCellValue("" + e);
	                }
	            }
			}
		} catch (Exception e1) {
			System.out.println("ERROR: al intentar construir archivo xlsx");
			e1.printStackTrace();
		}

		File file = new File("./report.xlsx");
		try {
			FileOutputStream out = new FileOutputStream(file);
			wb.write(out);
			out.close();
			dataSource.closeConnection(con);
			return file;
		} catch (Exception e) {
			System.out.println("ERROR al crear archivo.");
			dataSource.closeConnection(con);
			e.printStackTrace();
		}
		return null;
	}
	
	private CellStyle generarEstilosTitulo(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        Font fontHeader = workbook.createFont();
        fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontHeader.setFontHeightInPoints((short) 11);
        headerStyle.setFont(fontHeader);
        //Alinear títulos al Centro
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //Sombreado celda
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //Se Establecen los Bordes
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBottomBorderColor((short) 8);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setLeftBorderColor((short) 8);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setRightBorderColor((short) 8);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setTopBorderColor((short) 8);

        return headerStyle;
    }

	public String listarRol() {
    	String JsonStr = "", response = "[";
    	boolean sucess = false;
    	try {
    		con = dataSource.getConnection("formularios_telefonica");
            String consulta = "SELECT * FROM rol";
            	pst = con.prepareStatement(consulta);
            ResultSet rs = pst.executeQuery();
	        System.out.println(response + " Response");
	        if(response.length() > 10) {
	        	sucess = true;
	        	response = response.substring(0, response.length()-1);
	        	response += "]";
	        }else {
	        	response="[]";
	        }
			JsonStr = "[{"
			 + "\"success\":["+sucess
			 + "}]"; 
	        
	        dataSource.closeConnection(con);
	    } catch (SQLException f) {
	        f.printStackTrace();
	    }
        return JsonStr;
	}
}
