package it.unina.prisonmanager.view;

public interface MessageView
{	
	default void showErrorMessage() {
		showErrorMessage("An unknown error occurred.");
	}
	
	void showMessage(String message);
	void showErrorMessage(String message);
}
