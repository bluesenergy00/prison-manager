package it.unina.prisonmanager.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

import it.unina.prisonmanager.dao.PersonDAO;
import it.unina.prisonmanager.db.DBConnection;
import it.unina.prisonmanager.model.Person;

public class PersonDAOImpl extends AbstractDAO<Person> implements PersonDAO
{
	private static final PersonDAOImpl instance = new PersonDAOImpl();
	
	private PersonDAOImpl() {
		super(
			"INSERT INTO person(first_name, last_name, date_of_birth, personal_code,"
			+ " is_provisional_code, photo_url, nationality) VALUES (?, ?, ?, ?, ?, ?, ?)",
			"SELECT * FROM person WHERE id = ?",
			"UPDATE person SET first_name = ?, last_name = ?, date_of_birth = ?, personal_code = ?,"
			+ " is_provisional_code = ?, photo_url = ?, nationality = ? WHERE id = ?",
			"DELETE FROM person WHERE id = ?", "SELECT * FROM person", "SELECT 1 FROM person LIMIT 1"
		);
	}
	
	public static PersonDAOImpl getInstance() {
		return instance;
	}

	@Override
	protected void setupInsert(
		PreparedStatement prepared, Person person
	) throws SQLException {
		if (prepared != null && person != null) {
			prepared.setString(1, person.getFirstName());
			prepared.setString(2, person.getLastName());
			prepared.setDate(3, Date.valueOf(person.getDateOfBirth()));
			prepared.setString(4, person.getPersonalCode());
			prepared.setBoolean(5, person.isProvisionalCode());
			prepared.setString(6, person.getImagePath());
			prepared.setString(7, person.getNationality());
		}
	}
	
	protected void setupUpdate(
		PreparedStatement prepared, Person person
	) throws SQLException {
		if (prepared != null && person != null) {
			setupInsert(prepared, person);
			prepared.setInt(8, person.getId());
		}
	}

	@Override
	protected Person getResult(ResultSet result) throws SQLException {
		if (result != null) {
			Timestamp updatedAt = result.getTimestamp("updated_at");
			return new Person(
				result.getInt("id"),
				result.getString("first_name"),
				result.getString("last_name"),
				result.getDate("date_of_birth").toLocalDate(),
				result.getString("personal_code"),
				result.getBoolean("is_provisional_code"),
				result.getString("photo_url"),
				result.getString("nationality"),
				result.getTimestamp("inserted_at").toInstant(),
				updatedAt == null ? null : updatedAt.toInstant()
			);
		} return null;
	}

	@Override
	public Person findByPersonalCode(String personalCode) {
		Objects.requireNonNull(personalCode, "Personal code is NULL.");
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"SELECT * FROM person WHERE personal_code = ?"
				);
			) {
				prepared.setString(1, personalCode);
				return find(prepared);
			}
		} catch (SQLException e) {
			throw dispatchSQLException(e);
		}
	}

	/*@Override
	public Person findByUsername(String username) {
		Objects.requireNonNull(username, "Username is NULL.");
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					"SELECT * FROM person p JOIN frontend_user u"
					+ " ON u.person_id = p.id WHERE username = ?"
				)
			) {
				prepared.setString(1, username);
				return find(prepared);
			}
		} catch (SQLException e) {
			dispatchSQLException(e);
		} return null;
	}*/
}
