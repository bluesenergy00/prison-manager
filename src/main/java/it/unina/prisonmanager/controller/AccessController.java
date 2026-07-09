package it.unina.prisonmanager.controller;

import java.util.Arrays;
import java.util.Objects;

import org.mindrot.jbcrypt.BCrypt;

import it.unina.prisonmanager.connection.TransactionManager;
import it.unina.prisonmanager.dao.UserDAO;
import it.unina.prisonmanager.exception.DataAccessException;
import it.unina.prisonmanager.exception.DuplicateDataException;
import it.unina.prisonmanager.model.User;
import it.unina.prisonmanager.model.UserRole;
import it.unina.prisonmanager.utility.Check;
import it.unina.prisonmanager.view.AccessView;

public class AccessController extends TransactionController
{
	private final UserDAO userDAO;
	private final AccessView accessView;
	private final boolean isOwner;
	
	private static final int PASSWORD_MINIMUM_LENGTH = 8;
	private static final int PASSWORD_MAXIMUM_LENGTH = 120;
	
	public AccessController(
		TransactionManager transactionManager, UserDAO userDAO,
		AccessView accessView, boolean isOwner
	) {
		super(transactionManager);
		this.userDAO = Objects.requireNonNull(
			userDAO, "UserDAO reference is NULL."
		);
		this.accessView = Objects.requireNonNull(
			accessView, "AccessView reference is NULL."
		);
		this.isOwner = isOwner;
	}
	
	@Override
	public void openView() {
		if (!isOwner) {
			goToLoginView();
			return;
		} goToRegistrationView();
	}
	
	public void goToLoginView() {
		accessView.showLoginView();
	}
	
	public void goToRegistrationView() {
		accessView.showRegistrationView(isOwner);
	}
	
	public void handleLoginAttempt(String username, char[] password) {
		try {
			if (username.isBlank() || password.length == 0) {
				accessView.showMessage("Fill in all fields to login.");
				return;
			} User user = transaction(() -> {return userDAO.findByUsername(username);}, null);
			if (user == null || !BCrypt.checkpw(new String(password), user.getPasswordHash())) {
				accessView.showErrorMessage("Invalid credentials. Try again.");
				return;
			} if (!user.isActive()) {
				if (user.getRole() == UserRole.APPENDED) {
					accessView.showMessage(
						"This user is currently waiting to be accepted. Try again later."
					);
					return;
				} accessView.showMessage(
					"This user has been deactivated. Contact administrator."
				);
				return;
			} accessView.showMessage("Welcome back, " + user.getUsername() + '.');
			accessView.close();
			MainController.operateDashboard(user);
		} catch (DataAccessException e) {
			e.printStackTrace();
			accessView.showErrorMessage(e.getMessage());
		} finally {
			Arrays.fill(password, '\0');
		}
	}
	
	private static char[] requireValidPassword(char[] password) {
		Objects.requireNonNull(password, "Password is NULL.");
		int i = password.length;
		Check.requireInRange(
			i, PASSWORD_MINIMUM_LENGTH, PASSWORD_MAXIMUM_LENGTH, "Password needs to be between "
			+ PASSWORD_MINIMUM_LENGTH + " and " + PASSWORD_MAXIMUM_LENGTH + " characters long."
		);
		boolean hasLowercase = false;
		boolean hasUppercase = false;
		boolean hasDigit = false;
		while ((!hasLowercase || !hasUppercase || !hasDigit) && --i >= 0) {
			char c = password[i];
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
	
	public void handleRegistrationAttempt(String username, char[][] password) {
		try {
			if (username.isBlank() || password[0].length == 0 || password[1].length == 0) {
				accessView.showMessage("Fill in all fields to sign up.");
				return;
			} if (!Arrays.equals(password[0], password[1])) {
				accessView.showErrorMessage("Passwords do not match. Try again.");
				return;
			} User user = new User();
			user.setUsername(username);
			user.setPasswordHash(
				BCrypt.hashpw(
					new String(requireValidPassword(password[0])),
					BCrypt.gensalt()
				)
			);
			user.setRole(isOwner ? UserRole.OWNER : UserRole.APPENDED);
			user.setActivityStatus(isOwner);
			transaction(() -> {userDAO.insert(user);}, null);
			if (!isOwner) {
				accessView.showMessage(
					"Thank you, " + user.getUsername()
					+ ". The registration has been submitted."
				);
				goToLoginView();
				return;
			} accessView.close();
			MainController.operateDashboard(user);
		} catch (DuplicateDataException e) {
			accessView.showMessage("This username already exists.");
		} catch (IllegalArgumentException e) {
			accessView.showErrorMessage(e.getMessage());
		} catch( DataAccessException e) {
			e.printStackTrace();
			accessView.showErrorMessage(e.getMessage());
		} finally {
			Arrays.fill(password[0], '\0');
			Arrays.fill(password[1], '\0');
		}
	}
	
	@Override
	public void closeView() {accessView.close();}
}
