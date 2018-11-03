package com.grupodot.telefonica.conexion;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

  
public class ConexionDataSource implements DataSource  {
	
    private Map<String, String> mapProps = Utilitarios.getMapProperties("Schema");
    private BasicDataSource basicDataSource = new BasicDataSource();

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return basicDataSource.getAbandonedLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		basicDataSource.setAbandonedLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		basicDataSource.setLoginTimeout(seconds);
		
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return basicDataSource.getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return basicDataSource.getParentLogger();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return basicDataSource.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return basicDataSource.isWrapperFor(iface);
	}

	@Override
	public Connection getConnection() throws SQLException {
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUrl("jdbc:mysql://173.194.107.21/formularios_telefonica");
		basicDataSource.setUsername("root");
		basicDataSource.setPassword("root");
		return basicDataSource.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUrl("jdbc:mysql://173.194.107.21/formularios_telefonica");
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);
		return basicDataSource.getConnection();
	}
	
	public Connection getConnection(String dbName) throws SQLException {
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUrl("jdbc:mysql://"+mapProps.get("ip")+":3306/"+dbName);
		basicDataSource.setUsername(mapProps.get("username"));
		basicDataSource.setPassword(Utilitarios.desencript(mapProps.get("password")));
		return basicDataSource.getConnection();
	}
	
	public Connection getConnection(String instance, String database, String username, String password) throws SQLException {
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUrl("jdbc:mysql://"+instance+"/"+database);
		basicDataSource.setUrl("jdbc:mysql://"+instance+":3306/"+database+"?useSSL=false");
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);
		return basicDataSource.getConnection();
	}
	
	public void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeDataPool() {
		
		try {
			basicDataSource.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
