package it.unina.prisonmanager.controller;

import java.util.Objects;

import org.mindrot.jbcrypt.BCrypt;

import it.unina.prisonmanager.dao.FrontendUserDAO;
import it.unina.prisonmanager.exception.DataAccessException;
import it.unina.prisonmanager.model.FrontendUser;
import it.unina.prisonmanager.view.LoginView;

public class LoginController extends AuthenticationController
{	
	public LoginController(
		LoginView view,
		FrontendUserDAO userDAO
	) {
		super(view, userDAO);
	}
	
	@Override
	public void handleAuthentication(String username, String password) {
		Objects.requireNonNull(username, "Username is NULL.");
		Objects.requireNonNull(password, "Password is NULL.");
		if (username.isBlank() || password.isEmpty()) {
			view.showMessage("Fill in all fields to sign in.");
			return;
		} try {
			FrontendUser user = userDAO.findByUsername(username.trim());
			if (user == null || !BCrypt.checkpw(password, user.getPasswordHash())) {
				view.showMessage("Invalid credentials. Try again.");
				return;
			} if (!user.isActive()) {
				view.showMessage(
					"This user has been deactivated. Contact administrator."
				);
				return;
			} view.grantAccess(user);
		} catch (DataAccessException e) {
			e.printStackTrace();
			view.showErrorMessage();
		}
	}
}
