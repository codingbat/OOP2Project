
/**
 * @author Nazmul Alam | T00152975
 * @version 1.0
 * @since 2014-12-07
 */

import java.io.*;

/**
 * The Class User.
 * This is an instantiable class to create users for the SupremeBot program.
 */
public class User implements Serializable {

	/** Eclipse generated the serialVersionUID. */
	private static final long serialVersionUID = 1L;
	private String user; /** The username of the user. */
	private char[] password; /** The password of the user */

	/**
	 * Creates a new User with the default user name and password.
	 * User needs to be registered into the system before he/she can proceed
	 * with the program functionalities.
	 */
	public User() {
		user = "admin";
		password = new char['p'];
	}

	/**
	 * Creates a new Student with the given name.
	 * The name should include both first and
	 * last name.
	 *
	 * @param user This parameter takes the username.
	 * @param pass This parameter takes the password.
	 */

	public User(String user, char[] pass) {
		this.user = user;
		this.password = pass;
	}

	/**
	 * This method is used to gets the user name.
	 *
	 * @return String This returns the username.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user This sets the user name.
	 * @return void Nothing
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public char[] getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the password to set
	 */
	public void setPassword(char[] password) {
		this.password = password;
	}

}
