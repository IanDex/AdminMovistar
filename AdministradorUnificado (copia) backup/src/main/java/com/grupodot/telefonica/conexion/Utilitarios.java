package com.grupodot.telefonica.conexion;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Utilitarios {
	
	public static String getActualDate() {
		Date fecha = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
	    return sdf.format(fecha);
	}
	
	public static boolean isNumeric(String cadena) {
        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }
	
	public static String setHoursToDateStr(String fecha, String hour) {
		if(!fecha.isEmpty() && validarFecha(fecha)) {
			return fecha+" "+hour;
		} else if(!fecha.isEmpty() && validarFecha(fecha) && fecha.contains(":")) {
			return fecha;
		}
		else {
			return "";
		}
	}
	
	public static boolean validarFecha(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-mm-dd");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return false;

        }
        return true;
    }
	
	public static String FechaHora() {
		Date date = new Date();
		//Caso 1: obtener la hora y salida por pantalla con formato:
		DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
		System.out.println("Hora: "+hourFormat.format(date));
		//Caso 2: obtener la fecha y salida por pantalla con formato:
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		System.out.println("Fecha: "+dateFormat.format(date));
		//Caso 3: obtenerhora y fecha y salida por pantalla con formato:
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		return hourdateFormat.format(date)+"";
	}
	
	public static String getProperty(String propName) {
		Properties p = new Properties();
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		try {
			p.load(new FileReader(rootPath+"config.properties"));
			return p.getProperty(propName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String encript(String texto) {

		String secretKey = "GrupoDotS.@.S"; // llave para encriptar datos
		String base64EncryptedString = "";

		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] plainTextBytes = texto.getBytes("utf-8");
			byte[] buf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(buf);
			base64EncryptedString = new String(base64Bytes);

		} catch (Exception ex) {
			
		}
		return base64EncryptedString;
	}

	public static String desencript(String textoEncriptado) {

		String secretKey = "GrupoDotS.@.S"; // llave para desenciptar datos
		String base64EncryptedString = "";

		try {
			byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			SecretKey key = new SecretKeySpec(keyBytes, "DESede");

			Cipher decipher = Cipher.getInstance("DESede");
			decipher.init(Cipher.DECRYPT_MODE, key);

			byte[] plainText = decipher.doFinal(message);

			base64EncryptedString = new String(plainText, "UTF-8");

		} catch (Exception e) {
			
		}
		return base64EncryptedString;
	}
	
	public static Map<String, String> getMapProperties(String PruebasProduccio) {
		Map<String, String> mapProperties = new HashMap<String, String>();
		Properties p = new Properties();
//		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("com/grupodot/telefonica/config.properties");
			p.load(input);
			String [] ArrProps = splitProp(p.getProperty(PruebasProduccio), ";");
			for(int i=0;i<ArrProps.length;i++) {
				String[] propsSplt = splitProp(ArrProps[i], ":");
				mapProperties.put(propsSplt[0], propsSplt[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return mapProperties;
	}
	
	public static String[] splitProp(String propName, String delimiter) {
		return propName.split(delimiter);
	}
}
