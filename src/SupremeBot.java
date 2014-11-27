import chatter.CleverBotSession;

import javax.swing.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import java.util.List;
import java.util.ArrayList;

public class SupremeBot extends JFrame implements ActionListener, Serializable {

	JTextArea chatArea;
	JTextField chatField;
	JMenu file;
	JMenu Bot;
	JButton send;

	CleverBotSession bot;

	List<String> chatlog;

	public SupremeBot() {
		/** Frame properites */
		super("Supreme Bot");
		setSize(700, 500);
		setResizable(false); // best to keep the frame not resizable
		setLocationRelativeTo(null); //window in the centre
		setDefaultCloseOperation(EXIT_ON_CLOSE); //close when exits

		/** Use the default metal styled titlebar - for Windows */
		// setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		System.setProperty("apple.laf.useScreenMenuBar", "true"); //Mac styled menubar

		// set the default layout
		// Container cpane = getContentPane();
		// cpane.setLayout(new FlowLayout());

		/** Setting chat area layout */
		Container text = getContentPane();
		text.setLayout(new GridLayout());

		/** Creating menu */
		createFile();
		createBot();
		

		/** Setting menu bar */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.setBackground(Color.YELLOW);
		menuBar.add(file);
		menuBar.add(Bot);

		/** Setting chat area */
		chatArea = new JTextArea();
		text.add(chatArea);
		chatlog = new ArrayList<String>(); //save chat
		//https://stackoverflow.com/questions/8849063/adding-a-scrollable-jtextarea-java
		JScrollPane scroll = new JScrollPane (chatArea,  // set scroll able chat area
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		text.add(scroll);

		/** Setting chat sending field */
		chatField = new JTextField(5);
		text.add(chatField);

		/** Adding bot */
		bot = new CleverBotSession("http://www.cleverbot.com/webservicemin", 35);

		/** Listening to all the actions */
		chatField.addActionListener(this);

		/** Setting status bar */
		// http://stackoverflow.com/questions/3035880/how-can-i-create-a-bar-in-the-bottom-of-a-java-app-like-a-status-bar
		StatusBar statusBar = new StatusBar();
		getContentPane().add(statusBar, java.awt.BorderLayout.SOUTH);

		// Underlining each mnemonic characters by default
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
		Bot = new JMenu("Bot");
		Bot.setMnemonic('B');
		Bot.setBackground(Color.GREEN);

		JMenuItem addBot = new JMenuItem("Add a Bot");
		addBot.setMnemonic('A');
		addBot.addActionListener(this);
		Bot.add(addBot);

		Bot.addSeparator();

		JMenuItem displayBot = new JMenuItem("Display Bots");
		displayBot.addActionListener(this);
		Bot.add(displayBot);
	}

	public void actionPerformed(ActionEvent e) {

		String question = chatField.getText();
		// System.out.println(question);

		if (e.getActionCommand().equals(question)) {
			chatArea.append("Chatter: " + question + "\n");
			// Adding to the arraylist
			chatlog.add("Chatter: " + question + "\n");

			// Bot answers
			try {
				String answer = bot.think(question);
				chatArea.append("Bot: " + answer + "\n");
				chatlog.add("Bot: " + answer + "\n");

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			chatField.setText("");

			// System.out.println(question);
			//System.out.println(chatlog.toString());
		}
	}
}
