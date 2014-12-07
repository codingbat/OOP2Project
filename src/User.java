/**
 * This is an instantiable class to create users.
 */

/**
 * @author Nazmul Alam | T00152975
 *
 */

import java.io.*;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user;
	private char[] password;
	
	public User() {
		user = "admin";
		password = new char['p'];
	}
	
	public User(String user, char[] pass) {
		this.user = user;
		this.password = pass;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public char[] getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(char[] password) {
		this.password = password;
	}

}
