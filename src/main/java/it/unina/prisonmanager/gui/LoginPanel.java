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

public class LoginPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private final JTextField usernameField = new JTextField();
	private final JPasswordField passwordField = new JPasswordField();
	private final JButton enterButton = new JButton("ENTER");
	private final JButton newAccountButton = new JButton("NEW ACCOUNT");
	private final JButton exitButton = new JButton("EXIT");
	
	public LoginPanel(AccessController accessController) {
		enterButton.addActionListener(
			_ -> {
				accessController.handleLoginAttempt(
					usernameField.getText().strip(),
					passwordField.getPassword()
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
				accessController.closeView();
			}
		);
		
		//For this panel (layout and borders)
		setLayout(new BorderLayout(20, 20));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		//For credentials, make separate panel with a GridBagLayout
		JPanel credentialsPanel = new JPanel(new GridBagLayout());
		credentialsPanel.setBorder(BorderFactory.createEmptyBorder(0, 90, 0, 90));
		
		//GridBagConstraints contains attributes used to manipulate and position components with precision
		//Only works with panels that have a GridBagLayout set
		GridBagConstraints gbc = new GridBagConstraints();
		
		//Adjust it accordingly and pass it as parameter to add() method, alongside the chosen component
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		//Here, the object attributes are copied and needed only once during the component setup
		credentialsPanel.add(new JLabel("Username "), gbc);
		
		//since it's a deepy copy, references are not shared internally
		//we can keep reusing and modifying the same object for other components, without risks
		gbc.gridy = 1;
		credentialsPanel.add(new JLabel("Password "), gbc);
		
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		credentialsPanel.add(passwordField, gbc);
		
		gbc.gridy = 0;
		credentialsPanel.add(usernameField, gbc);
		
		//Credentials take the middle of the screen
		add(credentialsPanel, BorderLayout.CENTER);
		
		//By default, we use a centered FlowLayout for buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(enterButton);
		buttonPanel.add(newAccountButton);
		buttonPanel.add(exitButton);
		
		//Button panel goes near the bottom of the screen
		add(buttonPanel, BorderLayout.SOUTH);
	}
}
