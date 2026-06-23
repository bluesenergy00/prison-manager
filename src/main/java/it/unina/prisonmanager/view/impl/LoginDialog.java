package it.unina.prisonmanager.view.impl;

import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.unina.prisonmanager.controller.LoginController;
import it.unina.prisonmanager.dao.FrontendUserDAO;
import it.unina.prisonmanager.view.AuthenticationListener;

public class LoginDialog extends AuthenticationDialog
{
	private static final long serialVersionUID = 1L;
	
	public LoginDialog() {}
	
	public LoginDialog(
		JFrame parent,
		AuthenticationListener listener
	) {
		super(parent, "Login", userDAO, listener);
	}
	
	@Override
	public void setAuthenticationController(FrontendUserDAO userDAO) {
		Objects.requireNonNull(userDAO, "FrontendUserDAO reference is NULL.");
		this.controller = new LoginController(this, userDAO);
	}
	
	@Override
	public void showMessage(String message) {
		Objects.requireNonNull(message, "Message is NULL.");
		JOptionPane.showMessageDialog(
			this, "Access denied", message, JOptionPane.ERROR_MESSAGE
		);
	}
}
