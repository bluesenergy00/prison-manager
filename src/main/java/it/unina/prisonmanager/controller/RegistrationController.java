package it.unina.prisonmanager.controller;

import java.util.Objects;

import org.mindrot.jbcrypt.BCrypt;

import it.unina.prisonmanager.dao.FrontendUserDAO;
import it.unina.prisonmanager.exception.DataAccessException;
import it.unina.prisonmanager.exception.DuplicateDataException;
import it.unina.prisonmanager.model.FrontendUser;
import it.unina.prisonmanager.model.UserRole;
import it.unina.prisonmanager.view.RegistrationView;

public class RegistrationController extends AuthenticationController
{
	private final boolean isOwner;
	
	public RegistrationController(
		RegistrationView view,
		FrontendUserDAO userDAO,
		boolean isOwner
	) {
		super(view, userDAO);
		this.isOwner = isOwner;
	}
	
	@Override
	public void handleAuthentication(String username, String password) {
		Objects.requireNonNull(username, "Username is NULL.");
		Objects.requireNonNull(password, "Password is NULL.");
		if (username.isBlank() || password.isEmpty()) {
			view.showMessage("Fill in all fields to sign up.");
			return;
		} FrontendUser user = new FrontendUser();
		try {
			user.setUsername(username);
			user.setPasswordHash(
				BCrypt.hashpw(
					FrontendUser.requireValidPassword(password),
					BCrypt.gensalt()
				)
			);
			user.setRole(isOwner ? UserRole.owner : UserRole.standard);
			user.setActivityStatus(true);
			userDAO.insert(user);
			view.showMessage("Registration complete!");
			view.grantAccess(user);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			view.showErrorMessage(e.getMessage());
		} catch (DuplicateDataException e) {
			e.printStackTrace();
			view.showErrorMessage(e.getMessage());
		} catch (DataAccessException e) {
			e.printStackTrace();
			view.showErrorMessage(e.getMessage());
		}
	}
}
