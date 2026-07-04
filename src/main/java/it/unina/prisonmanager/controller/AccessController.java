package it.unina.prisonmanager.controller;

import java.util.Objects;

import org.mindrot.jbcrypt.BCrypt;

import it.unina.prisonmanager.dao.UserDAO;
import it.unina.prisonmanager.exception.DataAccessException;
import it.unina.prisonmanager.exception.DuplicateDataException;
import it.unina.prisonmanager.model.User;
import it.unina.prisonmanager.model.UserRole;
import it.unina.prisonmanager.utility.Check;
import it.unina.prisonmanager.view.AccessView;

public class AccessController
{
	private final UserDAO userDAO;
	private final AccessView accessView;
	
	private final boolean isOwner;
	
	private static final int PASSWORD_MINIMUM_LENGTH = 8;
	private static final int PASSWORD_MAXIMUM_LENGTH = 120;
	
	public AccessController(UserDAO userDAO) {
		this.userDAO = Objects.requireNonNull(
			userDAO, "UserDAO reference is NULL."
		);
		this.isOwner = userDAO.isEmpty();
		this.accessView = MainController.getAccessView(this, isOwner);
	}
	
	public void openAccessView() {
		if (!isOwner) {
			openLoginView();
			return;
		} openRegistrationView();
	}
	
	public void openLoginView() {
		accessView.showLoginView();
	}
	
	public void openRegistrationView() {
		accessView.showRegistrationView();
	}
	
	public void handleLoginAttempt(String username, String password) {
		if (username.isBlank() || password.isEmpty()) {
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
			} accessView.showMessage("Welcome back, " + user.getUsername() + '.');
			closeAccessView();
			MainController.openDashboard(user);
		} catch (DataAccessException e) {
			e.printStackTrace();
			accessView.showErrorMessage(e.getMessage());
		}
	}
	
	private static String requireValidPassword(String password) {
		Objects.requireNonNull(password, "Password is NULL.");
		int i = password.length();
		Check.requireInRange(
			i, PASSWORD_MINIMUM_LENGTH, PASSWORD_MAXIMUM_LENGTH, "Password needs to be between "
			+ PASSWORD_MINIMUM_LENGTH + " and " + PASSWORD_MAXIMUM_LENGTH + " characters long."
		);
		boolean hasLowercase = false;
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
	
	public void handleRegistrationAttempt(String username, String[] password) {
		if (username.isBlank() || password[0].isEmpty() || password[1].isEmpty()) {
			accessView.showMessage("Fill in all fields to sign up.");
			return;
		} if (!password[0].equals(password[1])) {
			accessView.showErrorMessage("Passwords do not match. Try again.");
			return;
		} try {
			User user = new User();
			user.setUsername(username);
			user.setPasswordHash(
				BCrypt.hashpw(
					requireValidPassword(password[0]),
					BCrypt.gensalt()
				)
			);
			user.setRole(isOwner ? UserRole.OWNER : UserRole.APPENDED);
			user.setActivityStatus(isOwner);
			userDAO.insert(user);
			accessView.showMessage("Welcome, " + user.getUsername() + '.');
			closeAccessView();
			MainController.openDashboard(user);
		} catch (DuplicateDataException e) {
			e.printStackTrace();
			accessView.showMessage("This username already exists.");
		} catch (IllegalArgumentException | DataAccessException e) {
			e.printStackTrace();
			accessView.showErrorMessage(e.getMessage());
		}
	}
	
	public void closeAccessView() {
		accessView.close();
	}
}
