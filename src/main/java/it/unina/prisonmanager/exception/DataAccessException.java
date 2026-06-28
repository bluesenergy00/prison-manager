package it.unina.prisonmanager.exception;

public class DataAccessException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_MESSAGE = "An unknown data access error occurred.";
	
	public DataAccessException() {
		this(DEFAULT_MESSAGE);
	}
	
	public DataAccessException(String message) {
		super(message);
	}
	
	public DataAccessException(Throwable cause) {
		this(DEFAULT_MESSAGE, cause);
	}
	
	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
