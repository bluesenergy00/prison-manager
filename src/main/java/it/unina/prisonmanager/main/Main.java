package it.unina.prisonmanager.main;

import javax.swing.SwingUtilities;

import it.unina.prisonmanager.dao.impl.FrontendUserDAOImpl;
import it.unina.prisonmanager.view.impl.WelcomeFrame;

public class Main
{
	//username -> "bububaba"
	//password -> "IlSovrappesoFabioFrattini"
	public static void main(String[] args) {
		SwingUtilities.invokeLater(
			() -> {
				new WelcomeFrame(FrontendUserDAOImpl.getInstance()).setVisible(true);
			}
		);
	}
}
