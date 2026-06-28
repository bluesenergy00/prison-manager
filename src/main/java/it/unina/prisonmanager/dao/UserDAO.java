package it.unina.prisonmanager.dao;

import java.util.Collection;

import it.unina.prisonmanager.model.User;
import it.unina.prisonmanager.model.UserRole;

public interface UserDAO extends DataAccessObject<User, Integer>
{
	User findByUsername(String username);
	Collection<User> findByRole(UserRole role);
}
