package it.unina.prisonmanager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection
{
	private static DBConnection instance;
	
	private static final String URL = "jdbc:postgresql://localhost:5432/prison_manager";
	private static final String USER = "blues";
	private static final String PASSWORD = "RockMan2000";
	
	private Connection connection;
	
	private DBConnection() throws SQLException {
		this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
	}
	
	public static synchronized DBConnection getInstance() throws SQLException {
		if (instance == null || instance.connection.isClosed()) {
			instance = new DBConnection();
		} return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
}
