package it.unina.prisonmanager.controller;

import java.util.Objects;

import org.mindrot.jbcrypt.BCrypt;

import it.unina.prisonmanager.dao.UserDAO;
import it.unina.prisonmanager.exception.DataAccessException;
import it.unina.prisonmanager.model.User;
import it.unina.prisonmanager.view.AccessView;

public class AccessController
{
	private final MainController mainController;
	
	private final UserDAO userDAO;
	private final AccessView accessView;
	
	private static final int PASSWORD_MINIMUM_LENGTH = 8;
	private static final int PASSWORD_MAXIMUM_LENGTH = 120;
	
	public AccessController(
		MainController mainController, UserDAO userDAO, AccessView accessView
	) {
		this.mainController = Objects.requireNonNull(
			mainController, "MainController reference is NULL."
		);
		this.userDAO = Objects.requireNonNull(
			userDAO, "UserDAO reference is NULL."
		);
		this.accessView = Objects.requireNonNull(
			accessView, "AccessView reference is NULL."
		);
	}
	
	public void goToLoginView() {
		accessView.showLoginView();
		//accessView.setTitle("LOGIN");
		//accessView.show("LOGIN");
	}
	
	public void goToRegistrationView() {
		accessView.showRegistrationView();
		//accessView.setTitle("REGISTRATION");
		//accessView.show("REGISTRATION");
	}
	
	public void handleLoginAttempt(String username, String password) {
		if (username.isBlank() || password.isBlank()) {
			accessView.showMessage("Fill in all fields to login.");
			return;
		} try {
			User user = userDAO.findByUsername(username);
			if (user == null || BCrypt.checkpw(password, user.getPasswordHash())) {
				accessView.showErrorMessage("Invalid credentials. Try again.");
				return;
			} if (!user.isActive()) {
				accessView.showMessage(
					"This user has been deactivated. Contact administrator."
				);
				return;
			} accessView.showMessage("Success! " + username);
			accessView.close();
			mainController.goToDashboardView(user);
		} catch (DataAccessException e) {
			e.printStackTrace();
			accessView.showErrorMessage(e.getMessage());
		}
	}
	
	private static String requireValidPassword(String password) {
		Objects.requireNonNull(password, "Password is NULL.");
		int i = password.length();
		if (i < PASSWORD_MINIMUM_LENGTH || i > PASSWORD_MAXIMUM_LENGTH) {
			throw new IllegalArgumentException(
				"Password needs to be between " + PASSWORD_MINIMUM_LENGTH
				+ " and " + PASSWORD_MAXIMUM_LENGTH + " characters long."
			);
		} boolean hasLowercase = false;
		boolean hasUppercase = false;
		boolean hasDigit = false;
		while ((!hasLowercase || !hasUppercase || !hasDigit) && --i >= 0) {
			char c = password.charAt(i);
			if (Character.isLowerCase(c)) {
				hasLowercase = true;
			} else if (Character.isUpperCase(c)) {
				hasUppercase = true;
			} else if (Character.isDigit(c)) {
				hasDigit = true;
			}
		} if (i < 0) {
			throw new IllegalArgumentException(
				"Password needs lowercase, uppercase and digit characters."
			);
		} return password;
	}
	
	public void handleRegistrationAttempt(
		String firstName, String lastName, String personalCode, boolean isProvisional,
		String nationality, String username, String password, String confirmedPassword
	) {
		
	}
	
	public void closeAccessView() {
		accessView.close();
	}
}
