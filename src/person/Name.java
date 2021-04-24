package person;

import java.util.Objects;

public class Name {
	
	private String firstName;
	private String lastName;
	
	
	public Name(String firstName, String lastName) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
        if (!(o instanceof Name))
            return false;
        
        Name name = (Name ) o;
        return Objects.equals(lastName, name.getLastName()) &&
        		Objects.equals(firstName, name.getFirstName());
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
	
	public String toString() {
		return firstName + " " + lastName;
	}

}
