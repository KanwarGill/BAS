package bas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import person.Address;
import person.Admin;
import person.Customer;
import person.Email;
import person.Number;
import person.Person;

@SuppressWarnings("serial")
class AdminScreen extends JFrame implements ActionListener {
	
	// properties
	Container contentPane;
	JPanel contactPane, searchPane, customerPane;
	JLabel L1, L2, L3, L4, L5, L6;
	JTable table;
	
	// Address screen fields
	JTextField streetNumberField, streetNameField, cityField, provinceField, countryField, postalCodeField;
	
	// Number screen fields
	JTextField numberField;
	
	// Username screen fields
	JTextField usernameField;
	
	// Password screen fields
	JTextField passwordField;
	
	// Password screen fields
	JTextField emailField;
		
	
	private Admin admin;

	public AdminScreen(Admin admin) {
		clearContactPane();
		
		contactPane = new JPanel();
		contactPane.setLayout(new BorderLayout());
		
		JMenuBar menuBar;
		JMenu menu = null;
		JMenuItem menuItem = null;

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		personalMenu(menuBar, menu, menuItem);
		
		adminMenu(menuBar, menu, menuItem);

		securityMenu(menuBar, menu, menuItem);
		
		String style = "<style>"
				+ ".center {"
					+ "text-align: center;"
					+ "font-size: 18px;"
				+ "}"
				+ "</style>";
		
		String firstName = admin.getName().getFirstName();
		String lastName = admin.getName().getLastName();
		
		String welcomeMsg = "<html>" + style
				+ "<body class=\"center\">Welcome " + firstName + " " + lastName +
				" to your Banking Automation System!"
				+ "</html>";

		L1 = initializeMessage(welcomeMsg, JLabel.CENTER, 34);
		contactPane.add(L1, BorderLayout.CENTER);
		
		contentPane.add(contactPane);
		
		this.admin = admin;
		
		validate();
	}
	
	private JLabel initializeMessage(String string, int position, int fontSize) {
		
		JLabel msg = new JLabel(string, position);
		msg.setFont(new Font ("Arial", Font.BOLD, fontSize));
		msg.setBounds(0, 0, 0, 0);
		msg.setForeground(Color.black);
		msg.setBackground(Color.ORANGE);
		msg.setOpaque(true);
		return msg;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String event = e.getActionCommand();
		
		if (event.equals("Logout"))
			logoutScreen();
		
		else if (event.equals("Change Address"))
			changeAddressScreen();

		else if (event.equals("Confirm Address Change"))
			changeAddress(BAS.person);
		
		else if (event.equals("Change Number"))
			changeNumberScreen();
		
		else if (event.equals("Confirm Number Change"))
			changeNumber(BAS.person);
		
		else if (event.equals("Change Username"))
			changeUsernameScreen();
		
		else if (event.equals("Confirm Username Change"))
			changeUsername(BAS.person);
		
		else if (event.equals("Change Password"))
			changePasswordScreen();
		
		else if (event.equals("Confirm Password Change"))
			changePassword(BAS.person);
		
		else if (event.equals("Change Email"))
			changeEmailScreen();
		
		else if (event.equals("Confirm Email Change"))
			changeEmail(BAS.person);
		
		else if (event.equals("View Information"))
			viewPersonalScreen();
		
	}
	
	private void viewPersonalScreen() {
		
		clearContactPane();
		
		contactPane = new JPanel();
		contactPane.setLayout(new BorderLayout());
		
		L1 = new JLabel("Personal Information", JLabel.CENTER);
		L1.setFont(new Font ("Arial", Font.BOLD, 24));
		contactPane.add(L1, BorderLayout.NORTH);

		L2 = new JLabel("End of Page", JLabel.CENTER);
		contactPane.add (L2, BorderLayout.SOUTH);
		
		String[] columnNames = {"Username", "Name", "Address", "Email", "Number"};
		
		table = new JTable(constructPersonalArray(admin), columnNames);
		
		// dont make the table editable
		table.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane(table);
		contactPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(contactPane);
		
		validate();
		
	}
	
	private String[][] constructPersonalArray(Admin admin) {
		
		String[][] personalData = new String[1][5];
		personalData[0][0] = admin.getUsername();
		personalData[0][1] = admin.getName().toString();
		personalData[0][2] = admin.getAddress().toString();
		personalData[0][3] = admin.getEmail().toString();
		personalData[0][4] = admin.getNumber().toString();
		return personalData;
	}

