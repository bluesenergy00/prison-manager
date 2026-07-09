package it.unina.prisonmanager.connection;

import it.unina.prisonmanager.db.DBConnection;
import it.unina.prisonmanager.exception.DataAccessException;

public class TransactionManagerImpl implements TransactionManager
{
	private static final TransactionManagerImpl instance = new TransactionManagerImpl();
	
	public static TransactionManagerImpl getInstance() {
		return instance;
	}
	
	@Override
	public void begin(Integer userId) throws DataAccessException {
		DBConnection.getInstance().startTransaction(userId);
	}

	@Override
	public void commit() throws DataAccessException {
		DBConnection.getInstance().endTransaction(true);
	}

	@Override
	public void rollback() throws DataAccessException {
		DBConnection.getInstance().endTransaction(false);
	}
}
