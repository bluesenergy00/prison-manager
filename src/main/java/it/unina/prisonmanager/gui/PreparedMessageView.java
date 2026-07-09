package it.unina.prisonmanager.gui;

import java.awt.Component;

import javax.swing.JOptionPane;

import it.unina.prisonmanager.view.MessageView;

public interface PreparedMessageView extends MessageView
{
	default Component getParentComponent() {
		/*if (this instanceof Component) {
			return (Component) this;
		} throw new UnsupportedOperationException(
			"This interface is meant to be used only with"
			+ " instances of java.awt.Component and nothing else."
		);*/
		return (this instanceof Component) ? (Component) this : null;
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
	
	@Override
	default void showWarningMessage(String message) {
		JOptionPane.showMessageDialog(
			getParentComponent(), message, "Warning",
			JOptionPane.WARNING_MESSAGE
		);
	}
}

/*public final class PreparedMessageView
{
	private PreparedMessageView() {
		throw new UnsupportedOperationException(
			"it.unina.prisonmanager.utility.PreparedMessageView is a static class."
		);
	}
	
	public static void showMessage(Component parent, String message) {
		JOptionPane.showMessageDialog(
			parent, message, "Message", JOptionPane.INFORMATION_MESSAGE
		);
	}
	
	public static void showErrorMessage(Component parent, String message) {
		JOptionPane.showMessageDialog(
			parent, message, "Error", JOptionPane.ERROR_MESSAGE
		);
	}
	
	public static void showWarningMessage(Component parent, String message) {
		JOptionPane.showMessageDialog(
			parent, message, "Warning", JOptionPane.WARNING_MESSAGE
		);
	}
}*/
