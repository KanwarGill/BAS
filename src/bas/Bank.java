package bas;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import account.*;
import person.Customer;
import person.Number;
import person.Person;
import person.Name;
import person.Staff;
import person.Admin;
import person.Email;
import person.Address;
import exception.*;

public class Bank {
	
	// Keeps track of accounts, customers, staff, and admin
	private static ArrayList<Account> accounts = new ArrayList<Account>();
	private static ArrayList<Customer> customers = new ArrayList<Customer>();
	private static ArrayList<Staff> staff = new ArrayList<Staff>();
	private static ArrayList<Admin> admins = new ArrayList<Admin>();
	private static ArrayList<String> credentials = new ArrayList<String>();
	
	static BufferedReader br = null;
	
	// used for setup only
	private HashMap<String, Customer> customerMap = new HashMap<String, Customer>();
	
	/*
	 * Constructor
	 */
	Bank() {
		try {
			this.setup();
			this.setupCustomerAccounts();
			// close the reader
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPersonTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	ArrayList<Account> openAccount(Customer customer, Account acct) {
		// new customer
		if (!customers.contains(customer)) {
			customers.add(customer);
		}
		
		customer.addAccount(acct);
		accounts.add(acct);
		return accounts;
	}
	
	/*
	 * Make sure that the account owner, balance, and type match before deleting
	 * Also the account balance should be 0
	 */
	void deleteAccount(Account acc, Customer customer) throws AccountBalanceNotZeroException {
		
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).equals(acc)) {
				if (acc.getBalance() == 0) {
					accounts.remove(i);
					return;
				} else {
					throw new AccountBalanceNotZeroException("Account balance must be 0!");
				}
			}
		}
	}
	
	ArrayList<String> getCredentials() {
		return credentials;
	}
	
	ArrayList<Customer> getCustomers() {
		return customers;
	}
	
	static Customer getCustomer(String username) {
		for (int i = 0; i < customers.size(); i++)
			if (customers.get(i).getUsername().equals(username))
				return customers.get(i);
		return null;
	}
	
	static Admin getAdmin(String username) {
		for (int i = 0; i < admins.size(); i++)
			if (admins.get(i).getUsername().equals(username))
				return admins.get(i);
		return null;
	}
	
	static Staff getStaff(String username) {
		for (int i = 0; i < staff.size(); i++)
			if (staff.get(i).getUsername().equals(username))
				return staff.get(i);
		return null;
	}
	
	ArrayList<Staff> getStaff() {
		return staff;
	}
	
	ArrayList<Admin> getAdmins() {
		return admins;
	}
	
	ArrayList<Customer> addCustomer(Customer customer) {
		customers.add(customer);
		return customers;
	}
	
	ArrayList<Staff> addStaff(Staff s) {
		staff.add(s);
		return staff;
	}
	
	ArrayList<Admin> addAdmin(Admin admin) {
		admins.add(admin);
		return admins;
	}
	
	Person changeAddress(Person person, Address newAddress) {
		person.setAddress(newAddress);
		return person;
	}
	
	/*
	 * Make sure the customer has no accounts before deleting
	 */
	void deleteCustomer(Customer customer) throws CustomerAccountsNotEmptyException {
		for (int i = 0; i < customers.size(); i++)
			if (customers.get(i).equals(customer)) {
				if (customer.getAccounts().isEmpty()) {
					customers.remove(i);
					return;
				} else {
					throw new CustomerAccountsNotEmptyException("Customer must have no accounts!");
				}
			}
	}
	
	/*
	 * This method sets up the banking application
	 */
	void setup() throws IOException, InvalidPersonTypeException {

		ArrayList<String> addresses = readAddresses();
		credentials = readCredentials();
		
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		System.out.println("Current relative path is: " + s);

		
		String personsPath = Paths.get("").toAbsolutePath().toString() + "\\src\\persons.csv";
		br = new BufferedReader(new FileReader(personsPath));
		
		// Skip first line
		br.readLine();
		
		String line;
		int count = 0;
		
		while ((line = br.readLine()) != null) {
			
			String[] list = line.split(",");
			String type = list[1].trim();
			System.out.println(type);
			
			// setup name
			Name name = new Name(list[2].trim(), list[3].trim());
				
			// setup email
			Email email = new Email(list[4].trim());
			
			// setup number
			Number num = new Number(list[5].trim());
			
			// setup username
			String username = credentials.get(count).split(",")[1].trim();
			
			// setup password
			String password = credentials.get(count).split(",")[2].trim();
			
			// setup address
			String[] addressList = addresses.get(count).split(",");
			Address address = this.setupAddress(addressList);
			
			if (type.equals("Customer")) {
				// setup Customer
				Customer customer = new Customer(name, address, num, email, username, password);
				this.customerMap.put(list[0].trim(), customer);
				this.addCustomer(customer);
			} 
			
			else if (type.equals("Staff")) {
				// setup Staff
				Staff staff = new Staff(name, address, num, email, username, password);
				staff.setCustomers(customers);
				this.addStaff(staff);
			}
			
			else if (type.equals("Admin")) {
				// setup Admin
				Admin admin = new Admin(name, address, num, email, username, password);
				admin.setStaff(staff);
				this.addAdmin(admin);
			}
			
			else
				throw new InvalidPersonTypeException("Person must either be Customer, Staff, or Admin!");
			
			count++;
		}
		
	}
	
	private void setupCustomerAccounts() {
		
		try {
			String accountsPath = Paths.get("").toAbsolutePath().toString() + "\\src\\accounts.csv";
			br = new BufferedReader(new FileReader(accountsPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Skip first line
		try {
			br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String line;
		Account account = null;
		
		try {
			while ((line = br.readLine()) != null) {
				String[] list = line.split(",");
				Name owner = this.customerMap.get(list[0].trim()).getName();
				int accountNumber = Integer.parseInt(list[1].trim());
				String type = list[2].trim();
				double balance = Double.parseDouble(list[3].trim());
				
				if (type.equals("Chequing")) {
					// create new chequing account
					account = new ChequingAccount(accountNumber, balance, owner);
					// add the account to the customer list
					this.customerMap.get(list[0].trim()).addAccount(account);
				}
				
				else if (type.equals("Savings")) {
					// create new savings account
					account = new SavingsAccount(accountNumber, balance, owner);
					// add the account to the customer list
					this.customerMap.get(list[0].trim()).addAccount(account);
				}
				
				else if (type.equals("Investment")) {
					// create new Investment account
					account = new InvestmentAccount(accountNumber, balance, owner);
					// add the account to the customer list
					this.customerMap.get(list[0].trim()).addAccount(account);
				}
				accounts.add(account);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private Address setupAddress(String[] addressList) {
		int houseNum = Integer.parseInt(addressList[1].trim());
		String street = addressList[2].trim();
		String city = addressList[3].trim();
		String province = addressList[4].trim();
		String postalCode = addressList[6].trim();
		String country = addressList[5].trim();
		return new Address(houseNum, street, city, province, postalCode, country);
	}

	private static ArrayList<String> readAddresses() throws IOException {
		
		String addressesPath = Paths.get("").toAbsolutePath().toString() + "\\src\\addresses.csv";
		br = new BufferedReader(new FileReader(addressesPath));
		
		// Skip first line
		br.readLine();
		
		String str;
		ArrayList<String> list = new ArrayList<String>();
		
		while((str = br.readLine()) != null){
		    list.add(str);
		}
		return list;
	}
	
	private static ArrayList<String> readCredentials() throws IOException {
		
		String credentialsPath = Paths.get("").toAbsolutePath().toString() + "\\src\\credentials.csv";
		br = new BufferedReader(new FileReader(credentialsPath));
		
		// Skip first line
		br.readLine();
		
		String str;
		ArrayList<String> list = new ArrayList<String>();
		
		while((str = br.readLine()) != null){
		    list.add(str);
		}
		return list;
	}

}
