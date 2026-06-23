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

import it.unina.prisonmanager.dao.FrontendUserDAO;
import it.unina.prisonmanager.db.DBConnection;
import it.unina.prisonmanager.exception.DataAccessException;
import it.unina.prisonmanager.exception.DuplicateDataException;
import it.unina.prisonmanager.model.FrontendUser;
import it.unina.prisonmanager.model.UserRole;

public class FrontendUserDAOImpl implements FrontendUserDAO
{
	private static FrontendUserDAOImpl instance;
	
	private FrontendUserDAOImpl() {}
	
	public static FrontendUserDAOImpl getInstance() {
		if (instance == null) {
			instance = new FrontendUserDAOImpl();
		} return instance;
	}
	
	@Override
	public boolean insert(FrontendUser user) {
		Objects.requireNonNull(user, "FrontendUser reference is NULL.");
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
				prepared.setString(3, user.getRole().name());
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
			if ("23505".equals(e.getSQLState())) {
				throw new DuplicateDataException(
					"The chosen username is already in use. Try another one.", e
				);
			} throw new DataAccessException(e);
		} return false;
	}
	
	private static FrontendUser getResult(ResultSet result)
	throws SQLException {
		if (result == null) {
			return null;
		} Timestamp updatedAt = result.getTimestamp("updated_at");
		return new FrontendUser(
			result.getInt("id"),
			result.getString("username"),
			result.getString("password_hash"),
			UserRole.valueOf(result.getString("role")),
			result.getBoolean("is_active"),
			result.getTimestamp("inserted_at").toInstant(),
			(updatedAt == null) ? null : updatedAt.toInstant()
		);
	}

	private static FrontendUser find(PreparedStatement prepared)
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
	public FrontendUser findByUsername(String username) {
		Objects.requireNonNull(username, "Username is NULL.");
		if (username.isBlank()) {
			return null;
		} try {
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
			throw new DataAccessException(e.getSQLState(), e);
		}
	}
	
	@Override
	public FrontendUser findById(int id) {
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
			throw new DataAccessException(e.getSQLState(), e);
		}
	}
	
	@Override
	public boolean update(FrontendUser user) {
		Objects.requireNonNull(user, "FrontendUser reference is NULL.");
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"UPDATE frontend_user SET username = ?, password_hash = ?,"
					+ "role = ?::user_role, is_active = ? WHERE id = ?"
				)
			) {
				prepared.setString(1, user.getUsername());
				prepared.setString(2, user.getPasswordHash());
				prepared.setString(3, user.getRole().name());
				prepared.setBoolean(4, user.isActive());
				prepared.setInt(5, user.getId());
				return (prepared.executeUpdate() > 0);
			}
		} catch (SQLException e) {
			if ("23505".equals(e.getSQLState())) {
				throw new DuplicateDataException(e.getSQLState(), e);
			} throw new DataAccessException(e.getSQLState(), e);
		}
	}

	@Override
	public boolean delete(int id) {
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
			throw new DataAccessException(e.getSQLState(), e);
		}
	}
	
	private static Collection<FrontendUser> getCollection(PreparedStatement prepared)
	throws SQLException {
		Collection<FrontendUser> users = new ArrayList<>();
		if (prepared != null) {
			try (ResultSet result = prepared.executeQuery()) {
				while (result.next()) {
					users.add(getResult(result));
				}
			}
		} return users;
	}
	
	@Override
	public Collection<FrontendUser> findByRole(UserRole role) {
		Objects.requireNonNull(role, "User role is NULL.");
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"SELECT * FROM frontend_user WHERE role = ?::user_role"
				)
			) {
				prepared.setString(1, role.name());
				return getCollection(prepared);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e.getSQLState(), e);
		}
	}
	
	@Override
	public Collection<FrontendUser> getAll() {
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
			throw new DataAccessException(e.getSQLState(), e);
		}
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
			throw new DataAccessException(e.getSQLState(), e);
		}
	}
}
