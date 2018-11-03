package com.grupodot.telefonica.controllers;

import java.io.File;
import java.sql.ResultSet;

public class TarjetaPreferencialController extends GenericController {
	
	public TarjetaPreferencialController(){
		super();
	}
	
	public TarjetaPreferencialController(String dbName, String tableName, String headers){
		super(dbName, tableName, headers);
	}
	
	@Override
	public File generarExcelPOI() {
		try {
			super.headers = super.getHeaders();
			con = dataSource.getConnection(this.dbName);
			String consulta = "SELECT * FROM prepagoCard"
					+ " ORDER BY fecha desc";	
	        pst = con.prepareStatement(consulta);
	        ResultSet rs = pst.executeQuery();
	        if(rs.next()) {
	        	File file = super.generarExcel(rs);
	        	return file;
	        }
	        return null;
		} catch (Exception e1) {
			System.out.println("ERROR: al realizar consulta a la BDs.");
			dataSource.closeConnection(con);
			e1.printStackTrace();
			return null;
		}
	}
	
	@Override
	public File generarExcelPOI(String fechaInicio, String fechaFin) {
    	try {
    		super.headers = super.getHeaders();
    		con = dataSource.getConnection(this.dbName);
    		String consulta = "SELECT * FROM prepagoCard"
					+ " WHERE fecha BETWEEN ? AND ?"
				+ " ORDER BY fecha desc";     	
            pst = con.prepareStatement(consulta);
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);
           
	        ResultSet rs = pst.executeQuery();
	        if(rs.next()) {
	        	File file = super.generarExcel(rs);
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
