package it.unina.prisonmanager.exception;

public class DuplicateDataException extends DataAccessException
{
	private static final long serialVersionUID = 1L;

	public DuplicateDataException(String message) {
		super(message);
	}
	
	public DuplicateDataException(Throwable cause) {
		super(cause);
	}
	
	public DuplicateDataException(String message, Throwable cause) {
		super(message, cause);
	}
}
