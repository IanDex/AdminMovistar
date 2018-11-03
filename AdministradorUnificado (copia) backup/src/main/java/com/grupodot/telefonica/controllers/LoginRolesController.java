package com.grupodot.telefonica.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.grupodot.telefonica.conexion.ConexionDataSource;
import com.grupodot.telefonica.models.Rol;
import com.grupodot.telefonica.models.Usuario;

public class LoginRolesController {
	
	protected ConexionDataSource dataSource = new ConexionDataSource();
	protected Connection con = null;
	protected PreparedStatement pst = null;
	protected String headers = "";
	protected String tableName = "";
	protected String dbName = "";
	
	public LoginRolesController(String dbName, String tableName, String headers){
		try {
			this.dbName = dbName;
			this.tableName = tableName;
			this.con = dataSource.getConnection(dbName);
			this.headers = headers;
			
		} catch (SQLException e) {
			System.out.println("SQLException");
			e.toString();
		} catch (Exception e) {
			System.out.println("Exception");
			e.toString();
		}
	}
	
	public Usuario login(String username, String password) {
		Usuario usuario = new Usuario();
        try {
        	con = dataSource.getConnection(this.dbName);
            //definimos la sentencia
            String consulta = "SELECT"+
            		" u.id AS id,"+ 
            		" u.username AS user,"+
            		" u.`password` AS passw,"+ 
            		" tpu.nombre AS tipousuario,"+
            		" u.nombreCompleto AS nombre"+
            		" FROM"+
            		" usuario AS u INNER JOIN tipo_de_usuario AS tpu"+
            		" WHERE"+ 
            		" u.fkTipoUsuario = tpu.id"+
            		" AND u.username = ?"+
            		" AND u.`password` = ?";
            //preparamos para ejecucion
            pst = con.prepareStatement(consulta);
            //pasamos las variables
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
            	usuario.setId(rs.getInt("id")+"");
            	usuario.setUsername(rs.getString("user"));
            	usuario.setPassword(rs.getString("passw"));
            	usuario.setTipousuario(rs.getString("tipousuario"));
            	usuario.setNombre(rs.getString("nombre"));
            	if(rs.getString("tipousuario").equalsIgnoreCase("superUser")) {
            		usuario.setRoles(this.getAllRoles());
            	} else {
            		usuario.setRoles(this.getRoles(rs.getInt("id")));
            	}
            	rs.close();
            	pst.close();
	        }else {
	        	System.out.println("No se encontraron resultados de usuario.");
	        	usuario = null;
	        }
            dataSource.closeConnection(con);
            con.close();
            return usuario;
        } catch (SQLException e) {
        	e.printStackTrace();
        } catch (NumberFormatException e) {
        	e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	public ArrayList<Rol> getAllRoles() {
		ArrayList<Rol> lista = new ArrayList<Rol>();
		Rol rol = null;
        try {
        	if(con.isClosed()) {
        		con = dataSource.getConnection(this.dbName);
        	}
            //definimos la sentencia
            String consulta = "SELECT * FROM rol";
            //preparamos para ejecucion
            pst = con.prepareStatement(consulta);
            //pasamos las variables
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
            	rol = new Rol();
            	rol.setId(rs.getInt("id")+"");
            	rol.setNombre(rs.getString("nombreTabla"));
            	rol.setDescripcion(rs.getString("descripcion"));
            	rol.setDescripcion2("");
            	lista.add(rol);
	        }
            rs.close();
            dataSource.closeConnection(con);
            con.close();
        } catch (SQLException e) {
        	e.printStackTrace();
        } catch (NumberFormatException e) {
        	e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
	}
	
	public ArrayList<Rol> getRoles(int codUsuario) {
		ArrayList<Rol> lista = new ArrayList<Rol>();
		Rol rol = null;
        try {
        	if(con.isClosed()) {
        		con = dataSource.getConnection(this.dbName);
        	}
            //definimos la sentencia
            String consulta = 
            		"SELECT " + 
            				"rol.id AS idRol, " +
            				"rol.nombreTabla AS nombreTabla, " +
            				"rol.descripcion AS rolDesc, " +
            				"usuario_rol.descripcion AS asigRol" + 
            		" FROM " + 
            			"usuario_rol, rol, usuario" + 
            		" WHERE " + 
            			"rol.id = usuario_rol.idRol" + 
            		" AND " + 
            			"usuario_rol.idUsuario = usuario.id" + 
            		" AND " + 
            			"usuario.id = ?";
            //preparamos para ejecucion
            pst = con.prepareStatement(consulta);
            //pasamos las variables
            pst.setInt(1, codUsuario);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
            	rol = new Rol();
            	rol.setId(rs.getInt("idRol")+"");
            	rol.setNombre(rs.getString("nombreTabla"));
            	rol.setDescripcion(rs.getString("rolDesc"));
            	rol.setDescripcion2(rs.getString("asigRol"));
            	lista.add(rol);
	        }
            rs.close();
            dataSource.closeConnection(con);
            con.close();
        }catch (NullPointerException e) {
        	try {
				con = dataSource.getConnection(this.dbName);
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        }catch (SQLException e) {
        	System.out.println("ERROR SQLException");
        	e.printStackTrace();
        } catch (NumberFormatException e) {
        	e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
	}
	
	public String getRolesArray(ArrayList<Rol> roles) {
		String result = "[";
		for(Rol iterator: roles) {
			result += "[" 
						+ "\"" + iterator.getId() + "\","
						+ "\"" + iterator.getNombre() + "\","
						+ "\"" + iterator.getDescripcion() + "\","
						+ "\"" + iterator.getDescripcion2() + "\""
					+ "],";
		}
		
		if(roles.size() <= 0) {
			result = "[]";
		} else {
			result = result.substring(0, result.length()-1);
			result += "]";
		}
		return result;
	}
	
	public boolean DeleteRol(int codUsuario, int codRol) {
		boolean sucess = false;
        int updateQuery = 0;
        try {
        	con = dataSource.getConnection(this.dbName);
            String consulta = "DELETE FROM usuario_rol WHERE idUsuario = ? AND idRol = ?";
            
            pst = con.prepareStatement(consulta);
            pst.setInt(1, codUsuario);
            pst.setInt(2, codRol);
            
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
	
	public boolean DeleteAllRol(int codUsuario) {
		boolean sucess = false;
        int updateQuery = 0;
        try {
        	con = dataSource.getConnection(this.dbName);
            String consulta = "DELETE FROM usuario_rol WHERE idUsuario = ?";
            
            pst = con.prepareStatement(consulta);
            pst.setInt(1, codUsuario);
            
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
	
	public boolean validaUsername(String username) {
		boolean sucess = false;
        try {
        	con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT * FROM usuario where username = ?";
            
            pst = con.prepareStatement(consulta);
            pst.setString(1, username);
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
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
	
	public boolean validaRolPorUsuario(int codUsuario, int codRol) {
		boolean sucess = false;
        try {
        	con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT * FROM usuario_rol WHERE idUsuario = ? AND idRol = ?";
            
            pst = con.prepareStatement(consulta);
            pst.setInt(1, codUsuario);
            pst.setInt(2, codRol);
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
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
	
	public boolean validaRol(String nombre) {
		boolean sucess = false;
        try {
        	con = dataSource.getConnection(this.dbName);
            String consulta = "SELECT * FROM rol WHERE nombreTabla = ?";
            
            pst = con.prepareStatement(consulta);
            pst.setString(1, nombre);
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
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

}
