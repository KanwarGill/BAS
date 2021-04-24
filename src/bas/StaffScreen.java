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
import javax.swing.JTable;
import javax.swing.JTextField;

import person.Address;
import person.Admin;
import person.Person;
import person.Staff;
import person.Customer;

@SuppressWarnings("serial")
class StaffScreen extends JFrame implements ActionListener {

	// properties
	Container contentPane;
	JPanel contactPane, searchPane, customerPane;
	JLabel L1, L2, L3, L4, L5, L6;
	JTable table;
	
	// Address screen fields
	JTextField streetNumberField, streetNameField, cityField, provinceField, countryField, postalCodeField;
	
	JTextField accountUsernameField;
	
	private Staff staff;
	
	public StaffScreen(Staff staff) {
		clearContactPane();
		
		contactPane = new JPanel();
		contactPane.setLayout(new BorderLayout());
		
		JMenuBar menuBar;
		JMenu menu = null;
		JMenuItem menuItem = null;

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		personalMenu(menuBar, menu, menuItem);
		
		accountAdminMenu(menuBar, menu, menuItem);
		
		String style = "<style>"
				+ ".center {"
					+ "text-align: center;"
					+ "font-size: 18px;"
				+ "}"
				+ "</style>";
		
		String firstName = staff.getName().getFirstName();
		String lastName = staff.getName().getLastName();
		
		String welcomeMsg = "<html>" + style
				+ "<body class=\"center\">Welcome " + firstName + " " + lastName +
				" to your Banking Automation System!"
				+ "</html>";

		L1 = initializeMessage(welcomeMsg, JLabel.CENTER, 34);
		contactPane.add(L1, BorderLayout.CENTER);
		
		contentPane.add(contactPane);
		
		this.staff = staff;
		
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
			StaffScreen.initializeStaffScreen(staff);
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
	
	private void accountAdminMenu(JMenuBar menuBar, JMenu menu, JMenuItem menuItem) {
		
		menu = new JMenu("Account");
		menuBar.add(menu);

		// Account -> Administration
		menuItem = new JMenuItem("Administration");
		menuItem.addActionListener(this);
		menu.add(menuItem);
	}

	private void securityMenu(JMenuBar menuBar, JMenu menu, JMenuItem menuItem) {
		
		menu = new JMenu("Security");
		menuBar.add(menu);

		// Security -> Report Account
		menuItem = new JMenuItem("Report Account");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Security -> Set Alert
		menuItem = new JMenuItem("Set Alert");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
	}

	private void accountMenu(JMenuBar menuBar, JMenu menu, JMenuItem menuItem) {
		
		menu = new JMenu("Account");
		menuBar.add(menu);

		// Account -> View Balance
		menuItem = new JMenuItem("View Balance");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Account -> Add Funds
		menuItem = new JMenuItem("Add Funds");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Account -> Withdraw Funds
		menuItem = new JMenuItem("Withdraw Funds");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Account -> Send Money
		menuItem = new JMenuItem("Send Money");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Account -> Deposit Cheque
		menuItem = new JMenuItem("Deposit Cheque");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Account -> Add Payee
		menuItem = new JMenuItem("Add Payee");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Account -> Open Account
		menuItem = new JMenuItem("Open Account");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		// Account -> Close Account
		menuItem = new JMenuItem("Close Account");
		menuItem.addActionListener(this);
		menu.add(menuItem);
	}

	private void personalMenu(JMenuBar menuBar, JMenu menu, JMenuItem menuItem) {
		
		menu = new JMenu("Personal");
		menuBar.add(menu);
		
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String event = e.getActionCommand();
		
		if (event.equals("Logout"))
			logoutScreen();
		
		else if (event.equals("Change Address"))
			changeAddressScreen();

		else if (event.equals("Confirm Address Change"))
			changeAddress(BAS.person);
		
		else if (event.equals("Administration"))
			accountScreen();
		
		else if (event.equals("Enter"))
			authorize();
		
		else if (event.equals("Main Menu")) {
			JOptionPane.showMessageDialog(contactPane, "Going back to the main menu!");
			setVisible(false);
			dispose();
			StaffScreen.initializeStaffScreen(staff);
		}
		
	}
	
	private void authorize() {
		
		String accountUsername = accountUsernameField.getText().trim();
		
		Customer customer = Bank.getCustomer(accountUsername);
		
		// if authorized then proceed
		if (staff.getCustomers().contains(customer)) {
			staffAuthorized(customer);
		} else {
			JOptionPane.showMessageDialog(contactPane, "You are not authorized to administer this account!");
		}
			
		
	}

	private void staffAuthorized(Customer customer) {
		
		clearContactPane();
		
		contactPane = new JPanel();
		contactPane.setLayout(new BorderLayout());
		
		JMenuBar menuBar;
		JMenu menu = null;
		JMenuItem menuItem = null;

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		accountMenu(menuBar, menu, menuItem);

		securityMenu(menuBar, menu, menuItem);
		
		navigateMenu(menuBar, menu, menuItem);
		
		String style = "<style>"
				+ ".center {"
					+ "text-align: center;"
					+ "font-size: 18px;"
				+ "}"
				+ "</style>";
		
		String firstName = staff.getName().getFirstName();
		String lastName = staff.getName().getLastName();
		
		String welcomeMsg = "<html>" + style +
				"<body class=\"center\">Welcome " + firstName + " " + lastName +
				" to your Banking Automation System!<br/> You are viewing " + customer.getName().getFirstName() +
				" " + customer.getName().getLastName() + "'s Account." +
				"</html>";

		L1 = initializeMessage(welcomeMsg, JLabel.CENTER, 34);
		contactPane.add(L1, BorderLayout.CENTER);
		
		contentPane.add(contactPane);
				
		validate();
	}

	private void navigateMenu(JMenuBar menuBar, JMenu menu, JMenuItem menuItem) {

		menu = new JMenu("Navigate");
		menuBar.add(menu);

		// Navigate -> Main Menu
		menuItem = new JMenuItem("Main Menu");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
	}

	private void accountScreen() {
		
		clearContactPane();

		contactPane = new JPanel();
		
		L1 = new JLabel("Account Username: ");
		contactPane.add(L1);
		accountUsernameField = new JTextField(10);
		accountUsernameField.setEditable(true);
		contactPane.add(accountUsernameField);
		
		JButton accountUsernameButton = new JButton("Enter");
		accountUsernameButton.addActionListener(this);
		contactPane.add(accountUsernameButton);

		contentPane.add(contactPane);

		validate();
		
	}

	public static void main(Staff staff) {
		initializeStaffScreen(staff);		
	}

	private static void initializeStaffScreen(Staff staff) {
		StaffScreen window = new StaffScreen(staff);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Banking Automation System");
		window.setSize(800,600);
		window.setVisible(true);
	}


}