	private void changeEmail(Person person) {
		
		try {
			String email = emailField.getText().trim();
			Email newEmail = new Email(email);
			
			if (newEmail.getEmail() != null) {
				person.setEmail(newEmail);
				// Reset
				emailField.setText("");
				
				JOptionPane.showMessageDialog(contactPane, "Email Change Successful!");
				
				System.out.println(person.getEmail());
				AdminScreen.initializeAdminScreen(admin);
			}
			
			else
				JOptionPane.showMessageDialog(contactPane, "Enter a valid email!");
		}
		
		catch (Exception e) {
			System.out.println("Nothing entered!");
			JOptionPane.showMessageDialog(contactPane, "Please enter all the fields!");
		}
		
	}

	private void changeEmailScreen() {
		
		clearContactPane();

		contactPane = new JPanel();
		
		L1 = new JLabel("Email: ");
		contactPane.add(L1);
		emailField = new JTextField(30);
		emailField.setEditable(true);
		contactPane.add(emailField);
		
		JButton changePasswordButton = new JButton("Confirm Email Change");
		changePasswordButton.addActionListener(this);
		contactPane.add(changePasswordButton);

		contentPane.add(contactPane);

		validate();
		
	}

	private void changePassword(Person person) {

		try {
			String password = passwordField.getText().trim();
			person.setPassword(password);
			
			// Reset
			passwordField.setText("");
			
			JOptionPane.showMessageDialog(contactPane, "Password Change Successful!");
			
			System.out.println(person.getPassword());
			AdminScreen.initializeAdminScreen(admin);
		}
		
		catch (Exception e) {
			System.out.println("Nothing entered!");
			JOptionPane.showMessageDialog(contactPane, "Please enter all the fields!");
		}
		
	}

	private void changePasswordScreen() {

		clearContactPane();

		contactPane = new JPanel();
		
		L1 = new JLabel("Password: ");
		contactPane.add(L1);
		passwordField = new JTextField(20);
		passwordField.setEditable(true);
		contactPane.add(passwordField);
		
		JButton changePasswordButton = new JButton("Confirm Password Change");
		changePasswordButton.addActionListener(this);
		contactPane.add(changePasswordButton);

		contentPane.add(contactPane);

		validate();
		
	}

	private void changeUsername(Person person) {
		
		try {
			String username = usernameField.getText().trim();
			person.setUsername(username);
			
			// Reset
			usernameField.setText("");
			
			JOptionPane.showMessageDialog(contactPane, "Username Change Successful!");
			
			System.out.println(person.getUsername());
			AdminScreen.initializeAdminScreen(admin);
		}
		
		catch (Exception e) {
			System.out.println("Nothing entered!");
			JOptionPane.showMessageDialog(contactPane, "Please enter all the fields!");
		}
		
	}

	private void changeUsernameScreen() {
		
		clearContactPane();

		contactPane = new JPanel();
		
		L1 = new JLabel("Username: ");
		contactPane.add(L1);
		usernameField = new JTextField(20);
		usernameField.setEditable(true);
		contactPane.add(usernameField);
		
		JButton changeUsernameButton = new JButton("Confirm Username Change");
		changeUsernameButton.addActionListener(this);
		contactPane.add(changeUsernameButton);

		contentPane.add(contactPane);

		validate();
		
	}

	private void changeNumber(Person person) {
		
		try {
			String number = numberField.getText().trim();
			Number newNumber= new Number(number);
			
			if (newNumber.getNumber() != null) {
				person.setNumber(newNumber);
				
				// Reset
				numberField.setText("");
				
				JOptionPane.showMessageDialog(contactPane, "Number Change Successful!");
				
				System.out.println(person.getNumber());
				AdminScreen.initializeAdminScreen(admin);
			}
			else
				JOptionPane.showMessageDialog(contactPane, "Enter a valid number!");
		}
		
		catch (Exception e) {
			System.out.println("Nothing entered!");
			JOptionPane.showMessageDialog(contactPane, "Please enter all the fields!");
		}
		
	}

	private void changeNumberScreen() {
		
		clearContactPane();

		contactPane = new JPanel();
		
		L1 = new JLabel("Number: ");
		contactPane.add(L1);
		numberField = new JTextField(20);
		numberField.setEditable(true);
		contactPane.add(numberField);
		
		JButton changeNumberButton = new JButton("Confirm Number Change");
		changeNumberButton.addActionListener(this);
		contactPane.add(changeNumberButton);

		contentPane.add(contactPane);

		validate();
		
	}

