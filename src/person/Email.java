package person;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
	
	private String email;
	
	public Email(String email) {
		if (this.isValid(email))
			this.setEmail(email);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	private boolean isValid(String email) {
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
	    Matcher matcher = pattern.matcher(email);
		return matcher.matches();
		
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
        if (!(o instanceof Email))
            return false;
        
        Email e = (Email) o;
        return Objects.equals(email, e.getEmail());
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(email);
    }
	
	public String toString() {
		return email;
	}

}
