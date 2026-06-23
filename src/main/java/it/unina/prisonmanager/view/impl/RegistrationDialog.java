package it.unina.prisonmanager.view.impl;

import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.unina.prisonmanager.controller.RegistrationController;
import it.unina.prisonmanager.dao.FrontendUserDAO;
import it.unina.prisonmanager.view.AuthenticationListener;

public class RegistrationDialog extends AuthenticationDialog
{
	private static final long serialVersionUID = 1L;
	
	private boolean isOwner;
	
	public RegistrationDialog() {}
	
	public RegistrationDialog(
		JFrame parent, FrontendUserDAO userDAO,
		AuthenticationListener listener, boolean isOwner
	) {
		super(parent, "Registration", userDAO, listener);
		this.isOwner = isOwner;
	}

	@Override
	public void showMessage(String message) {
		Objects.requireNonNull(message, "Message is NULL.");
		JOptionPane.showMessageDialog(
			this, message, "Oops!", JOptionPane.ERROR_MESSAGE
		);
	}

	@Override
	public void setAuthenticationController(FrontendUserDAO userDAO) {
		Objects.requireNonNull(userDAO, "FrontendUserDAO reference is NULL.");
		this.controller = new RegistrationController(this, userDAO, isOwner);
	}
}