	private void changeAddress(Person person) {
		
		try {
			int streetNumber = Integer.parseInt(streetNumberField.getText().trim());
			String streetName = streetNameField.getText().trim();
			String city = cityField.getText().trim();
			String province = provinceField.getText().trim();
			String country = countryField.getText().trim();
			String postalCode = postalCodeField.getText().trim();
			
			Address newAddress = new Address(streetNumber, streetName, city,
					province, postalCode, country);

			BAS.bank.changeAddress(person, newAddress);
			
			// Reset
			streetNumberField.setText("");
			streetNameField.setText("");
			cityField.setText("");
			provinceField.setText("");
			countryField.setText("");
			postalCodeField.setText("");
			
			JOptionPane.showMessageDialog(contactPane, "Address Change Successful!");
			
			System.out.println(person.getAddress());
			AdminScreen.initializeAdminScreen(admin);
		}
		
		catch (Exception e) {
			System.out.println("Nothing entered!");
			JOptionPane.showMessageDialog(contactPane, "Please enter all the fields!");
		}
		
	}

	private void changeAddressScreen() {
		
		clearContactPane();

		contactPane = new JPanel();
		
		L1 = new JLabel("Street Number: ");
		contactPane.add(L1);
		streetNumberField = new JTextField(5);
		streetNumberField.setEditable(true);
		contactPane.add(streetNumberField);
		
		L2 = new JLabel("Street Name: ");
		contactPane.add(L2);
		streetNameField = new JTextField(10);
		streetNameField.setEditable(true);
		contactPane.add(streetNameField);
		
		L3 = new JLabel("City: ");
		contactPane.add(L3);
		cityField = new JTextField(10);
		cityField.setEditable(true);
		contactPane.add(cityField);
		
		L4 = new JLabel("Province: ");
		contactPane.add(L4);
		provinceField = new JTextField(10);
		provinceField.setEditable(true);
		contactPane.add(provinceField);
		
		L5 = new JLabel("Country: ");
		contactPane.add(L5);
		countryField = new JTextField(10);
		countryField.setEditable(true);
		contactPane.add(countryField);
		
		L6 = new JLabel("Postal Code: ");
		contactPane.add(L6);
		postalCodeField = new JTextField(10);
		postalCodeField.setEditable(true);
		contactPane.add(postalCodeField);
		
		JButton changeAddressButton = new JButton("Confirm Address Change");
		changeAddressButton.addActionListener(this);
		contactPane.add(changeAddressButton);

		contentPane.add(contactPane);

		validate();
		
	}
	
	
	private void logoutScreen() {
		JOptionPane.showMessageDialog(contactPane, "Logout Successful!");
		setVisible(false);
		dispose();
		BAS.initializeBAS();
	}
	
	
	private void securityMenu(JMenuBar menuBar, JMenu menu, JMenuItem menuItem) {
		
		menu = new JMenu("Security");
		menuBar.add(menu);

		// Security -> Remove Staff
		menuItem = new JMenuItem("Remove Staff");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Security -> Add System Alert
		menuItem = new JMenuItem("Add System Alert");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
	}

	private void adminMenu(JMenuBar menuBar, JMenu menu, JMenuItem menuItem) {
		
		menu = new JMenu("Account");
		menuBar.add(menu);

		// Admin -> Monitor System
		menuItem = new JMenuItem("Monitor System");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Admin -> Add Staff
		menuItem = new JMenuItem("Add Staff");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Admin -> Assign Access Level
		menuItem = new JMenuItem("Assign Access Level");
		menuItem.addActionListener(this);
		menu.add(menuItem);		
	}

	private void personalMenu(JMenuBar menuBar, JMenu menu, JMenuItem menuItem) {
		
		menu = new JMenu("Personal");
		menuBar.add(menu);
		
		// Personal -> View Information
		menuItem = new JMenuItem("View Information");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Personal -> Change Address
		menuItem = new JMenuItem("Change Address");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// Personal -> Change Number
		menuItem = new JMenuItem("Change Number");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// Personal -> Change Username
		menuItem = new JMenuItem("Change Username");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Personal -> Change Password
		menuItem = new JMenuItem("Change Password");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Personal -> Change Email
		menuItem = new JMenuItem("Change Email");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Personal -> Close Window
		menuItem = new JMenuItem("Logout");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
	}

	void clearContactPane() {
		
		contentPane = getContentPane();

		if (contactPane != null)
			contentPane.remove(contactPane);
	}
	
	public static void main(Admin admin) {
		initializeAdminScreen(admin);		
	}

	private static void initializeAdminScreen(Admin admin) {
		AdminScreen window = new AdminScreen(admin);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Banking Automation System");
		window.setSize(800,600);
		window.setVisible(true);
	}

}
