package it.unina.prisonmanager.gui;

import java.awt.GridLayout;
import java.util.Locale;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

abstract public class PersonFieldPanel extends JPanel implements SwingConstants2
{
	private static final long serialVersionUID = 1L;
	
	protected final JTextField firstNameField = new JTextField(STANDARD_FIELD_LENGTH);
	protected final JTextField lastNameField = new JTextField(STANDARD_FIELD_LENGTH);
	protected final JTextField personalCodeField = new JTextField(STANDARD_FIELD_LENGTH);
	protected final JCheckBox isProvisionalCode = new JCheckBox("Provisional");
	protected final JComboBox<String> nationalityField = new JComboBox<>(Locale.getISOCountries());
	protected final JPanel personFieldPanel = new JPanel(new GridLayout(4, 1));
	
	public PersonFieldPanel() {
		JPanel firstNamePanel = new JPanel();
		firstNamePanel.add(new JLabel("First name "));
		firstNamePanel.add(firstNameField);
		JPanel lastNamePanel = new JPanel();
		lastNamePanel.add(new JLabel("Last name "));
		lastNamePanel.add(lastNameField);
		JPanel personalCodePanel = new JPanel();
		personalCodePanel.add(new JLabel("Personal code "));
		personalCodePanel.add(personalCodeField);
		personalCodePanel.add(isProvisionalCode);
		JPanel nationalityPanel = new JPanel();
		nationalityPanel.add(new JLabel("Nationality "));
		nationalityPanel.add(nationalityField);
		personFieldPanel.add(firstNamePanel);
		personFieldPanel.add(lastNamePanel);
		personFieldPanel.add(personalCodePanel);
		personFieldPanel.add(nationalityPanel);
	}
}
