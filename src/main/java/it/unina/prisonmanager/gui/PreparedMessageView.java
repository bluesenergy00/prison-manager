package it.unina.prisonmanager.gui;

import java.awt.Component;

import javax.swing.JOptionPane;

import it.unina.prisonmanager.view.MessageView;

public interface PreparedMessageView extends MessageView
{
	default Component getParentComponent() {
		if (this instanceof Component) {
			return (Component) this;
		} throw new UnsupportedOperationException(
			"This interface is meant to be used only with"
			+ " instances of java.awt.Component and nothing else."
		);
	}
	
	@Override
	default void showMessage(String message) {		
		JOptionPane.showMessageDialog(
			getParentComponent(), message, "Message",
			JOptionPane.INFORMATION_MESSAGE
		);
	}
	
	@Override
	default void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(
			getParentComponent(), message, "Error",
			JOptionPane.ERROR_MESSAGE
		);
	}
}
