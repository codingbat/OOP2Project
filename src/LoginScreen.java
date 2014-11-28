import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class LoginScreen extends JFrame implements GUICreation, ActionListener {
	
	JMenu file;
	JMenu bot;
	
	public LoginScreen() {
		frameCreation();
		createFile();
		createBot();
		menuBar();
		
		/** Underlining each mnemonic characters by default */
		UIManager.getDefaults().put("Button.showMnemonics", Boolean.TRUE);
	}

	// Creating file menu
	private void createFile() {
		file = new JMenu("File");
		file.setMnemonic('F');
		file.setBackground(Color.red);

		JMenuItem quit = new JMenuItem("Quit the Program");
		quit.addActionListener(this);
		quit.setForeground(Color.RED);

		file.add(quit);
	}

	// Creating Bot menu
	private void createBot() {
		bot = new JMenu("Bot");
		bot.setMnemonic('B');
		bot.setBackground(Color.GREEN);

		JMenuItem addBot = new JMenuItem("Add a Bot");
		addBot.setMnemonic('A');
		addBot.addActionListener(this);
		bot.add(addBot);

		bot.addSeparator();

		JMenuItem displayBot = new JMenuItem("Display Bots");
		displayBot.addActionListener(this);
		bot.add(displayBot);
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
		System.setProperty("apple.laf.useScreenMenuBar", "true"); // Mac styled
																	// menubar
	}
	
	public void menuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.setBackground(Color.YELLOW);
		menuBar.add(file);
		menuBar.add(bot);
	}
	
	/** All the implemented methods from ActionListener */
	public void actionPerformed(ActionEvent e) {
		
		
	}

}
