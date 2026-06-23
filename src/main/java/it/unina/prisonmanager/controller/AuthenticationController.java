package it.unina.prisonmanager.controller;

import java.util.Objects;

import it.unina.prisonmanager.dao.FrontendUserDAO;
import it.unina.prisonmanager.view.AuthenticationView;

public abstract class AuthenticationController
{
	protected final AuthenticationView view;
	protected final FrontendUserDAO userDAO;
	
	public AuthenticationController(
		AuthenticationView view,
		FrontendUserDAO userDAO
	) {
		this.view = Objects.requireNonNull(view, "AuthenticationView reference is NULL.");
		this.userDAO = Objects.requireNonNull(userDAO, "FrontendUserDAO reference is NULL.");
	}
	
	abstract public void handleAuthentication(String username, String password);
}
