package person;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import account.*;

public class Customer extends Person {
	
	private HashSet<Account> accounts = new HashSet<Account>();
	
	public Customer(Name name, Address addr, Number num, Email email, String username, String password) {
		this.setName(name);
		this.setAddress(addr);
		this.setNumber(num);
		this.setEmail(email);
		this.setUsername(username);
		this.setPassword(password);
//		this.setAccounts();
	}
	
	public Set<Account> getAccounts() {
		return accounts;
	}
	
//	void setAccounts() {
//		accounts = new HashSet<Account>();
//	}
	
	public void addAccount(Account acct) {
		accounts.add(acct);
		
	}
	
	public void deleteAccount(Account acct) {
		for (Account account: accounts) {
			if (account.equals(acct)) {
				accounts.remove(account);
			}
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if (!(o instanceof Customer))
		    return false;
		
		Customer customer = (Customer) o;
		return Objects.equals(name, customer.getName()) &&
				Objects.equals(addr, customer.getAddress()) &&
				Objects.equals(num, customer.getNumber()) &&
				Objects.equals(email, customer.getEmail()) &&
				Objects.equals(accounts, customer.getAccounts());
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(name, addr, num, email, accounts);
    }

}
