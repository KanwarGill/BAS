package account;

import person.Name;
import alert.Alert;

public abstract class Account {
	
	protected double balance;
	protected Name owner;
	protected Alert alert;
	protected int accountNumber;
	
	Account(int number, double balance, Name owner) {
		this.setBalance(balance);
		this.setOwner(owner);
		this.setAccountNumber(number);
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double amount) {
		this.balance = amount;

	}

	public Name getOwner() {
		return owner;
	}

	public void setOwner(Name name) {
		this.owner = name;
	}

	public Alert getAlert() {
		return alert;
	}

	public void setAlert() {
		// TODO
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int number) {
		this.accountNumber = number;
	}
	
	public abstract int getAccountType();
	
	public String toString() {
		return "Account number: " + Integer.toString(accountNumber) + ", Account Balance: $" +
				Double.toString(balance) + ", Account Owner: " + owner;
	}

}
