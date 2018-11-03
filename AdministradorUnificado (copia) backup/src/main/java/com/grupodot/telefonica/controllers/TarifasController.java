package com.grupodot.telefonica.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.grupodot.telefonica.models.Tarifas;

public class TarifasController extends GenericController{
	
	
	public TarifasController(){
		super();
	}
	
	public TarifasController(String dbName, String tableName, String headers){
		super(dbName, tableName, headers);
	}
	
	@Override
	public String exportar() {
		System.out.println("listar tarifas");
        return "[]";
	}
	
	@Override
	public String exportar(String fechaInicio, String fechaFin) {
		Tarifas instTar = new Tarifas();
		ArrayList<Tarifas> arrTarifas = new ArrayList<Tarifas>();
        String leadsTotales = "SELECT id_campana AS Campana, count(id) AS TotalLeads FROM tarifas.pos_pos_formulario_tarifa WHERE fecha_creacion between ? and ? GROUP BY id_campana;";
        String leadsCorrectos = "SELECT id_campana AS Campana, count(id) AS LeadsCorrectos FROM tarifas.pos_pos_formulario_tarifa WHERE RetornoWS = \"INGRESO CORRECTO\" AND fecha_creacion between ? and ? GROUP BY id_campana;";
        String leadsNoCorrectos = "SELECT id_campana AS Campana, count(id) AS LeadsNoCorrectos FROM tarifas.pos_pos_formulario_tarifa WHERE RetornoWS != \"INGRESO CORRECTO\" AND fecha_creacion between ? and ? GROUP BY id_campana;";
        String JsonStr = "["+headers+",";
        
    	try {
            con = dataSource.getConnection(this.dbName);
            pst = con.prepareStatement(leadsTotales);
            pst.setString(1, fechaInicio);
	        pst.setString(2, fechaFin);
	        
            ResultSet rs = pst.executeQuery();
            
	        while (rs.next()) {
	        	instTar = new Tarifas(rs.getString("Campana"), rs.getInt("TotalLeads"), 0, 0);
	        	arrTarifas.add(instTar);       	
	        }
	        
	        pst.close();
	        rs.close();
	        			
	        pst = con.prepareStatement(leadsCorrectos);
            pst.setString(1, fechaInicio);
	        pst.setString(2, fechaFin);
	        
            rs = pst.executeQuery();
            
	        while (rs.next()) {
	        	addToArray(arrTarifas, rs.getInt("LeadsCorrectos"), rs.getString("Campana"), "correctos");
	        }
	        
	        pst.close();
	        rs.close();
	        
	        pst = con.prepareStatement(leadsNoCorrectos);
            pst.setString(1, fechaInicio);
	        pst.setString(2, fechaFin);
	        
            rs = pst.executeQuery();
            
	        while (rs.next()) {
	        	addToArray(arrTarifas, rs.getInt("LeadsNoCorrectos"), rs.getString("Campana"), "nocorrectos");      	
	        }
	        	        
	        pst.close();
	        rs.close();
	        con.close();
	    } catch (SQLException f) {
	        f.printStackTrace();
	    }
        return JsonStr+generateJsonFromArray(arrTarifas)+"]";
	}
	
	@Override//FIXME Completar Función
	public String listar(String limit, String interval, String palabra) {
		String JsonStr = "[{"
				 + "\"success\":\""+false+"\"," 
				 + "\"numRows\":\"0\"," 
			   	 + "\"headers\":"+super.headers+"," 		   	
				 + "\"response\":[]"
				 + "}]";
		return JsonStr;
	}
	
