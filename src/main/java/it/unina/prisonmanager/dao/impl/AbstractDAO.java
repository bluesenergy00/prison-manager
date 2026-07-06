package it.unina.prisonmanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import it.unina.prisonmanager.dao.DataAccessObject;
import it.unina.prisonmanager.db.DBConnection;
import it.unina.prisonmanager.exception.DataAccessException;
import it.unina.prisonmanager.exception.DataDependencyException;
import it.unina.prisonmanager.exception.DuplicateDataException;
import it.unina.prisonmanager.model.Entity;

public abstract class AbstractDAO<T extends Entity>
implements DataAccessObject<T, Integer>
{	
	private String insert;
	private String select;
	private String update;
	private String delete;
	private String selectAll;
	private String booleanStatement;
	
	public AbstractDAO(
		String insert, String select, String update,
		String delete, String selectAll, String booleanStatement
	) {
		this.insert = insert;
		this.select = select;
		this.update = update;
		this.delete = delete;
		this.selectAll = selectAll;
		this.booleanStatement = booleanStatement;
	}
	
	protected static DataAccessException dispatchSQLException(SQLException e) {
		Objects.requireNonNull(e, "SQLException reference is NULL.");
		String state = e.getSQLState();
		if (state == null) {
			return new DataAccessException(
				"Communication with data source has failed."
				+ " Check your connection and try again.", e
			);
		} switch(state) {
			case "23001":
			case "23503":
				return new DataDependencyException(e);
			case "23505":
				return new DuplicateDataException(e);
			default:
				return new DataAccessException(e);
		}
	}
	
	protected abstract void setupInsert(PreparedStatement prepared, T entity) throws SQLException;
	protected abstract void setupUpdate(PreparedStatement prepared, T entity) throws SQLException;
	protected abstract T getResult(ResultSet result) throws SQLException;
	
	protected T find(PreparedStatement prepared)
	throws SQLException {
		if (prepared != null) {
			try (ResultSet result = prepared.executeQuery()) {
				if (result.next()) {
					return getResult(result);
				}
			}
		} return null;
	}
	
	protected Collection<T> getCollection(PreparedStatement prepared)
	throws SQLException {
		Collection<T> entities = new ArrayList<>();
		if (prepared != null) {
			try (ResultSet result = prepared.executeQuery()) {
				while (result.next()) {
					entities.add(getResult(result));
				}
			}
		} return entities;
	}
	
	@Override
	public boolean insert(T entity) {
		try {
			Connection connection = DBConnection.getInstance().getActiveConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(
					insert, Statement.RETURN_GENERATED_KEYS
				)
			) {
				setupInsert(prepared, entity);
				if (prepared.executeUpdate() > 0) {
					try (ResultSet serial = prepared.getGeneratedKeys()) {
						if (serial.next()) {
							entity.setId(serial.getInt(1));
						}
					} return true;
				}
			}
		} catch (SQLException e) {
			throw dispatchSQLException(e);
		} return false;
	}
	
	@Override
	public T findById(Integer id) {
		try {
			Connection connection = DBConnection.getInstance().getActiveConnection();
			try (PreparedStatement prepared = connection.prepareStatement(select)) {
				prepared.setInt(1, id);
				return find(prepared);
			}
		} catch (SQLException e) {
			throw dispatchSQLException(e);
		}
	}

	@Override
	public boolean update(T entity) {
		try {
			Connection connection = DBConnection.getInstance().getActiveConnection();
			try (PreparedStatement prepared = connection.prepareStatement(update)) {
				setupUpdate(prepared, entity);
				return (prepared.executeUpdate() > 0);
			}
		} catch (SQLException e) {
			throw dispatchSQLException(e);
		}
	}
	
	@Override
	public boolean delete(Integer id) {
		try {
			Connection connection = DBConnection.getInstance().getActiveConnection();
			try (PreparedStatement prepared = connection.prepareStatement(delete)) {
				prepared.setInt(1, id);
				return (prepared.executeUpdate() > 0);
			}
		} catch (SQLException e) {
			throw dispatchSQLException(e);
		}
	}
	
	@Override
	public Collection<T> getAll() {
		try {
			Connection connection = DBConnection.getInstance().getActiveConnection();
			try (PreparedStatement prepared = connection.prepareStatement(selectAll)) {
				return getCollection(prepared);
			}
		} catch (SQLException e) {
			throw dispatchSQLException(e);
		}
	}
	
	@Override
	public boolean isEmpty() {
		try {
			Connection connection = DBConnection.getInstance().getActiveConnection();
			try (
				PreparedStatement prepared = connection.prepareStatement(booleanStatement);
				ResultSet result = prepared.executeQuery()
			) {
				return !(result.next());
			}
		} catch (SQLException e) {
			throw dispatchSQLException(e);
		}
	}
}
