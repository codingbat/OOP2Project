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
	JButton sendButton;

	CleverBotSession bot;

	List<String> chatlog;

	public SupremeBot() {
		/** Frame properties */
		super("Supreme Bot");
		setSize(700, 500);
		setResizable(false); // best to keep the frame not resizable
		setLocationRelativeTo(null); // window in the centre
		setDefaultCloseOperation(EXIT_ON_CLOSE); // close when exits

		/** Use the default metal styled titlebar - for Windows */
		setUndecorated(true); // false for mac
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
		chatField.addActionListener(this);
		sendButton.addActionListener(this);

		/** Listening to window events */
		this.addWindowListener(new WindowAdapter() {
			// Requesting focus to chat field
			public void windowOpened(WindowEvent event) {
				chatField.requestFocus();
			}
		});

		/** Underlining each mnemonic characters by default */
		UIManager.getDefaults().put("Button.showMnemonics", Boolean.TRUE);

	}

	// Creating file menu
	private void createFile() {
		file = new JMenu("File");
		file.setMnemonic('F');
		file.setBackground(Color.red);

		JMenuItem load = new JMenuItem("Load previous conversation");
		load.addActionListener(this);
		load.setForeground(Color.RED);

		file.add(load);
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

		// chatArea.setText(readFile());

		Box box = Box.createHorizontalBox();
		add(box, BorderLayout.SOUTH);
		chatField = new JTextField();
		chatField.requestFocus();
		sendButton = new JButton("Send");
		box.add(chatField);
		box.add(sendButton);

		/** Adding status bar */
		// To-Do
	}

	public void actionPerformed(ActionEvent e) {

		String question = chatField.getText();
		// System.out.println(question);

		if (e.getActionCommand().equals(question)
				|| e.getSource() == sendButton) {

			// Quit if !quit is typed
			if (e.getActionCommand().equals("!quit"))
				System.exit(0);

			// Validate if question is not empty and white spaces length is > 0
			if (question != null && question.trim().length() > 0) {
				chatArea.append("You: " + question + "\n");
				// Adding to the arraylist
				chatlog.add("You: " + question + "\n");

				// Bot answers
				try {
					String answer = bot.think(question);
					chatArea.append("Bot: " + answer + "\n");
					chatlog.add("Bot: " + answer + "\n");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				saveFile(); // save the chat on every conversation iteration
				chatField.setText(""); // set the chatField to empty
			} // end of input validation

		} // end of chatField and sendButton listener

		if (e.getActionCommand().equals("Load previous conversation")) {
			chatArea.setText(readFile());
		}

	} // end of actionPerformed method

	/** Saving and Reading methods */
	/** http://bit.ly/196RjvC */
	public void saveFile() {
		try {

			File file = new File("test.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			for (String i : chatlog) {
				bw.write(i.toString());
			}
			// bw.write(chatlog.toString());

			bw.close();

			// System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** http://stackoverflow.com/a/10710115 */
	public String readFile() {
		String content = null;
		File file = new File("test.txt"); // for ex foo.txt
		try {
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

}
