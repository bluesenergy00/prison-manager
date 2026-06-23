package it.unina.prisonmanager.view.impl;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import it.unina.prisonmanager.controller.AuthenticationController;
import it.unina.prisonmanager.model.FrontendUser;
import it.unina.prisonmanager.view.AuthenticationListener;
import it.unina.prisonmanager.view.AuthenticationView;

public abstract class AuthenticationDialog
extends JDialog implements AuthenticationView
{
	private static final long serialVersionUID = 1L;
	
	private final JTextField usernameField = new JTextField(15);
	private final JPasswordField passwordField = new JPasswordField(15);
	private final JButton enterButton = new JButton("ENTER");
	private final JButton goBackButton = new JButton("GO BACK");
	
	private AuthenticationListener listener;
	protected AuthenticationController controller;
	
	public AuthenticationDialog(
		JFrame parent, String title,
		AuthenticationListener listener
	) {
		super(parent, title, true);
		Objects.requireNonNull(listener, "AuthenticationListener reference is NULL.");
		this.listener = listener;
	}
	
	public void setAuthenticationDialog() {
		setSize(400, 150);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(3, 1));
		JPanel usernamePanel = new JPanel();
		usernameField.addKeyListener(
			new KeyAdapter()
			{
				@Override
				public void keyPressed(KeyEvent e) {
					switch(e.getKeyCode()) {
						case KeyEvent.VK_ENTER:
							if (passwordField.getPassword().length > 0) {
								enterButton.doClick();
								break;
							}
						case KeyEvent.VK_DOWN:
							passwordField.requestFocusInWindow();
							break;
						case KeyEvent.VK_ESCAPE:
							goBackButton.doClick();
							break;
					}
				}
			}
		);
		usernamePanel.add(new JLabel("username"));
		usernamePanel.add(usernameField);
		JPanel passwordPanel = new JPanel();
		passwordField.addKeyListener(
			new KeyAdapter()
			{
				@Override
				public void keyPressed(KeyEvent e) {
					switch(e.getKeyCode()) {
						case KeyEvent.VK_UP:
							usernameField.requestFocusInWindow();
							break;
						case KeyEvent.VK_DOWN:
							enterButton.requestFocusInWindow();
							break;
						case KeyEvent.VK_ENTER:
							enterButton.doClick();
							break;
						case KeyEvent.VK_ESCAPE:
							goBackButton.doClick();
							break;
					}
				}
			}
		);
		passwordPanel.add(new JLabel("password"));
		passwordPanel.add(passwordField);
		JPanel buttonPanel = new JPanel();
		enterButton.addKeyListener(
			new KeyAdapter()
			{
				@Override
				public void keyPressed(KeyEvent e) {
					switch(e.getKeyCode()) {
						case KeyEvent.VK_UP:
							passwordField.requestFocusInWindow();
							break;
						case KeyEvent.VK_RIGHT:
							goBackButton.requestFocusInWindow();
							break;
						case KeyEvent.VK_ENTER:
							enterButton.doClick();
							break;
						case KeyEvent.VK_ESCAPE:
							goBackButton.doClick();
							break;
					}
				}
			}
		);
		enterButton.addActionListener(
			_ -> {
				controller.handleAuthentication(
					usernameField.getText(),
					new String(passwordField.getPassword())
				);
			}
		);
		goBackButton.addKeyListener(
			new KeyAdapter()
			{
				@Override
				public void keyPressed(KeyEvent e) {
					switch(e.getKeyCode()) {
						case KeyEvent.VK_UP:
							passwordField.requestFocusInWindow();
							break;
						case KeyEvent.VK_LEFT:
							enterButton.requestFocusInWindow();
							break;
						case KeyEvent.VK_ENTER:
						case KeyEvent.VK_ESCAPE:
							goBackButton.doClick();
							break;
					}
				}
				
			}
		);
		goBackButton.addActionListener(
			_ -> {
				dispose();
			}
		);
		buttonPanel.add(enterButton);
		buttonPanel.add(goBackButton);
		add(usernamePanel);
		add(passwordPanel);
		add(buttonPanel);
	}
	
	@Override
	public void grantAccess(FrontendUser user) {
		Objects.requireNonNull(user, "User is NULL.");
		listener.accessGranted(user);
	}
}
