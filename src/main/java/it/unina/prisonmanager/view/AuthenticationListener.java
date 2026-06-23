package it.unina.prisonmanager.view;

import it.unina.prisonmanager.model.FrontendUser;

public interface AuthenticationListener
{
	void accessGranted(FrontendUser user);
}
