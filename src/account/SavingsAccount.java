package account;

import java.util.Objects;

import person.Name;

public class SavingsAccount extends Account {

	public SavingsAccount(int number, double balance, Name owner) {
		super(number, balance, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getAccountType() {
		return AccountType.SAVINGS;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if (!(o instanceof SavingsAccount))
		    return false;
		
		SavingsAccount account = (SavingsAccount) o;
		return Objects.equals(balance, account.getBalance()) &&
				Objects.equals(owner, account.getOwner()) &&
				Objects.equals(alert, account.getAlert()) &&
				Objects.equals(accountNumber, account.getAccountNumber());
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(balance, owner, alert, accountNumber);
    }

}
