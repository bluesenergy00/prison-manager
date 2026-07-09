package it.unina.prisonmanager.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import it.unina.prisonmanager.controller.AccessController;

public class RegistrationPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private final JTextField usernameField = new JTextField();
	private final JPasswordField[] passwordField = {
		new JPasswordField(), new JPasswordField()
	};
	private final JButton enterButton;
	private final JButton leaveButton;
	
	public RegistrationPanel(
		AccessController accessController, boolean isOwner
	) {
		enterButton = new JButton((isOwner) ? "CREATE OWNER" : "SAVE");
		leaveButton = new JButton((isOwner) ? "EXIT" : "GO BACK");
		enterButton.addActionListener(
			_ -> {
				accessController.handleRegistrationAttempt(
					usernameField.getText().strip(),
					new char[][] {
						passwordField[0].getPassword(),
						passwordField[1].getPassword()
					}
				);
			}
		);
		leaveButton.addActionListener(
			_ -> {
				if (isOwner) {
					accessController.closeView();
				} else {
					accessController.goToLoginView();
				}
			}
		);
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		JPanel userFieldPanel = new JPanel(new GridBagLayout());
		userFieldPanel.setBorder(BorderFactory.createEmptyBorder(0, 67, 0, 67));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.gridx = 0;
		gbc.gridy = 0;
		userFieldPanel.add(new JLabel("Username"), gbc);
		gbc.gridy = 1;
		userFieldPanel.add(new JLabel("Password"), gbc);
		gbc.gridy = 2;
		userFieldPanel.add(new JLabel("Confirm password"), gbc);
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		userFieldPanel.add(passwordField[1], gbc);
		gbc.gridy = 1;
		userFieldPanel.add(passwordField[0], gbc);
		gbc.gridy = 0;
		userFieldPanel.add(usernameField, gbc);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(enterButton);
		buttonPanel.add(leaveButton);
		add(userFieldPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}
}