	@Override
	public String listar(String limit, String interval, String palabra, String fechaInicio, String fechaFin) {
		Tarifas instTar = new Tarifas();
		ArrayList<Tarifas> arrTarifas = new ArrayList<Tarifas>();
        String leadsTotales = "SELECT id_campana AS Campana, count(id) AS TotalLeads FROM tarifas.pos_pos_formulario_tarifa WHERE fecha_creacion between ? and ? GROUP BY id_campana LIMIT ?, ?";
        String leadsCorrectos = "SELECT id_campana AS Campana, count(id) AS LeadsCorrectos FROM tarifas.pos_pos_formulario_tarifa WHERE RetornoWS = \"INGRESO CORRECTO\" AND fecha_creacion between ? and ? GROUP BY id_campana LIMIT ?, ?";
        String leadsNoCorrectos = "SELECT id_campana AS Campana, count(id) AS LeadsNoCorrectos FROM tarifas.pos_pos_formulario_tarifa WHERE RetornoWS != \"INGRESO CORRECTO\" AND fecha_creacion between ? and ? GROUP BY id_campana LIMIT ?, ?";
        System.out.println("Listar Tarifas");
    	try {
            con = dataSource.getConnection(this.dbName);
            pst = con.prepareStatement(leadsTotales);
            pst.setString(1, fechaInicio);
	        pst.setString(2, fechaFin);
	        pst.setInt(3, Integer.parseInt(interval));
	        pst.setInt(4, Integer.parseInt(limit));
	        
            ResultSet rs = pst.executeQuery();
            
	        while (rs.next()) {
	        	instTar = new Tarifas(rs.getString("Campana"), rs.getInt("TotalLeads"), 0, 0);
	        	arrTarifas.add(instTar);       	
	        }
	        
	        pst.close();
	        rs.close();
	        			
	        pst = con.prepareStatement(leadsCorrectos);
            pst.setString(1, fechaInicio);
	        pst.setString(2, fechaFin);
	        pst.setInt(3, Integer.parseInt(interval));
	        pst.setInt(4, Integer.parseInt(limit));
	        
            rs = pst.executeQuery();
            
	        while (rs.next()) {
	        	addToArray(arrTarifas, rs.getInt("LeadsCorrectos"), rs.getString("Campana"), "correctos");
	        }
	        
	        pst.close();
	        rs.close();
	        
	        pst = con.prepareStatement(leadsNoCorrectos);
            pst.setString(1, fechaInicio);
	        pst.setString(2, fechaFin);
	        pst.setInt(3, Integer.parseInt(interval));
	        pst.setInt(4, Integer.parseInt(limit));
	        
            rs = pst.executeQuery();
            
	        while (rs.next()) {
	        	addToArray(arrTarifas, rs.getInt("LeadsNoCorrectos"), rs.getString("Campana"), "nocorrectos");      	
	        }
	        	        
	        pst.close();
	        rs.close();
	        con.close();
	        
	    } catch (SQLException f) {
	        f.printStackTrace();
	    }
    	
        return generateJsonFromArray(arrTarifas, fechaInicio, fechaFin);
	}
	
	private String generateJsonFromArray(ArrayList<Tarifas> tarifas) {
		String JsonStr = "";
		for(int i=0;i<tarifas.size();i++) {
			JsonStr += "[" 
        			+ "\"" + tarifas.get(i).getCampania()
        			+ "\" ," + "\"" + tarifas.get(i).getLeadsTotales()
                    + "\" ," + "\"" + tarifas.get(i).getLeadsCorrectos()
                    + "\" ," + "\"" + tarifas.get(i).getLeadsNoCorrectos()
                    + "\""
                    + "],";
		}

		JsonStr = JsonStr.substring(0, JsonStr.length()-1);
		
		return JsonStr;
	}
	
	private String generateJsonFromArray(ArrayList<Tarifas> tarifas, String fechaInicio, String fechaFin) {
		String JsonStr = "";
		boolean sucess = false;
		
		if(tarifas.size() > 0) {
			sucess = true;
        }

		JsonStr = "[{"
		 + "\"success\":\""+sucess+"\"," 
		 + "\"numRows\":\""+count(fechaInicio, fechaFin)+"\"," 
	   	 + "\"headers\":"+super.headers+"," 		   	
		 + "\"response\":"+tarifas.toString()
		 + "}]"; 
		
		return JsonStr;
	}
	
	private void addToArray(ArrayList<Tarifas> tarifas, Object valor, String campaña, String atributo) {
		for(int i=0; i < tarifas.size(); i++) {
			if(tarifas.get(i).getCampania().equalsIgnoreCase(campaña)) {
				if(atributo.equalsIgnoreCase("correctos")) {
					tarifas.get(i).setLeadsCorrectos((int) valor);
				} else if(atributo.equalsIgnoreCase("nocorrectos")) {
					tarifas.get(i).setLeadsNoCorrectos((int) valor);
				} else if(atributo.equalsIgnoreCase("totales")) {
					tarifas.get(i).setLeadsTotales((int) valor);
				} else {
					System.out.println("atributo inexistente");
					break;
				}
			}
		}
	}
	
	public String count(String fechaInicio, String fechaFin) {
		String str = "0";
    	try {
            con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT count(Distinct(id_campana)) As cantidad FROM "+tableName+" WHERE fecha_creacion between ? and ?";            
            pst = con.prepareStatement(consulta);
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	        	str = rs.getInt("cantidad")+"";
	        }
	        pst.close();
	        con.close();
	    } catch (SQLException f) {
	        f.printStackTrace();
	        return "0";
	    }
        return str;
	}
}
