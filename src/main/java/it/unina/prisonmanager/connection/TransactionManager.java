package it.unina.prisonmanager.connection;

import it.unina.prisonmanager.exception.DataAccessException;

public interface TransactionManager
{	
	@FunctionalInterface
	public interface TransactionEvent<R>
	{
		R execute() throws DataAccessException;
	}
	
	@FunctionalInterface
	public interface VoidTransactionEvent
	{
		void execute() throws DataAccessException;
	}
	
	void begin(Integer userId) throws DataAccessException;
	void commit() throws DataAccessException;
	void rollback() throws DataAccessException;
	
	default <R> R executeTransaction(
		Integer userId, TransactionEvent<R> event
	) throws DataAccessException {
		try {
			begin(userId);
			R result = event.execute();
			commit();
			return result;
		} catch (DataAccessException e) {
			try {
				rollback();
			} catch (DataAccessException ex) {
				ex.printStackTrace();
			} throw e;
		}
	}
	
	default void executeTransaction(
		Integer userId, VoidTransactionEvent event
	) {
		executeTransaction(userId, () -> {event.execute(); return null;});
	}
}
