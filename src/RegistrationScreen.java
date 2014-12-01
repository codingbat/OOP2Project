import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class RegistrationScreen extends JFrame implements GUICreation, ActionListener {
	
	JMenu fileMenu;
	JMenu botMenu;
	JTextField userField;
	JPasswordField passField;
	JPasswordField passConfirmField;	
	JButton cancelBtn;
	JButton regBtn;
	
	public RegistrationScreen() {
		frameCreation();
		createFile();
		createBot();
		menuBar();
		
		setLayout(null); // set the layout to null to allow using the setBounds() method
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
		
		/** Request focus to login field */
		this.addWindowListener(new WindowAdapter() {
			// Requesting focus to user field
			public void windowOpened(WindowEvent event) {
				userField.requestFocus();
			}
		});
		
		/** Move to next field upon pressing Enter */
		// http://www.rgagnon.com/javadetails/java-0253.html
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) 
					requestFocus();
			}
		});
		
		/** Listing to user fields */
		userField.addActionListener(this);
		passField.addActionListener(this);
		cancelBtn.addActionListener(this);
		
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
		setTitle("Supreme Bot: Register");
		setSize(700, 500);
		setResizable(false); // best to keep the frame not resizable
		setLocationRelativeTo(null); // window in the centre
		setDefaultCloseOperation(EXIT_ON_CLOSE); // close when exits
		
		/** Use the default metal styled titlebar - for Windows */
		setUndecorated(true); // false for mac
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		//System.setProperty("apple.laf.useScreenMenuBar", "true"); // Mac styled
																	// menubar
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
		
		if (e.getSource() == cancelBtn) {
			Window frame = SwingUtilities.windowForComponent((Component) e
                    .getSource());
            frame.setVisible(true);
		}
		
	}

}
