package it.unina.prisonmanager.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import it.unina.prisonmanager.controller.AccessController;

public class LoginPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private final JTextField usernameField = new JTextField(15);
	private final JPasswordField passwordField = new JPasswordField(15);
	private final JButton enterButton = new JButton("ENTER");
	private final JButton newAccountButton = new JButton("NEW ACCOUNT");
	private final JButton exitButton = new JButton("EXIT");
	
	public LoginPanel(AccessController accessController) {
		setLayout(new GridLayout(3, 1));
		enterButton.addActionListener(
			_ -> {
				accessController.handleLoginAttempt(
					usernameField.getText().strip(),
					new String(passwordField.getPassword())
				);
			}
		);
		newAccountButton.addActionListener(
			_ -> {
				accessController.goToRegistrationView();
			}
		);
		exitButton.addActionListener(
			_ -> {
				accessController.closeAccessView();
			}
		);
		JPanel usernamePanel = new JPanel();
		usernamePanel.add(new JLabel("Username "));
		usernamePanel.add(usernameField);
		JPanel passwordPanel = new JPanel();
		passwordPanel.add(new JLabel("Password "));
		passwordPanel.add(passwordField);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(enterButton);
		buttonPanel.add(newAccountButton);
		buttonPanel.add(exitButton);
		add(usernamePanel);
		add(passwordPanel);
		add(buttonPanel);
	}
}
