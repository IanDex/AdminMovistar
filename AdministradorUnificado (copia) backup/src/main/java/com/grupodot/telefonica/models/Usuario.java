package com.grupodot.telefonica.models;

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Usuario implements Serializable{
	
	protected String id;
	protected String username;
	protected String password;
	protected String tipousuario;
	protected String nombre;
	protected ArrayList<Rol> roles;
	
	public Usuario() {
		this.id = "0";
		this.username = "user";
		this.password = "12345";
		this.tipousuario = "administrador";
		this.nombre = "User User";
		this.roles = new ArrayList<Rol>();
		
	}

	public Usuario(String id, String username, String password, String tipousuario, String nombre, ArrayList<Rol> roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.tipousuario = tipousuario;
		this.nombre = nombre;
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTipousuario() {
		return tipousuario;
	}

	public void setTipousuario(String tipousuario) {
		this.tipousuario = tipousuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Rol> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<Rol> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", username=" + username + ", password=" + password + ", tipousuario="
				+ tipousuario + ", nombre=" + nombre + ", roles=" + roles.toString() + "]\n";
	}
}
