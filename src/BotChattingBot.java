import chatter.CleverBotSession;

import javax.swing.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class BotChattingBot extends JFrame
		implements
			ActionListener,
			Serializable {

	JTextArea chatArea;
	// JTextField chatField;
	JMenu file;
	JMenu Bot;
	JButton askButton;
	JButton replyButton;

	CleverBotSession bot;

	List<String> chatlog;

	public BotChattingBot() {
		/** Frame properties */
		super("Supreme Bot");
		setSize(700, 500);
		setResizable(false); // best to keep the frame not resizable
		setLocationRelativeTo(null); // window in the centre
		setDefaultCloseOperation(EXIT_ON_CLOSE); // close when exits

		/** Use the default metal styled titlebar - for Windows */
		setUndecorated(false); // false for mac
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		System.setProperty("apple.laf.useScreenMenuBar", "true"); // Mac styled
		// menubar

		/** Creating menu */
		createFile();
		createBot();

		/** Setting menu bar */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.setBackground(Color.YELLOW);
		menuBar.add(file);
		menuBar.add(Bot);

		/** Get the chat interface */
		chatInterface();
		chatlog = new ArrayList<String>(); // Save chat history

		/** Adding bot */
		bot = new CleverBotSession("http://www.cleverbot.com/webservicemin", 35);

		/** Listening to all the actions */
		askButton.addActionListener(this);
		replyButton.addActionListener(this);

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

	private void chatInterface() {
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setLineWrap(true);
		add(new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);

		Box box = Box.createHorizontalBox();
		add(box, BorderLayout.SOUTH);
		// chatField = new JTextField();
		// chatField.requestFocus();
		askButton = new JButton("Generate Converastion");
		replyButton = new JButton("Reply to the Convo");
		box.add(askButton);
		box.add(replyButton);

		/** Adding status bar */
		// To-Do
	}

	public void actionPerformed(ActionEvent e) {

		String question = "Hey";
		// System.out.println(question);

		if (e.getSource() == askButton) {
			try {
				question = bot.think(question);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			chatArea.append("You: " + question + "\n");
			// Adding to the arraylist
			chatlog.add("You: " + question + "\n");
			// chatField.setText(""); // set the chatField to empty

			if (e.getSource() == replyButton) {
				try {
					String answer = bot.think(question);
					chatArea.append("Bot: " + answer + "\n");
					chatlog.add("Bot: " + answer + "\n");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} // end of replying to the bot
		} // end of asking the bot

	} // end of actionPerformed method

}
