package it.unina.prisonmanager.main;

import javax.swing.SwingUtilities;

import it.unina.prisonmanager.controller.MainController;

public class Main
{
	//username -> "bububaba"
	//password -> "IlSovrappeso2000"
	public static void main(String[] args) {
		SwingUtilities.invokeLater(
			() -> {
				MainController.start();
			}
		);
	}
}
