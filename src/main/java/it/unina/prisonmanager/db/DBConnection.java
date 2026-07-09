package it.unina.prisonmanager.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import it.unina.prisonmanager.exception.DataAccessException;

public final class DBConnection
{
	private static final DBConnection instance = new DBConnection();
	
	public static DBConnection getInstance() {
		return instance;
	}
	
	private static final String URL;
	private static final String USER;
	private static final String PASSWORD;
	
	static {
		Properties properties = new Properties();
		try (InputStream credentials =
				DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
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
	
	private final ThreadLocal<Connection> localConnection = new ThreadLocal<>();
	
	private DBConnection() {}
	
	//Controller starts transaction for specific user
	public Connection startTransaction(Integer userId) {
		Connection connection = localConnection.get();
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				connection.setAutoCommit(false);
				localConnection.set(connection);
			} if (userId != null) {
				try (
					PreparedStatement prepared = connection.prepareStatement(
						"SET LOCAL frontend.current_user_id = ?"
					)
				) {
					prepared.setInt(1, userId);
					prepared.execute();
				}
			} 
		} catch (SQLException e) {
			throw new DataAccessException(
				"Data access failure on transaction start.", e
			);
		} return connection;
	}
	
	//DAO gets connection activated by Controller transaction
	public Connection getConnection() {
		Connection connection = localConnection.get();
		if (connection == null) {
			throw new IllegalStateException(
				"Tried to receive a connection"
				+ " without starting a transaction first."
			);
		} return connection;
	}
	
	//true for commit, false for rollback
	public void endTransaction(boolean isCommit) {
		Connection connection = localConnection.get();
		if (connection == null) {
			return;
		} try {
			if (!connection.isClosed()) {
				if (isCommit) {
					connection.commit();
				} else {
					connection.rollback();
				} connection.close();
			}
		} catch(SQLException e) {
			String endOp = (isCommit) ? "commit" : "rollback ";
			throw new DataAccessException(
				"Failed to " + endOp + " transaction.", e
			);
		} finally {
			localConnection.remove();
		}
	}
}
