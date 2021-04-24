package person;

import java.util.ArrayList;

import exception.StaffNotAuthorizedException;
import exception.AccountBalanceBelowZeroException;
import account.*;

public class Staff extends Person {
	
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	
	public Staff(Name name, Address addr, Number num, Email email, String username, String password) {
		this.setName(name);
		this.setAddress(addr);
		this.setNumber(num);
		this.setEmail(email);
		this.setUsername(username);
		this.setPassword(password);
//		this.initializeCustomers();
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}
	
	public void addCustomer(Customer c) {
		customers.add(c);
	}
	
	public ArrayList<Customer> getCustomers() {
		return this.customers;
	}
	
	/*
	 * Remove the customer from the list i.e., unassign it
	 * Note this is not the same as deleting a customer
	 */
	public void removeCustomer(Customer customer) {
		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).equals(customer)) {
				customers.remove(i);
			}
		}
	}
	
	public void changeAddress(Customer customer, Address addr) throws StaffNotAuthorizedException {
		if (this.isAuthorized(customer)) {
			customer.setAddress(addr);
		} else {
			throw new StaffNotAuthorizedException("You are not authorized to change the address!");
		}
	}
	
	public void changeName(Customer customer, Name name) throws StaffNotAuthorizedException {
		if (this.isAuthorized(customer)) {
			customer.setName(name);
		} else {
			throw new StaffNotAuthorizedException("You are not authorized to change the name!");
		}
	}
	
	public void changeNumber(Customer customer, Number num) throws StaffNotAuthorizedException {
		if (this.isAuthorized(customer)) {
			customer.setNumber(num);
		} else {
			throw new StaffNotAuthorizedException("You are not authorized to change the number!");
		}
	}
	
	public void changeEmail(Customer customer, Email email) throws StaffNotAuthorizedException {
		if (this.isAuthorized(customer)) {
			customer.setEmail(email);
		} else {
			throw new StaffNotAuthorizedException("You are not authorized to change the email!");
		}
	}
	
	public double addFunds(Customer customer, Account account, double amount) throws StaffNotAuthorizedException {
		if (this.isAuthorized(customer)) {
			if (this.getCustomerAccountIndex(customer) != -1) {
				Account customerAccount = this.getCustomerAccount(customer, account);
				double currentBalance = customerAccount.getBalance();
				double newBalance = currentBalance + amount;
				customerAccount.setBalance(newBalance);
				return newBalance;
			}
		} else {
			throw new StaffNotAuthorizedException("You are not authorized to add funds!");
		}
		return -1;
	}
	
	public double withdrawFunds(Customer customer, Account account, double amount)
			throws StaffNotAuthorizedException, AccountBalanceBelowZeroException {
		if (this.isAuthorized(customer)) {
			if (this.getCustomerAccountIndex(customer) != -1) {
				Account customerAccount = this.getCustomerAccount(customer, account);
				double currentBalance = customerAccount.getBalance();
				if (willBalanceStayPositive(currentBalance, amount)) {
					customerAccount.setBalance(currentBalance - amount);
					return customerAccount.getBalance();
				} else {
					throw new AccountBalanceBelowZeroException("Account balance can't go below zero!");
				}
			}
		} else {
			throw new StaffNotAuthorizedException("You are not authorized to withdraw funds!");
		}
		return -1;
	}
	
	public void openAccount(Customer customer, int type) throws StaffNotAuthorizedException {
		if (this.isAuthorized(customer)) {
			if (this.getCustomerAccountIndex(customer) != -1) {
				int i = this.getCustomerAccountIndex(customer);
				if (type == account.AccountType.CHEQUING) {
					int accountNumber = lastAccountNumberByType(customer, type);
					customers.get(i).addAccount(new ChequingAccount(accountNumber, 0, customer.getName()));
				} else if (type == account.AccountType.SAVINGS) {
					int accountNumber = lastAccountNumberByType(customer, type);
					customers.get(i).addAccount(new SavingsAccount(accountNumber, 0, customer.getName()));
				} else if (type == account.AccountType.INVESTMENT) {
					int accountNumber = lastAccountNumberByType(customer, type);
					customers.get(i).addAccount(new InvestmentAccount(accountNumber, 0, customer.getName()));
				}
			}
		} else {
			throw new StaffNotAuthorizedException("You are not authorized to open account!");
		}
	}
	
	public void closeAccount(Customer customer, Account account) throws StaffNotAuthorizedException {
		if (this.isAuthorized(customer)) {
			if (this.getCustomerAccountIndex(customer) != -1) {
				Account customerAccount = this.getCustomerAccount(customer, account);
				double currentBalance = customerAccount.getBalance();
				if (currentBalance == 0) {
					Customer cust = this.getCustomer(customer);
					cust.deleteAccount(account);
				}
			}
		} else {
			throw new StaffNotAuthorizedException("You are not authorized to close account!");
		}
	}
	
	private int lastAccountNumberByType(Customer customer, int type) {
		int last = 0;
		for (Account account: customer.getAccounts()) {
			if (account.getAccountType() == type) {
				last += 1;
			}
		}
		return last;
	}
	
	private Account getCustomerAccount(Customer customer, Account account) {
		for (Account acct: customer.getAccounts())
			if (acct.equals(account))
				return acct;
		return null;
	}
	
	private int getCustomerAccountIndex(Customer customer) {
		for (int i = 0; i < customers.size(); i++)
			if (this.customers.get(i).equals(customer))
				return i;
		return -1;
	}
	
	private Customer getCustomer(Customer customer) {
		for (Customer cust: customers)
			if (cust.equals(customer)) 
				return cust;
		return null;
	}
	
	private boolean isAuthorized(Customer c) {
		return customers.contains(c);
	}
	
	private boolean willBalanceStayPositive(double currentBalance, double amount) {
		return (currentBalance - amount >= 0);
	}
}
