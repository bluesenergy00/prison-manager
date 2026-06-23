package it.unina.prisonmanager.view;

public interface MessageView
{
	String DEFAULT_ERROR_MESSAGE = "An unknown error occurred.";
	
	default void showErrorMessage() {
		showErrorMessage(DEFAULT_ERROR_MESSAGE);
	}
	
	void showMessage(String message);
	void showErrorMessage(String message);
}
