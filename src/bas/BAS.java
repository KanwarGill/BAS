package bas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import person.Customer;
import person.Person;
import person.Staff;
import person.Admin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@SuppressWarnings("serial")
public class BAS extends JFrame implements ActionListener {
	
	JTable table;		
	int row = -1;
	int column;
	String originalValue, modifiedValue;
	
	// properties
	Container contentPane;
	JPanel contactPane, searchPane, customerPane;
	JLabel L1, L2, L3, L4, L5, L6;
	
	// Login screen fields
	JTextField usernameField;
	JPasswordField passwordField;
	
	JTextField contactNameField;
	JTextField contactNumberField;
	JTextField contactAddressField;
	JTextField contactEmailField;
	
	// Address screen fields
	JTextField streetNumberField, streetNameField, 
		cityField, provinceField, countryField, postalCodeField;
	
	JButton addContactButton;
	
	static Bank bank;
	
	// track who is logged in
	static Person person;
	
	static Customer customer;
	static Staff staff;
	static Admin admin;

	int count = 0;
	
	/*
	Menu initializes the menubar
	*/
	public BAS(Bank bank)
	{
		BAS.bank = bank;
		this.setup();		
	}
	
	private void setup() {
		
		clearContactPane();
		contactPane = new JPanel();
		contactPane.setLayout(new BorderLayout());
		
		String style = "<style>"
				+ ".center {"
					+ "text-align: center;"
					+ "font-size: 18px;"
				+ "}"
				+ "</style>";
		
		String welcomeMsg = "<html>" + style
							+ "<body>Welcome to your Banking Automation System<br>"
							+ "<h2 class=\"center\">Please Enter your Username and Password</h2></body>"
							+ "</html>";
		
		L1 = initializeMessage(welcomeMsg, JLabel.CENTER, 34);
		contactPane.add(L1, BorderLayout.CENTER);
		
		JButton Button = new JButton ("Click to Begin");
		Button.setBounds(300, 350, 200, 50);
		Button.addActionListener(this);
		contactPane.add(Button, BorderLayout.AFTER_LAST_LINE);
		
		contentPane.add(contactPane);
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


	/*
	 * name: addEntry
	 * date: Nov 18, 2020
	 * description: Add a new contact to the list of contacts.
	 * input/output: This method does not have an input or an output.
	 * 
	 */
	public void addEntry() {
		
		clearContactPane();

		contactPane = new JPanel();

		L1 = new JLabel("Add contact name: ");
		L2 = new JLabel("Add contact number: ");
		L3 = new JLabel("Add contact address: ");
		L4 = new JLabel("Add contact email: ");

		contactPane.add(L1);
		contactNameField = new JTextField(20);
		contactNameField.setEditable(true);
		contactPane.add(contactNameField);

		contactPane.add(L2);
		contactNumberField = new JTextField(20);
		contactNumberField.setEditable(true);
		contactPane.add(contactNumberField);
		
		contactPane.add(L3);
		contactAddressField = new JTextField(20);
		contactAddressField.setEditable(true);
		contactPane.add(contactAddressField);
		
		contactPane.add(L4);
		contactEmailField = new JTextField(20);
		contactEmailField.setEditable(true);
		contactPane.add(contactEmailField);

		//Add the add button to show get text
		addContactButton = new JButton("Add Contact");
		addContactButton.addActionListener(this);
		contactPane.add(addContactButton);

		//display the player pane
		contentPane.add(contactPane);
		validate();
	}
	
	
	/* 
	 * name: validName
	 * date: Nov 18, 2020
	 * description: The method validates a contact name.
	 * input/output: This method returns whether the contact name entered by the user is valid or not.
	 * 
	 */
	public boolean validName(String name) {
		Pattern pattern = Pattern.compile("^[\\p{L} .'-]+$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}
	
	
	/* 
	 * name: validNumber
	 * date: Nov 18, 2020
	 * description: The method validates a contact number.
	 * input/output: This method returns whether the contact number entered by the user is valid or not.
	 * 
	 */
	public boolean validNumber(String number) {
		Pattern pattern = Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$");
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
	}
	
	
	/*
	 * 
	 */
	public boolean validEmail(String email) {
		Pattern pattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:"
											+ "[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	 public void viewEntries() {
		
		JButton Button;
		JPanel bottomPane;
		
		bottomPane = new JPanel();
		bottomPane.setLayout (new BorderLayout());
		
		contentPane = getContentPane();

		if (contactPane != null)
			contentPane.remove(contactPane);

		contactPane = new JPanel();
		contactPane.setLayout(new BorderLayout());

		L1 = new JLabel("Welcome to your Banking Automation System", JLabel.CENTER);
		L1.setFont(new Font ("Arial", Font.BOLD, 34));
		L1.setBounds(0, 10, 100, 50);
		L1.setForeground(Color.black);
		L1.setBackground(Color.ORANGE);
		L1.setOpaque(true);
		contactPane.add(L1, BorderLayout.CENTER);

		Button = new JButton ("Click to Begin");
		Button.setBounds(300, 350, 200, 50);
		Button.addActionListener (this);
		contactPane.add(Button, BorderLayout.SOUTH);

		contactPane.add(bottomPane, BorderLayout.SOUTH);
		contentPane.add(contactPane);

		validate();	//refresh the gui
	 }
	
	/*
	 * 
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		
		String event = e.getActionCommand();

		//Menu Items
		/*if (event.equals("Load")) {
			try	{
				loadBook();
				viewEntries();
			}
			catch(IOException ioerror) {
				System.out.println("Input/Output error - program crashed " + ioerror);
			}
		}
		
		else if (event.equals("Save")) {
			try {
				saveBook();
			}
			catch(IOException ioerror) {
				System.out.println("Input/Output error - program crashed" + ioerror);
			}
		}*/
			
		if (event.equals("Close Window")) {
			System.out.println("Exiting the program!");
			System.exit(0);
		}
		
		else if (event.equals("Click to Begin"))
			loginScreen();
		
		else if (event.equals("Login"))
			authenticateUser();

	}

	private void authenticateUser() {
		
		String username = usernameField.getText().trim();
		@SuppressWarnings("deprecation")
		String password = passwordField.getText().trim();
		
		HashMap<String, String> map = initializeMap();
		
		for (int i = 0; i < bank.getCredentials().size(); i++) {
			
			if (username.equals(bank.getCredentials().get(i).split(",")[1].trim()) && 
					password.equals(bank.getCredentials().get(i).split(",")[2].trim())) {
				
				System.out.println("It's a match!");
				
				String type = map.get(bank.getCredentials().get(i).split(",")[0].trim());
				System.out.println(type);
				
				// Reset
				usernameField.setText("");
				passwordField.setText("");
				JOptionPane.showMessageDialog(contactPane, "Login Successful!");
				
				if (type.equals("Customer")) {
					// set customer
					customer = Bank.getCustomer(username);
					
					// set current logged in user
					person = customer;
					setVisible(false);
					dispose();
					CustomerScreen.main(customer);
				}
				
				else if (type.equals("Staff")) {
					// set customer
					staff = Bank.getStaff(username);
					
					// set current logged in user
					person = staff;
					setVisible(false);
					dispose();
					StaffScreen.main(staff);
				}
				
				else if (type.equals("Admin")) {
					// set customer
					admin = Bank.getAdmin(username);
					
					// set current logged in user
					person = admin;
					setVisible(false);
					dispose();
					AdminScreen.main(admin);
				}
				
				return;
			}
		}
		System.out.println("No match :(");
		JOptionPane.showMessageDialog(contactPane, "Username and Password does not exist!");
	}


	public HashMap<String, String> initializeMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("1", "Customer");
		map.put("2", "Customer");
		map.put("3", "Customer");
		map.put("4", "Customer");
		map.put("5", "Customer");
		map.put("6", "Staff");
		map.put("7", "Staff");
		map.put("8", "Staff");
		map.put("9", "Admin");
		map.put("10", "Admin");
		return map;
	}

	void clearContactPane() {
		
		contentPane = getContentPane();

		if (contactPane != null)
			contentPane.remove(contactPane);
	}

	private void loginScreen() {
	
		clearContactPane();

		contactPane = new JPanel();
		
		L2 = new JLabel("Username: ");
		contactPane.add(L2);
		usernameField = new JTextField(20);
		usernameField.setEditable(true);
		contactPane.add(usernameField);
		
		L3 = new JLabel("Password: ");
		contactPane.add(L3);
		passwordField = new JPasswordField(20);
		passwordField.setEditable(true);
		passwordField.setEchoChar('*');
		contactPane.add(passwordField);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(this);
		contactPane.add(loginButton);

		contentPane.add(contactPane);

		validate();
	}

	/*
	 * name: main
	 * date: Nov 18, 2020
	 * description: This is the main method that will display the menu and run the 
	 * appropriate method.
	 * input/output: It takes nothing as input and prints menu msg depending on the
	 * option selected by the user.
	 * 
	 */
	public static void main(String[] args) {
		initializeBAS();		
	}
	
	static void initializeBAS() {
		Bank bank = new Bank();
		BAS window = new BAS(bank);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Banking Automation System");
		window.setSize(800,600);
		window.setVisible(true);
	}

}
