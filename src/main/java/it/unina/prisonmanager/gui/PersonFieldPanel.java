package it.unina.prisonmanager.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Locale;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

public class PersonFieldPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	protected final JTextField firstNameField = new JTextField();
	protected final JTextField lastNameField = new JTextField();
	protected final JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
	protected final JTextField personalCodeField = new JTextField();
	protected final JCheckBox isProvisionalCode = new JCheckBox("Provisional");
	protected final JComboBox<String> nationalityComboBox = new JComboBox<>(Locale.getISOCountries());
	
	public PersonFieldPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("First name "), gbc);
		gbc.gridx = 2;
		add(new JLabel("Last name"), gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("Personal code "), gbc);
		gbc.gridx = 2;
		add(isProvisionalCode, gbc);
		gbc.gridy = 2;
		add(new JLabel("Nationality "), gbc);
		gbc.gridx = 0;
		add(new JLabel("Date of birth "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		add(firstNameField, gbc);
		gbc.gridx = 3;
		add(lastNameField, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(personalCodeField, gbc);
		gbc.gridy = 2;
		add(dateSpinner, gbc);
		gbc.gridx = 3;
		add(nationalityComboBox, gbc);
	}
}
