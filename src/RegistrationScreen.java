import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

public class RegistrationScreen extends JFrame implements GUICreation,
ActionListener {

	/**
	 * Eclipse generated the serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private JMenu fileMenu;
	private JTextField userField;
	private JPasswordField passField;
	private JPasswordField passConfirmField;
	private JButton cancelBtn;
	private JButton regBtn;
	private User newUser;
	private ArrayList<User> users;

	public RegistrationScreen() {
		frameCreation();
		createFile();
		menuBar();
		// writeFile();

		setLayout(null); // set the layout to null to allow using the
		// setBounds() method
		JLabel userLbl = new JLabel("Username: ");
		JLabel passLbl = new JLabel("Password: ");
		JLabel passConfirmLbl = new JLabel("Confirm Password: ");

		userField = new JTextField();
		passField = new JPasswordField();
		passConfirmField = new JPasswordField();

		cancelBtn = new JButton("<< Go Back");
		regBtn = new JButton("Register");

		userLbl.setBounds(80, 70, 200, 30);
		passLbl.setBounds(80, 110, 200, 30);
		passConfirmLbl.setBounds(80, 150, 200, 30);
		userField.setBounds(300, 70, 200, 30);
		passField.setBounds(300, 110, 200, 30);
		passConfirmField.setBounds(300, 150, 200, 30);
		cancelBtn.setBounds(300, 190, 100, 30);
		regBtn.setBounds(400, 190, 100, 30);
		regBtn.setForeground(Color.red);

		this.add(userLbl);
		this.add(userField);
		this.add(passLbl);
		this.add(passField);
		this.add(passConfirmLbl);
		this.add(passConfirmField);
		this.add(cancelBtn);
		this.add(regBtn);

		// Create an user
		users = new ArrayList<User>();
		newUser = new User();

		/** Request focus to login field */
		this.addWindowListener(new WindowAdapter() {
			// Requesting focus to user field
			public void windowOpened(WindowEvent event) {
				userField.requestFocus();
			}
		});

		/*****************************************************
		*    Title: React to the ENTER key in a Textfield
		*    Author: mKorbel
		*    Site owner/sponsor: http://www.rgagnon.com
		*    Date: 2014
		*    Code version: edited Mar 8 '13 at 21:09
		*    Availability: http://www.rgagnon.com/javadetails/java-0253.html (Accessed 07 December 2014)
		*    Modified:  added my class to suit with my program
		*****************************************************/	
		
		/** Move to next field upon pressing Enter */
		userField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER)
					passField.requestFocus();	
			}
		});

		/** Listing to user fields */
		userField.addActionListener(this);
		passField.addActionListener(this);
		regBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		/*****************************************************
		*    Title: Is there a way to set underline to mnemonic character in native look and feel under Win 7?
		*    Author: a_horse_with_no_name
		*    Site owner/sponsor: stackoverflow.com
		*    Date: 2013
		*    Code version: edited Sep 15 '13 at 10:23
		*    Availability: http://stackoverflow.com/a/18811279 (Accessed 07 December 2014)
		*    Modified:  (remain unmodified)
		*****************************************************/
		
		/** Underlining each mnemonic characters by default */
		UIManager.getDefaults().put("Button.showMnemonics", Boolean.TRUE);
	}

	// Creating fileMenu menu
	private void createFile() {
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		fileMenu.setBackground(Color.red);

		JMenuItem quit = new JMenuItem("Quit the Program");
		quit.addActionListener(this);
		quit.setForeground(Color.RED);

		fileMenu.add(quit);
	}


	/** All the implemented methods from GUI Creation */
	public void frameCreation() {
		/** Frame properties */
		setTitle("Supreme Bot: Register");
		setSize(700, 500);
		setResizable(false); // best to keep the frame not resizable
		setLocationRelativeTo(null); // window in the centre
		setDefaultCloseOperation(EXIT_ON_CLOSE); // close when exits

		/*****************************************************
		*    Title: JRootPane: setWindowDecorationStyle(int style)
		*    Author: Java2s
		*    Site owner/sponsor: http://www.java2s.com
		*    Date: 2014
		*    Code version: edited Jan 10 '13 at 17:42
		*    Availability: http://www.java2s.com/Code/JavaAPI/javax.swing/JRootPanesetWindowDecorationStyleintstyle.htm (Accessed 07 December 2014)
		*    Modified:  (remain unmodified)
		*****************************************************/
		
		/** Use the default metal styled titlebar - for Windows */
		setUndecorated(true); // false for mac
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
	}

	public void menuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.setBackground(Color.YELLOW);
		menuBar.add(fileMenu);
	}

	/** All the implemented methods from ActionListener */
	public void actionPerformed(ActionEvent e) {
		// if cancel back button is pressed
		if (e.getSource() == cancelBtn) {
			/*****************************************************
			*    Title: Why to use SwingUtilities.invokeLater in main method?
			*    Author: mKorbel
			*    Site owner/sponsor: stackoverflow.com
			*    Date: 2013
			*    Code version: edited Mar 8 '13 at 21:09
			*    Availability: http://stackoverflow.com/q/15302085 (Accessed 07 December 2014)
			*    Modified:  added my class to suit with my program
			*****************************************************/
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new LoginScreen().setVisible(true); // go back to the login screen
				}
			});
			this.setVisible(false); // hide the registration screen
		
		} else if (e.getSource() == regBtn) { // if register button is pressed
			addUser();

		}
		
		if (e.getActionCommand().equals("Quit the Program")) {
			System.exit(0);
		}
	}

	// writing to a file
	private void writeFile() {
		try {
			FileOutputStream fos = new FileOutputStream("users");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(users);
			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// reading the file
	@SuppressWarnings("unchecked")
	private void readFile() {
		try {
			FileInputStream fis = new FileInputStream("users");
			ObjectInputStream ois = new ObjectInputStream(fis);
			users = (ArrayList<User>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
	}

	// Adding a new user
	private void addUser() {
		// Check to see if the file exists
		if (fileExist()) {
			readFile();
		}
		/** Loop through the 'users' file to see if the user
		 * already exists or not.
		 */
		boolean userExist = false;
		for (User u : users) {
			if (u.getUser().equals(userField.getText().trim())) {
				userExist = true; // user is found!
			}
		}
		// user field and password field should not be empty
		if (notEmpty()) {
			if (!userExist) { // if user doesn't exist
				String user = userField.getText();
				char[] pass = passField.getPassword();
				char[] confirm = passConfirmField.getPassword();
				// make sure both passwords are the same
				if (String.valueOf(pass).equals(String.valueOf(confirm))) {
					newUser.setUser(user);
					newUser.setPassword(pass);
					users.add(newUser); // add the new user to the array list
					writeFile(); // save the new user to 'users' file
					JOptionPane.showMessageDialog(null, "User is registered.");
					/*****************************************************
					*    Title: Why to use SwingUtilities.invokeLater in main method?
					*    Author: mKorbel
					*    Site owner/sponsor: stackoverflow.com
					*    Date: 2013
					*    Code version: edited Mar 8 '13 at 21:09
					*    Availability: http://stackoverflow.com/q/15302085 (Accessed 07 December 2014)
					*    Modified:  added my class to suit with my program
					*****************************************************/
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new LoginScreen().setVisible(true); // go back to the login screen
						}
					});
					this.setVisible(false); // hide the registration screen
					
				} else
					JOptionPane.showMessageDialog(null,
							"Password aren't the same");
			} else
				JOptionPane
				.showMessageDialog(null,
						"That username is already taken, please choose another");
		} else
			JOptionPane.showMessageDialog(null, "Username & password are required!");
	}

	private boolean notEmpty() {
		char[] pass = passField.getPassword();
		String password = String.valueOf(pass);		
		String user = userField.getText();
		
		if (pass != null && password.trim().length() > 0
				&& (user != null && user.trim().length() > 0) ) {
			return true;
		}
			
		return false;
	}

	private boolean fileExist() {
		File file = new File("users");

		return file.exists();
	}
}
