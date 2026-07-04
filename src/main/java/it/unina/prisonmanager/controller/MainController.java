package it.unina.prisonmanager.controller;

import it.unina.prisonmanager.dao.impl.UserDAOImpl;
import it.unina.prisonmanager.gui.AccessFrame;
import it.unina.prisonmanager.model.User;
import it.unina.prisonmanager.view.AccessView;

public class MainController
{
	public static AccessView getAccessView(
		AccessController accessController, boolean isOwner
	) {
		return new AccessFrame(accessController, isOwner);
	}
	
	public static void start() {
		new AccessController(
			UserDAOImpl.getInstance()
		).openAccessView();
	}
	
	public static void openDashboard(User user) {
		System.out.println("\nDashboard Controller Loading ... ... ...");
	}
}
