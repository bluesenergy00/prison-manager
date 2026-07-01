package it.unina.prisonmanager.gui;

import java.awt.CardLayout;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unina.prisonmanager.controller.AccessController;
import it.unina.prisonmanager.view.AccessView;

public class AccessFrame extends JFrame
implements AccessView, PreparedMessageView
{
	private static final long serialVersionUID = 1L;
	
	private final CardLayout cardLayout = new CardLayout();
	private final JPanel containerPanel = new JPanel(cardLayout);
	
	public AccessFrame() {}
	
	public void setAccessFrame(
		AccessController accessController, boolean isOwner
	) {
		//Set Access Controller
		Objects.requireNonNull(
			accessController, "AccessController reference is NULL."
		);
		
		//Setup Access Frame
		setTitle("Prison Manager - WELCOME");
		setSize(500, 550);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		containerPanel.add(new LoginPanel(accessController), "LOGIN");
		containerPanel.add(
			new RegistrationPanel(accessController, isOwner), "REGISTRATION"
		);
		add(containerPanel);
	}

	public void show(String panelName) {
		setTitle("Prison Manager - " + panelName);
		cardLayout.show(containerPanel, panelName);
	}

	@Override
	public void showLoginView() {
		show("LOGIN");
		
	}

	@Override
	public void showRegistrationView() {
		show("REGISTRATION");
	}
	
	@Override
	public void close() {
		this.dispose();
	}
}
