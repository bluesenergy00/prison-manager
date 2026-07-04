package it.unina.prisonmanager.model;

public interface Entity
{
	default boolean isThisClass(Object obj) {
		return (obj != null && this.getClass() == obj.getClass());
	}
	
	public static int requirePositiveId(int id) {
		return requirePositiveId(id, "ID can only be positive.");
	}
	
	public static int requirePositiveId(int id, String message) {
		if (id <= 0) {
			throw new IllegalArgumentException(message);
		} return id;
	}
	
	void setId(int id);
	int getId();
}
