package it.unina.prisonmanager.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConnection
{
	private static DBConnection instance;
	
	private static final String URL;
	private static final String USER;
	private static final String PASSWORD;
	
	static {
		Properties properties = new Properties();
		try (InputStream credentials = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
			if (credentials == null) {
				throw new RuntimeException("db.properties file not found!");
			} properties.load(credentials);
			URL = properties.getProperty("db.url");
			USER = properties.getProperty("db.user");
			PASSWORD = properties.getProperty("db.password");
		} catch (IOException e) {
			throw new RuntimeException(
				"An unknown problem occurred while"
				+ " trying to read db.properties file.", e
			);
		}
	}
	
	private Connection connection;
	
	private DBConnection() {}
	
	public static synchronized DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		} return instance;
	}
	
	public Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} return connection;
	}
}
