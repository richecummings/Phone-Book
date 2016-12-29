package finalproject;

/**
 * Defines a Contact object
 * Contact has first and last name, phone number, and email
 * @author Richard Cummings
 *
 */
class Contact {
	private String fName, lName;
	private String phoneNum, email;
	// default constructor - empty contact
	public Contact() {}
	// build Contact according to parameters
	public Contact(String fName, String lName, String phoneNum, String email) {
		this.fName = fName;
		this.lName = lName;
		this.phoneNum = phoneNum;
		this.email = email;
	}
	/**
	 * Get first name
	 * @return first name
	 */
	public String getFName() {
		return fName;
	}
	/**
	 * Set first name
	 * @param fName - name to be set
	 */
	public void setFName(String fName) {
		this.fName = fName;
	}
	/**
	 * Get last name
	 * @return last name
	 */
	public String getLName() {
		return lName;
	}
	public void setLName(String lName) {
		this.lName = lName;
	}
	/**
	 * Get phone number
	 * @return phone number
	 */
	public String getNumber() {
		return phoneNum;
	}
	public void setNumber(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	/**
	 * Get email
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return String.format("First-Name: %s\t Last-Name: %s\n Phone: %s\t email: %s\n\n",
								fName, lName, phoneNum, email);
	}
}
