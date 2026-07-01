package it.unina.prisonmanager.controller;

import it.unina.prisonmanager.dao.impl.UserDAOImpl;
import it.unina.prisonmanager.gui.AccessFrame;
import it.unina.prisonmanager.model.User;

public class MainController
{
	private final UserDAOImpl userDAO = UserDAOImpl.getInstance();
	
	public void start() {
		AccessFrame accessFrame = new AccessFrame();
		AccessController accessController = new AccessController(this, userDAO, accessFrame);
		boolean isOwner = userDAO.isEmpty();
		accessFrame.setAccessFrame(accessController, isOwner);
		if (isOwner) {
			accessFrame.showRegistrationView();
		} else {
			accessFrame.showLoginView();
		} accessFrame.setVisible(true);
	}
	
	public void goToDashboardView(User user) {
		
	}
}
