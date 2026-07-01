package it.unina.prisonmanager.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unina.prisonmanager.controller.AccessController;

public class RegistrationPanel extends UserFieldPanel
{
	private static final long serialVersionUID = 1L;

	private final JButton saveButton;
	private final JButton leaveButton;
	
	public RegistrationPanel(
		AccessController accessController, boolean isOwner
	) {
		saveButton = new JButton((isOwner) ? "CREATE OWNER" : "SAVE");
		leaveButton = new JButton((isOwner) ? "EXIT" : "<-");
		saveButton.addActionListener(
			_ -> {
				accessController.handleRegistrationAttempt(
					firstNameField.getText(), lastNameField.getText(),
					personalCodeField.getText(), isProvisionalCode.isSelected(),
					(String) nationalityField.getSelectedItem(), usernameField.getText(),
					new String(passwordField.getPassword()),
					new String(confirmPasswordField.getPassword())
				);
			}
		);
		leaveButton.addActionListener(
			_ -> {
				if (isOwner) {
					accessController.closeAccessView();
				} else {
					accessController.goToLoginView();
				}
			}
		);
		setLayout(new BorderLayout(10, 10));
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(leaveButton);
		add(personFieldPanel, BorderLayout.NORTH);
		add(userFieldPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}
}
