package it.unina.prisonmanager.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unina.prisonmanager.controller.AccessController;
import it.unina.prisonmanager.utility.ScaledImageLoader;
import it.unina.prisonmanager.view.AccessView;

public class AccessFrame extends JFrame implements AccessView, PreparedMessageView
{
	private static final long serialVersionUID = 1L;
	
	private final CardLayout cardLayout = new CardLayout();
	private final JPanel containerPanel = new JPanel(cardLayout);
	
	private static final String LOGIN_PANEL_NAME = "Login";
	private static final String REGISTRATION_PANEL_NAME = "Registration";
	private static final String WELCOME_PANEL_NAME = "Welcome";
	
	public void setAccessFrame(
		AccessController accessController, boolean isOwner
	) {
		//Set Access Controller
		Objects.requireNonNull(
			accessController, "AccessController reference is NULL."
		);
		
		//Setup Access Frame
		setSize(580, 680);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout(10, 10));
		JLabel logoLabel = new JLabel(
			ScaledImageLoader.loadScaledImageIcon(
				"/PrisonManagerLogo.png", 550, 400
			)
		);
		add(logoLabel, BorderLayout.NORTH);
		if (!isOwner) {
			containerPanel.add(
				new LoginPanel(accessController), LOGIN_PANEL_NAME
			);
		} containerPanel.add(
			new RegistrationPanel(accessController, isOwner),
			(isOwner) ? WELCOME_PANEL_NAME : REGISTRATION_PANEL_NAME
		);
		add(containerPanel, BorderLayout.CENTER);
	}

	public void show(String panelName) {
		setTitle("Prison Manager - " + panelName);
		cardLayout.show(containerPanel, panelName);
		setVisible(true);
	}

	@Override
	public void showLoginView() {
		show(LOGIN_PANEL_NAME);
	}

	@Override
	public void showRegistrationView(boolean isOwner) {
		show((isOwner) ? WELCOME_PANEL_NAME : REGISTRATION_PANEL_NAME);
	}

	@Override
	public void close() {
		dispose();
	}
}
