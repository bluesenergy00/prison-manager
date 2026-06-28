package it.unina.prisonmanager.exception;

public class DataDependencyException extends DataAccessException
{
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_MESSAGE =
		"This key data is currently used by the system. Cannot be altered.";
	
	public DataDependencyException() {
		this(DEFAULT_MESSAGE);
	}
	
	public DataDependencyException(String message) {
		super(message);
	}
	
	public DataDependencyException(Throwable cause) {
		this(DEFAULT_MESSAGE, cause);
	}
	
	public DataDependencyException(String message, Throwable cause) {
		super(message, cause);
	}
}
