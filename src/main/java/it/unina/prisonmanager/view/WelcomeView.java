package it.unina.prisonmanager.view;

import it.unina.prisonmanager.model.FrontendUser;

public interface WelcomeView extends AuthenticationListener
{
	void navigateToLogin();
	void navigateToRegistration(boolean isOwner);
	void navigateToDashboard(FrontendUser user);
}
