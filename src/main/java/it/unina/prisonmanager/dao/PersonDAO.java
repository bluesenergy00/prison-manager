package it.unina.prisonmanager.dao;

import it.unina.prisonmanager.model.Person;

public interface PersonDAO extends DataAccessObject<Person, Integer>
{
	Person findByPersonalCode(String personalCode);
	//Person findByUsername(String username);
}
