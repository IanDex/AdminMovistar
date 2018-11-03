package com.grupodot.telefonica.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
 
public class Conexion {
	 
    private static Connection cn = null;
    private static Map<String, String> mapPropsProduccion = PropertiesController.getMapProperties("Produccion");
    private static Map<String, String> mapPropsPruebas = PropertiesController.getMapProperties("Pruebas");
    
    //conexion generica para la instancia prueba-telefonicacorp de ip 173.194.229.234 con demas datos quemados
    public static Connection getConexion() throws SQLException {
    	try {
	    	if(System.getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
	    		//a la de produccion no poner password
	    		//a la de pruebas poner passwrod
	    		cn = DriverManager.getConnection("jdbc:google:mysql://modified-wonder-87620:prueba-telefonicacorp/formularios_telefonica?user=root");
	    	}
	    	
	    	else {
				Class.forName("com.mysql.jdbc.Driver");
				//cn = DriverManager.getConnection("jdbc:mysql://173.194.229.234/formularios_telefonica?user=root&password=root");
				cn = DriverManager.getConnection("jdbc:mysql://173.194.229.234/formularios_telefonica?user=root&password=");
	    	}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        return cn;
    }
    
    //conexion para distintras bases de datos en una instancia configurada en el properties de el proyecto
    public static Connection getConexion(String dbName) throws SQLException {
    	try {
	    	if(System.getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
	    		//a la de produccion no poner password
	    		//a la de pruebas poner passwrod
	    		cn = DriverManager.getConnection("jdbc:google:mysql://modified-wonder-87620:"+mapPropsProduccion.get("instancia")+"/"+dbName+"?user="+mapPropsProduccion.get("username"));
	    	}
	    	else {
	    		
				Class.forName("com.mysql.cj.jdbc.Driver");
				cn = DriverManager.getConnection("jdbc:mysql://"+mapPropsPruebas.get("ip")+"/"+dbName+"?user="+mapPropsPruebas.get("username")+"&password="+PropertiesController.desencript(mapPropsPruebas.get("password")));
	    	}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        return cn;
    }
    
    //conexion para una instancia en especifico
	public static Connection getConexion(String instance, String database, String user, String passw) {
		try {
	    	if(System.getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
	    		//a la de produccion no poner password
	    		//a la de pruebas poner passwrod
	    		System.out.println("jdbc:google:mysql://modified-wonder-87620:us-central1:"+instance.split(",")[1]+"/"+database+"?user="+user+"&password='"+passw+"'&useSSL=false");
	    		cn = DriverManager.getConnection("jdbc:google:mysql://modified-wonder-87620:us-central1:"+instance.split(",")[1]+"/"+database+"?user="+user+"&password="+passw+"&useSSL=false");
	    	}
	    	
	    	else {
				Class.forName("com.mysql.jdbc.Driver");
				cn = DriverManager.getConnection("jdbc:mysql://"+instance.split(",")[0]+"/"+database+"?user="+user+"&password="+passw+"&useSSL=false");
	    	}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return cn;
	}
}
