package com.grupodot.telefonica.Models;

public class Tarifas {
	protected String campania;
	protected int leadsTotales;
	protected int leadsCorrectos;
	protected int leadsNoCorrectos;
	
	
	public Tarifas() {
		this.campania = "";
		this.leadsTotales = 0;
		this.leadsCorrectos = 0;
		this.leadsNoCorrectos = 0;
	}

	public Tarifas(String campania, int leadsTotales, int leadsCorrectos, int leadsNoCorrectos) {
		this.campania = campania;
		this.leadsTotales = leadsTotales;
		this.leadsCorrectos = leadsCorrectos;
		this.leadsNoCorrectos = leadsNoCorrectos;
	}

	public String getCampania() {
		return campania;
	}

	public void setCampania(String campania) {
		this.campania = campania;
	}

	public int getLeadsTotales() {
		return leadsTotales;
	}

	public void setLeadsTotales(int leadsTotales) {
		this.leadsTotales = leadsTotales;
	}

	public int getLeadsCorrectos() {
		return leadsCorrectos;
	}

	public void setLeadsCorrectos(int leadsCorrectos) {
		this.leadsCorrectos = leadsCorrectos;
	}

	public int getLeadsNoCorrectos() {
		return leadsNoCorrectos;
	}

	public void setLeadsNoCorrectos(int leadsNoCorrectos) {
		this.leadsNoCorrectos = leadsNoCorrectos;
	}

//	@Override
//	public String toString() {
//		return "{\"campania\": \"" + campania + "\", \"leadsTotales\": \"" + leadsTotales + "\", \"leadsCorrectos\": \"" + leadsCorrectos
//				+ "\", \"leadsNoCorrectos\": \"" + leadsNoCorrectos + "\"}\n";
//	}
	
	@Override
	public String toString() {
		return "[\"" + campania + "\", \"" + leadsTotales + "\", \"" + leadsCorrectos
				+ "\", \"" + leadsNoCorrectos + "\"]";
	}
	
}
