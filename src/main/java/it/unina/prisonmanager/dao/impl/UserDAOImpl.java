package it.unina.prisonmanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

import it.unina.prisonmanager.dao.UserDAO;
import it.unina.prisonmanager.db.DBConnection;
import it.unina.prisonmanager.model.User;
import it.unina.prisonmanager.model.UserRole;

public class UserDAOImpl extends AbstractDAO<User> implements UserDAO
{
	private static final UserDAOImpl instance = new UserDAOImpl();
	
	private UserDAOImpl() {
		super(
			"INSERT INTO frontend_user(username, password_hash, role,"
			+ " is_active) VALUES (?, ?, ?::user_role, ?)",
			"SELECT * FROM frontend_user WHERE id = ?",
			"UPDATE frontend_user SET username = ?, password_hash = ?,"
			+ " role = ?::user_role, is_active = ? WHERE id = ?",
			"DELETE FROM frontend_user WHERE id = ?",
			"SELECT * FROM frontend_user",
			"SELECT 1 FROM frontend_user LIMIT 1"
		);
	}
	
	public static UserDAOImpl getInstance() {
		return instance;
	}
	
	@Override
	protected void setupInsert(PreparedStatement prepared, User user)
	throws SQLException {
		if (prepared != null && user != null) {
			prepared.setString(1, user.getUsername());
			prepared.setString(2, user.getPasswordHash());
			prepared.setString(3, user.getRole().name().toLowerCase());
			prepared.setBoolean(4, user.isActive());
			//prepared.setInt(5, user.getPerson().getId());
		}
	}
	
	@Override
	protected void setupUpdate(PreparedStatement prepared, User user)
	throws SQLException {
		if (prepared != null && user != null) {
			setupInsert(prepared, user);
			prepared.setInt(5, user.getId());
		}
	}
	
	@Override
	protected User getResult(ResultSet result)
	throws SQLException {
		if (result == null) {
			return null;
		} Timestamp updatedAt = result.getTimestamp("updated_at");
		return new User(
			//PersonDAOImpl.getInstance().findById(result.getInt("person_id")),
			result.getInt("id"),
			result.getString("username"),
			result.getString("password_hash"),
			UserRole.valueOf(result.getString("role").toUpperCase()),
			result.getBoolean("is_active"),
			result.getTimestamp("inserted_at").toInstant(),
			(updatedAt == null) ? null : updatedAt.toInstant()
		);
	}
	
	@Override
	public User findByUsername(String username) {
		try {
			Connection connection = DBConnection.getInstance().getActiveConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"SELECT * FROM frontend_user WHERE username = ?"
				)
			) {
				prepared.setString(1, username);
				return find(prepared);
			}
		} catch (SQLException e) {
			throw dispatchSQLException(e);
		}
	}

	/*@Override
	public User findByPersonalCode(String personalCode) {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"SELECT * FROM frontend_user u JOIN person p"
					+ " ON p.id = u.person_id WHERE personal_code = ?"
				);
			) {
				prepared.setString(1, personalCode);
				return find(prepared);
			}
		} catch (SQLException e) {
			dispatchSQLException(e);
		} return null;
	}*/

	@Override
	public Collection<User> findByRole(UserRole role) {
		try {
			Connection connection = DBConnection.getInstance().getActiveConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"SELECT * FROM frontend_user WHERE role = ?::user_role"
				)
			) {
				prepared.setString(1, role.name().toLowerCase());
				return getCollection(prepared);
			}
		} catch (SQLException e) {
			throw dispatchSQLException(e);
		}
	}
}
