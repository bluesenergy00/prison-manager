package it.unina.prisonmanager.controller;

import java.util.Objects;

import it.unina.prisonmanager.connection.TransactionManager;
import it.unina.prisonmanager.model.User;
import it.unina.prisonmanager.view.DashboardView;

public class DashboardController extends TransactionController
{
	private final User user;
	private final DashboardView dashboardView;
	
	public DashboardController(
		User user, TransactionManager transactionManager,
		DashboardView dashboardView
	) {
		super(transactionManager);
		this.user = Objects.requireNonNull(user, "User is NULL.");
		this.dashboardView = Objects.requireNonNull(
			dashboardView, "DashboardView reference is NULL."
		);
	}

	@Override
	public void openView() {
	}

	@Override
	public void closeView() {dashboardView.close();}

}
