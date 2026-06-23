package it.unina.prisonmanager.view;

import it.unina.prisonmanager.model.FrontendUser;

public interface AuthenticationView extends MessageView
{
	void grantAccess(FrontendUser user);
}
