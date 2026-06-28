package it.unina.prisonmanager.dao;

import java.util.Collection;

public interface DataAccessObject<T, U>
{
	boolean insert(T entity);
	T findById(U id);
	boolean update(T entity);
	boolean delete(U id);
	Collection<T> getAll();
	boolean isEmpty();
}
