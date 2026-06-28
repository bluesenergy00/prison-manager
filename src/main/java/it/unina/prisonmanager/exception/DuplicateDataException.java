package it.unina.prisonmanager.exception;

public class DuplicateDataException extends DataAccessException
{
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_MESSAGE =
		"This unique object is already present in the system. Cannot be duplicated.";
	
	public DuplicateDataException() {
		this(DEFAULT_MESSAGE);
	}
	
	public DuplicateDataException(String message) {
		super(message);
	}
	
	public DuplicateDataException(Throwable cause) {
		this(DEFAULT_MESSAGE, cause);
	}
	
	public DuplicateDataException(String message, Throwable cause) {
		super(message, cause);
	}
}
