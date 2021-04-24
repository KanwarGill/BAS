package person;

import java.util.ArrayList;
import exception.AdminNotAuthorizedException;

public class Admin extends Person {
	
	private ArrayList<Staff> staff = new ArrayList<Staff>();
	
	public Admin(Name name, Address addr, Number num, Email email, String username, String password) {
		this.setName(name);
		this.setAddress(addr);
		this.setNumber(num);
		this.setEmail(email);
		this.setUsername(username);
		this.setPassword(password);
	}

	public ArrayList<Staff> addStaff(Staff s) {
		staff.add(s);
		return staff;
	}
	
	public ArrayList<Staff> removeStaff(Staff s) {
		staff.remove(s);
		return staff;
	}
	
	public void setStaff(ArrayList<Staff> s) {
		this.staff = s;
	}
	
	public ArrayList<Staff> getStaff() {
		return this.staff;
	}
	
	public void changeAddress(Staff staff, Address addr) throws AdminNotAuthorizedException {
		if (this.isAuthorized(staff)) {
			staff.setAddress(addr);
		} else {
			throw new AdminNotAuthorizedException("You are not authorized to change the address!");
		}
	}
	
	public void changeName(Staff staff, Name name) throws AdminNotAuthorizedException {
		if (this.isAuthorized(staff)) {
			staff.setName(name);
		} else {
			throw new AdminNotAuthorizedException("You are not authorized to change the name!");
		}
	}


	public void changeNumber(Staff staff, Number num) throws AdminNotAuthorizedException {
		if (this.isAuthorized(staff)) {
			staff.setNumber(num);
		} else {
			throw new AdminNotAuthorizedException("You are not authorized to change the number!");
		}
	}
	
	public void changeEmail(Staff staff, Email email) throws AdminNotAuthorizedException {
		if (this.isAuthorized(staff)) {
			staff.setEmail(email);
		} else {
			throw new AdminNotAuthorizedException("You are not authorized to change the email!");
		}
	}
	
	private boolean isAuthorized(Staff s) {
		return staff.contains(s);
	}

}
