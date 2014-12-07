import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.*;

public class LoginScreen extends JFrame implements GUICreation, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenu fileMenu;
	JMenu botMenu;
	JTextField userField;
	JPasswordField passField;
	JButton loginBtn;
	JButton regBtn;
	ArrayList<User> users;

	public LoginScreen() {
		frameCreation();
		createFile();
		createBot();
		menuBar();
		users = new ArrayList<User>();
		//newUser = new User();

		setLayout(null); // set the layout to null to allow using the
		// setBounds() method
		JLabel userLbl = new JLabel("Username: ");
		JLabel passLbl = new JLabel("Password: ");

		userField = new JTextField();
		passField = new JPasswordField();

		loginBtn = new JButton("Login");
		regBtn = new JButton("Register");

		userLbl.setBounds(80, 70, 200, 30);
		passLbl.setBounds(80, 110, 200, 30);
		userField.setBounds(300, 70, 200, 30);
		passField.setBounds(300, 110, 200, 30);
		loginBtn.setBounds(300, 160, 100, 60);
		regBtn.setBounds(400, 160, 100, 60);
		regBtn.setForeground(Color.red);

		this.add(userLbl);
		this.add(userField);
		this.add(passLbl);
		this.add(passField);
		this.add(loginBtn);
		this.add(regBtn);

		/** Request focus to login field */
		this.addWindowListener(new WindowAdapter() {
			// Requesting focus to user field
			public void windowOpened(WindowEvent event) {
				userField.requestFocus();
			}
		});

		/** Move to next field upon pressing Enter */
		// http://www.rgagnon.com/javadetails/java-0253.html
		userField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER)
					passField.requestFocus();
			}
		});

		/** Listing to user fields and buttons */
		userField.addActionListener(this);
		passField.addActionListener(this);
		loginBtn.addActionListener(this);
		regBtn.addActionListener(this);


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
		quit.setEnabled(false); //disable the quit menu item in the login screen

		fileMenu.add(quit);
	}

	// Creating Bot menu
	private void createBot() {
		botMenu = new JMenu("Bot");
		botMenu.setMnemonic('B');
		botMenu.setBackground(Color.GREEN);

		JMenuItem addBot = new JMenuItem("Add a Bot");
		addBot.setMnemonic('A');
		addBot.addActionListener(this);
		botMenu.add(addBot);

		botMenu.addSeparator();

		JMenuItem displayBot = new JMenuItem("Display Bots");
		displayBot.addActionListener(this);
		botMenu.add(displayBot);
	}

	/** All the implemented methods from GUI Creation */
	public void frameCreation() {
		/** Frame properties */
		setTitle("Supreme Bot: Login");
		setSize(700, 500);
		setResizable(false); // best to keep the frame not resizable
		setLocationRelativeTo(null); // window in the centre
		setDefaultCloseOperation(EXIT_ON_CLOSE); // close when exits

		/** Use the default metal styled titlebar - for Windows */
		setUndecorated(true); // false for mac
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

	}

	public void menuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.setBackground(Color.YELLOW);
		menuBar.add(fileMenu);
		menuBar.add(botMenu);
	}

	/** All the implemented methods from ActionListener */
	public void actionPerformed(ActionEvent e) {

		String user = userField.getText();
		char[] password = passField.getPassword();
		String pass = String.valueOf(password);
		//System.out.print(password);

		//newUser.getUser();

		if (e.getSource() == regBtn) {

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {

					new RegistrationScreen().setVisible(true);
				}
			});

			// https://stackoverflow.com/a/7334900
			//Window frame = SwingUtilities.windowForComponent((Component) e
			//  .getSource());
			// frame.setVisible(false);

			this.setVisible(false);

			/*} else if (user.equals("") || pass.equals("")) {
			JOptionPane.showMessageDialog(null, "Error"); */
		} else  {

			//check if file exists in the directory
			if (fileExist()) {
				readFile(); //if exists read the file

			} else {

				confirmRegistration();
			}

			// loop through entire 'users' file
			for (User u : users) {
				String pas = String.valueOf(u.getPassword());

				if (notEmpty()) {
					if (u.getUser().equals(user) && pas.equals(pass)) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {

								new Dashboard().setVisible(true);
							}
						});

						/** hide the ancestor window */
						Window frame = SwingUtilities.windowForComponent((Component) e
								.getSource());
						frame.setVisible(false);
					} else
						JOptionPane.showMessageDialog(null,"Username/password is not recognised!");
				} else
					JOptionPane.showMessageDialog(null, "Please enter username and password!");
			
			} // end of for-loop	

		} // end of regBtn ActionListener check

	}

	//reading the file
	@SuppressWarnings("unchecked")
	public void readFile() {
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

	private boolean fileExist() {
		File file = new File("users");
		return file.exists();
	}

	/** Confirm registration method */
	private void confirmRegistration() {
		int option = JOptionPane.showConfirmDialog(null, "No users found in the system! "
				+ "Do you want to create a new one?", "Not found", JOptionPane.YES_NO_OPTION);

		if (option == JOptionPane.YES_OPTION) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {

					new RegistrationScreen().setVisible(true);
				}
			});
			this.setVisible(false); // hide the login screen
		}
	}

	public boolean notEmpty() {
		char[] pass = passField.getPassword();
		String password = String.valueOf(pass);		
		String user = userField.getText();

		if (pass != null && password.trim().length() > 0
				&& (user != null && user.trim().length() > 0) ) {
			return true;
		}

		return false;
	}
}
