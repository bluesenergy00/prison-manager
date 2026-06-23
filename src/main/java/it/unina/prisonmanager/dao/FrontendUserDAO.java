package it.unina.prisonmanager.dao;

import java.util.Collection;

import it.unina.prisonmanager.model.FrontendUser;
import it.unina.prisonmanager.model.UserRole;

public interface FrontendUserDAO
{
	boolean insert(FrontendUser user);
	FrontendUser findByUsername(String username);
	FrontendUser findById(int id);
	boolean update(FrontendUser user);
	boolean delete(int id);
	Collection<FrontendUser> findByRole(UserRole role);
	Collection<FrontendUser> getAll();
	boolean isEmpty();
}
