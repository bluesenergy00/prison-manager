package it.unina.prisonmanager.dao;

import java.util.Collection;

import it.unina.prisonmanager.model.Entity;

public interface DataAccessObject<T extends Entity, U>
{
	boolean insert(T entity);
	T findById(U id);
	boolean update(T entity);
	boolean delete(U id);
	Collection<T> getAll();
	boolean isEmpty();
}
