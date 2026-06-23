package it.unina.prisonmanager.view.impl;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unina.prisonmanager.controller.WelcomeController;
import it.unina.prisonmanager.dao.FrontendUserDAO;
import it.unina.prisonmanager.model.FrontendUser;
import it.unina.prisonmanager.view.WelcomeView;

public class WelcomeFrame extends JFrame implements WelcomeView
{
	private static final long serialVersionUID = 1L;
	
	private final JButton loginButton = new JButton("LOGIN");
	private final JButton registerButton = new JButton("REGISTER");
	private final JButton exitButton = new JButton("EXIT");
	
	private FrontendUserDAO userDAO;
	private WelcomeController controller;
	
	private static final String WELCOME_IMAGE_RESOURCE_PATH = "/PrisonManagerLogo.png";
	private static final int WELCOME_IMAGE_WIDTH = 930;
	private static final int WELCOME_IMAGE_HEIGHT = 460;
	
	public WelcomeFrame(FrontendUserDAO userDAO) {
		setupWelcomeController(userDAO);
		setupWelcomeFrame();
	}
	
	private void setupWelcomeController(FrontendUserDAO userDAO) {
		Objects.requireNonNull(userDAO, "FrontendUserDAO reference is NULL.");
		this.userDAO = userDAO;
		this.controller = new WelcomeController(this, userDAO);
	}
	
	private ImageIcon loadPrisonManagerLogo() {
		URL imagePath = getClass().getResource(WELCOME_IMAGE_RESOURCE_PATH);
		if (imagePath != null) {
			try {
				return new ImageIcon(
					ImageIO.read(imagePath).getScaledInstance(
						WELCOME_IMAGE_WIDTH, WELCOME_IMAGE_HEIGHT, Image.SCALE_SMOOTH
					)
				);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} return new ImageIcon();
	}
	
	private void setupWelcomeFrame() {
		setTitle("Prison Manager -  Welcome");
		setSize(950, 550);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		loginButton.addKeyListener(
			new KeyAdapter()
			{
				@Override
				public void keyPressed(KeyEvent e) {
					switch(e.getKeyCode()) {
						case KeyEvent.VK_LEFT:
							exitButton.requestFocusInWindow();
							break;
						case KeyEvent.VK_RIGHT:
							registerButton.requestFocusInWindow();
							break;
						case KeyEvent.VK_ENTER:
							loginButton.doClick();
							break;
						case KeyEvent.VK_ESCAPE:
							exitButton.doClick();
							break;
					}
				}
			}
		);
		loginButton.addActionListener(
			_ -> {
				controller.handleLoginNavigation();
			}
		);
		registerButton.addKeyListener(
			new KeyAdapter()
			{
				@Override
				public void keyPressed(KeyEvent e) {
					switch(e.getKeyCode()) {
						case KeyEvent.VK_LEFT:
							loginButton.requestFocusInWindow();
							break;
						case KeyEvent.VK_RIGHT:
							exitButton.requestFocusInWindow();
							break;
						case KeyEvent.VK_ENTER:
							registerButton.doClick();
							break;
						case KeyEvent.VK_ESCAPE:
							exitButton.doClick();
							break;
					}
				}
			}
		);
		registerButton.addActionListener(
			_ -> {
				controller.handleRegistrationNavigation();
			}
		);
		exitButton.addKeyListener(
			new KeyAdapter()
			{
				@Override
				public void keyPressed(KeyEvent e) {
					switch(e.getKeyCode()) {
						case KeyEvent.VK_LEFT:
							registerButton.requestFocusInWindow();
							break;
						case KeyEvent.VK_RIGHT:
							loginButton.requestFocusInWindow();
							break;
						case KeyEvent.VK_ESCAPE:
						case KeyEvent.VK_ENTER:
							exitButton.doClick();
							break;
					}
				}
			}
		);
		exitButton.addActionListener(
			_ -> {
				System.exit(0);
			}
		);
		buttonPanel.add(loginButton);
		buttonPanel.add(registerButton);
		buttonPanel.add(exitButton);
		add(
			new JLabel(loadPrisonManagerLogo()),
			BorderLayout.NORTH
		);
		add(buttonPanel, BorderLayout.CENTER);
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(
			this, message, "Oops!",
			JOptionPane.ERROR_MESSAGE
		);
	}

	@Override
	public void showErrorMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void navigateToLogin() {
		new LoginDialog(this, this).setVisible(true);
	}

	@Override
	public void navigateToRegistration(boolean isOwner) {
		new RegistrationDialog(this, userDAO, this, isOwner).setVisible(true);
	}
	
	@Override
	public void navigateToDashboard(FrontendUser user) {
		showMessage("Dashboard, " + user.getUsername());
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//new DashboardFrame(user, userDAO).setVisible(true);
		//dispose();
	}
	
	@Override
	public void accessGranted(FrontendUser user) {
		navigateToDashboard(user);
	}
}
