package com.grupodot.telefonica.Controller;

public interface MySqlProcess {
	public String Select(String fields, String headers, String conditions, String values);
	public boolean Insert(String fields, String headers, String conditions, String values);
	public boolean Update(String fields, String headers, String conditions, String values);
	public boolean Delete(String field, String value);
	public String GetTableFields();
	public String Count(String conditions, String values);
	public boolean validaCampo(String campo, String valor);
}
