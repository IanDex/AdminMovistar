package com.grupodot.telefonica.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Rol implements Serializable{

	protected String id;
	protected String nombre;
	protected String descripcion;
	protected String descripcion2;
	
	public Rol() {
		
	}
	
	public Rol(String id, String nombre, String descripcion, String descripcion2) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.descripcion2 = descripcion2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion2() {
		return descripcion2;
	}

	public void setDescripcion2(String descripcion2) {
		this.descripcion2 = descripcion2;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", descripcion2="
				+ descripcion2 + "]\n";
	}
}
