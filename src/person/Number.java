package person;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Number {
	
	private String number;
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Number(String number) {
		if (this.isValid(number))
			this.setNumber(number);
	}
	
	private boolean isValid(String number) {
		Pattern pattern = Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$");
	    Matcher matcher = pattern.matcher(number);
		return matcher.matches();
		
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
        if (!(o instanceof Number))
            return false;
        
        Number n = (Number) o;
        return Objects.equals(number, n.getNumber());
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(number);
    }
	
	public String toString() {
		return number;
	}

}
