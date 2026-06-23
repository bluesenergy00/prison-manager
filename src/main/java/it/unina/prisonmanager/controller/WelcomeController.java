package it.unina.prisonmanager.controller;

import java.util.Objects;

import it.unina.prisonmanager.dao.FrontendUserDAO;
import it.unina.prisonmanager.exception.DataAccessException;
import it.unina.prisonmanager.view.WelcomeView;

public class WelcomeController
{
	private final WelcomeView view;
	private final FrontendUserDAO userDAO;
	
	public WelcomeController(
		WelcomeView view, FrontendUserDAO userDAO
	) {
		this.view = Objects.requireNonNull(
			view, "WelcomeView reference is NULL."
		);
		this.userDAO = Objects.requireNonNull(
			userDAO, "FrontendUserDAO reference is NULL."
		);
	}
	
	public void handleLoginNavigation() {
		try {
			if (userDAO.isEmpty()) {
				view.navigateToRegistration(true);
				return;
			} view.navigateToLogin();
		} catch (DataAccessException e) {
			e.printStackTrace();
			view.showErrorMessage();
		}
	}
	
	public void handleRegistrationNavigation() {
		try {
			view.navigateToRegistration(userDAO.isEmpty());
		} catch (DataAccessException e) {
			e.printStackTrace();
			view.showErrorMessage();
		}
	}
}
