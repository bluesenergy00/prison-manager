package it.unina.prisonmanager.gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

abstract public class UserFieldPanel extends PersonFieldPanel
{
	private static final long serialVersionUID = 1L;
	
	protected final JTextField usernameField = new JTextField(STANDARD_FIELD_LENGTH);
	protected final JPasswordField passwordField = new JPasswordField(STANDARD_FIELD_LENGTH);
	protected final JPasswordField confirmPasswordField = new JPasswordField(STANDARD_FIELD_LENGTH);
	protected final JPanel userFieldPanel = new JPanel(new GridLayout(3, 1));
	
	public UserFieldPanel() {
		JPanel usernamePanel = new JPanel();
		usernamePanel.add(new JLabel("Username "));
		usernamePanel.add(usernameField);
		JPanel passwordPanel = new JPanel();
		passwordPanel.add(new JLabel("Password "));
		passwordPanel.add(passwordField);
		JPanel confirmPasswordPanel = new JPanel();
		confirmPasswordPanel.add(new JLabel("Confirm password "));
		confirmPasswordPanel.add(confirmPasswordField);
		userFieldPanel.add(usernamePanel);
		userFieldPanel.add(passwordPanel);
		userFieldPanel.add(confirmPasswordPanel);
	}
}
