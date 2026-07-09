package it.unina.prisonmanager.controller;

import it.unina.prisonmanager.connection.TransactionManagerImpl;
import it.unina.prisonmanager.dao.impl.UserDAOImpl;
import it.unina.prisonmanager.exception.DataAccessException;
import it.unina.prisonmanager.gui.AccessFrame;
import it.unina.prisonmanager.model.User;

public class MainController
{	
	public static void startApplication() {
		UserDAOImpl userDAO = UserDAOImpl.getInstance();
		TransactionManagerImpl transactionManager = TransactionManagerImpl.getInstance();
		try {
			boolean isOwner = transactionManager.executeTransaction(
				null, () -> {return userDAO.isEmpty();}
			);
			AccessFrame accessFrame = new AccessFrame();
			AccessController accessController = new AccessController(
				transactionManager, userDAO, accessFrame, isOwner
			);
			accessFrame.setAccessFrame(accessController, isOwner);
			accessController.openView();
		} catch (DataAccessException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void operateDashboard(User user) {
		//System.out.println("\nDashboard Controller Loading ... ... ...");
		//DashboardController dashboardController = new DashboardController(user)
	}
}
