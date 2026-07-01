package it.unina.prisonmanager.view;

public interface AccessView extends MessageView, Closable
{
	void showLoginView();
	void showRegistrationView();
}
