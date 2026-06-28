package it.unina.prisonmanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import it.unina.prisonmanager.dao.UserDAO;
import it.unina.prisonmanager.db.DBConnection;
import it.unina.prisonmanager.exception.DataAccessException;
import it.unina.prisonmanager.exception.DataDependencyException;
import it.unina.prisonmanager.exception.DuplicateDataException;
import it.unina.prisonmanager.model.User;
import it.unina.prisonmanager.model.UserRole;

public class UserDAOImpl implements UserDAO
{
	private static UserDAOImpl instance;
	
	private UserDAOImpl() {}
	
	public static UserDAOImpl getInstance() {
		if (instance == null) {
			instance = new UserDAOImpl();
		} return instance;
	}
	
	private static void dispatchSQLException(SQLException e) {
		Objects.requireNonNull(e, "SQLException reference is NULL.");
		String state = e.getSQLState();
		if (state == null) {
			throw new DataAccessException(
				"Communication with data source has failed."
				+ " Check your connection and try again.", e
			);
		} switch(state) {
			case "23001":
			case "23503":
				throw new DataDependencyException(
					"This user is currently being utilized by the system."
					+ " Cannot be deleted.", e
				);
			case "23505":
				throw new DuplicateDataException(
					"Username already exists. Try another one.", e
				);
			default:
				throw new DataAccessException(e);
		}
	}
	
	@Override
	public boolean insert(User user) {
		Objects.requireNonNull(user, "User is NULL.");
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"INSERT INTO frontend_user(username, password_hash, role, is_active)"
					+ " VALUES (?, ?, ?::user_role, ?)",
					Statement.RETURN_GENERATED_KEYS
				)
			) {
				prepared.setString(1, user.getUsername());
				prepared.setString(2, user.getPasswordHash());
				prepared.setString(3, user.getRole().name().toLowerCase());
				prepared.setBoolean(4, user.isActive());
				if (prepared.executeUpdate() > 0) {
					try (ResultSet serial = prepared.getGeneratedKeys()) {
						if (serial.next()) {
							user.setId(serial.getInt(1));
						}
					} return true;
				}
			}
		} catch (SQLException e) {
			dispatchSQLException(e);
		} return false;
	}
	
	private static User getResult(ResultSet result)
	throws SQLException {
		if (result == null) {
			return null;
		} Timestamp updatedAt = result.getTimestamp("updated_at");
		return new User(
			result.getInt("id"),
			result.getString("username"),
			result.getString("password_hash"),
			UserRole.valueOf(result.getString("role").toUpperCase()),
			result.getBoolean("is_active"),
			result.getTimestamp("inserted_at").toInstant(),
			(updatedAt == null) ? null : updatedAt.toInstant()
		);
	}

	private static User find(PreparedStatement prepared)
	throws SQLException {
		if (prepared != null) {
			try (ResultSet result = prepared.executeQuery()) {
				if (result.next()) {
					return getResult(result);
				}
			}
		} return null;
	}
	
	@Override
	public User findByUsername(String username) {
		Objects.requireNonNull(username, "Username is NULL.");
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"SELECT * FROM frontend_user WHERE username = ?"
				)
			) {
				prepared.setString(1, username);
				return find(prepared);
			}
		} catch (SQLException e) {
			dispatchSQLException(e);
		} return null;
	}
	
	@Override
	public User findById(Integer id) {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"SELECT * FROM frontend_user WHERE id = ?"
				)
			) {
				prepared.setInt(1, id);
				return find(prepared);
			}
		} catch (SQLException e) {
			dispatchSQLException(e);
		} return null;
	}
	
	@Override
	public boolean update(User user) {
		Objects.requireNonNull(user, "User is NULL.");
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"UPDATE frontend_user SET username = ?, password_hash = ?,"
					+ " role = ?::user_role, is_active = ? WHERE id = ?"
				)
			) {
				prepared.setString(1, user.getUsername());
				prepared.setString(2, user.getPasswordHash());
				prepared.setString(3, user.getRole().name().toLowerCase());
				prepared.setBoolean(4, user.isActive());
				prepared.setInt(5, user.getId());
				return (prepared.executeUpdate() > 0);
			}
		} catch (SQLException e) {
			dispatchSQLException(e);
		} return false;
	}

	@Override
	public boolean delete(Integer id) {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"DELETE FROM frontend_user WHERE id = ?"
				)
			) {
				prepared.setInt(1, id);
				return (prepared.executeUpdate() > 0);
			}
		} catch (SQLException e) {
			dispatchSQLException(e);
		} return false;
	}
	
	private static Collection<User> getCollection(PreparedStatement prepared)
	throws SQLException {
		Collection<User> users = new ArrayList<>();
		if (prepared != null) {
			try (ResultSet result = prepared.executeQuery()) {
				while (result.next()) {
					users.add(getResult(result));
				}
			}
		} return users;
	}
	
	@Override
	public Collection<User> findByRole(UserRole role) {
		Objects.requireNonNull(role, "User role is NULL.");
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"SELECT * FROM frontend_user WHERE role = ?::user_role"
				)
			) {
				prepared.setString(1, role.name().toLowerCase());
				return getCollection(prepared);
			}
		} catch (SQLException e) {
			dispatchSQLException(e);
		} return new ArrayList<>();
	}
	
	@Override
	public Collection<User> getAll() {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"SELECT * FROM frontend_user"
				)
			) {
				return getCollection(prepared);
			}
		} catch (SQLException e) {
			dispatchSQLException(e);
		} return new ArrayList<>();
	}
	
	@Override
	public boolean isEmpty() {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"SELECT 1 FROM frontend_user LIMIT 1"
				);
				ResultSet result = prepared.executeQuery()
			) {
				return !(result.next());
			}
		} catch (SQLException e) {
			dispatchSQLException(e);
		} return false;
	}
}
