package account;

import java.util.Objects;
import person.Name;

public class ChequingAccount extends Account {
	
	
	public ChequingAccount(int number, double balance, Name owner) {
		super(number, balance, owner);
	}


	@Override
	public int getAccountType() {
		return AccountType.CHEQUING;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if (!(o instanceof ChequingAccount))
		    return false;
		
		ChequingAccount account = (ChequingAccount) o;
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
