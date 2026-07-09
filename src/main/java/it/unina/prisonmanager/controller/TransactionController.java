package it.unina.prisonmanager.controller;

import java.util.Objects;

import it.unina.prisonmanager.connection.TransactionManager;

public abstract class TransactionController
{
	private final TransactionManager transactionManager;
	
	protected TransactionController(
		TransactionManager transactionManager
	) {
		this.transactionManager = Objects.requireNonNull(
			transactionManager, "TransactionManager reference is NULL."
		);
	}
	
	protected <R> R transaction(
		TransactionManager.TransactionEvent<R> event, Integer userId
	) {
		return transactionManager.executeTransaction(userId, event);
	}
	
	protected void transaction(
		TransactionManager.VoidTransactionEvent event, Integer userId
	) {
		transactionManager.executeTransaction(userId, event);
	}
	
	public abstract void openView();
	public abstract void closeView();
}
