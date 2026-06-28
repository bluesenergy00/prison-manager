package it.unina.prisonmanager.model;

public interface Entity
{
	@Override
	String toString();
	
	@Override
	boolean equals(Object obj);
	
	@Override
	int hashCode();
}
