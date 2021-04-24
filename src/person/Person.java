package person;

public abstract class Person {
	
	protected Name name;
	protected Address addr;
	protected Number num;
	protected Email email;
	protected String username;
	protected String password;
	
	public Address getAddress() {
		return addr;
	}

	public void setAddress(Address addr) {
		this.addr = addr;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Number getNumber() {
		return num;
	}

	public void setNumber(Number num) {
		this.num = num;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
		
}
