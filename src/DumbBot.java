import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class DumbBot extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextArea chatArea;
	private JTextField chatField;
	private JMenu file;
	private JButton sendButton;
	private String menuName;

	private List<String> chatlog;

	private String[][] chatBot = {
			// standard greetings
			{ "hi", "hello", "hola", "ola", "howdy" },
			{ "hi", "hello", "hey" },
			// question greetings
			{ "how are you", "how r you", "how r u", "how are u" },
			{ "good", "doing well", "yerrah, i'm grand" },
			// yes
			{ "yes" },
			{ "no", "Nada", "Neva Eva!" },
			// thanks
			{ "thanks", "thank you"},
			{ "You are welcome!", "Welcome!", "No probs!" },
			
			// default
			{ "I love talking to you!", "This is amazing :)", "Hmm", "Let me think...",
			"(Unavailable to chat, ha!)" } };

	public DumbBot() {
		/** Frame properties */
		super("Supreme Bot");
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
		//setUndecorated(true); // false for mac
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

		/** Creating menu */
		createFile();

		/** Setting menu bar */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.setBackground(Color.YELLOW);
		menuBar.add(file);

		/** Get the chat interface */
		chatInterface();
		chatlog = new ArrayList<String>(); // Save chat history


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

	public void actionPerformed(ActionEvent e) {
		String question = chatField.getText();
		if (e.getSource() == sendButton || e.getActionCommand().equals(question)) {
			if (question != null && question.trim().length() > 0) {
				chatArea.append("You asked: " + question + "\n");
				chatlog.add("You asked: " + question + "\n");
				chatField.setText("");
				
				/*****************************************************
				*    Title: Chatbot delay between saying something and getting answer
				*    Author: user3231227
				*    Site owner/sponsor: stackoverflow.com
				*    Date: 2014
				*    Code version:  asked Jan 26 at 17:48 
				*    Availability: https://stackoverflow.com/q/21366986 (Accessed 07 December 2014)
				*    Modified:  used the algorithm and applied my own codes
				*****************************************************/
				
				int response = 0;
				/*
				 * 0: we're searching through chatBot[][] for matches 
				 * 1: we didn't find anything
				 * 2: we did find something
				 */
				// -----check for matches----
				int j = 0;// which group we're checking
				String answer;
				while (response == 0) {
					if (inArray(question.toLowerCase(), chatBot[j * 2])) {
						response = 2; // found something
						//get random answer
						int r = (int) Math.floor(Math.random()
								* chatBot[(j * 2) + 1].length);
						answer = chatBot[(j * 2) + 1][r];
						chatArea.append("Dumb replies: " + answer + "\n");
						chatlog.add("Dumb replies: " + answer + "\n");
					}
					j++;
					if (j * 2 == chatBot.length - 1 && response == 0) {
						response = 1;
					}
				}

				// -----default--------------
				if (response == 1) {
					int r = (int) Math.floor(Math.random()
							* chatBot[chatBot.length - 1].length);
					answer = chatBot[chatBot.length - 1][r];
					chatArea.append("Dumb replies: \t" + answer + "\n");
					chatlog.add("Dumb replies: \t" + answer + "\n");
				}
				saveFile();

			} else
				JOptionPane.showMessageDialog(null, "Please enter something!");


		} // end of chatField 

		/** Dealing with menu */
		menuName = e.getActionCommand();
		menuActions();
	}

	private boolean inArray(String in, String[] str) {
		boolean match = false;
		for (int i = 0; i < str.length; i++) {
			if (str[i].equals(in)) {
				match = true;
			}
		}
		return match;
	}

	// Creating file menu
	private void createFile() {
		file = new JMenu("File");
		file.setMnemonic('F');
		file.setBackground(Color.red);

		JMenuItem load = new JMenuItem("Load previous conversation");
		load.addActionListener(this);

		JMenuItem logout = new JMenuItem("Logout");
		logout.addActionListener(this);

		JMenuItem back = new JMenuItem("<< Go Back");
		back.addActionListener(this);

		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		quit.setForeground(Color.RED);

		file.add(load);
		file.add(logout);
		file.add(back);
		file.addSeparator();
		file.add(quit);
	}

	private void menuActions() {

		switch (menuName) {
		case "Logout":
			int option = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to logout?", "Confirm",
					JOptionPane.YES_NO_OPTION);

			if (option == JOptionPane.YES_OPTION) {
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
						new LoginScreen().setVisible(true); // go back to the
						// login
						// screen
					}
				});
				this.setVisible(false);
			}
			break;

		case "Quit":
			System.exit(0);
			break;

		case "Load previous conversation":
			if (fileExist())
				chatArea.setText(readFile());
			else
				JOptionPane.showMessageDialog(null, "No history found!");
			break;

		case "<< Go Back":
			int bck = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to go back?", "Confirm",
					JOptionPane.YES_NO_OPTION);

			if (bck == JOptionPane.YES_OPTION) {
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
						new Dashboard().setVisible(true); // go back to the
						// login
						// screen
					}
				});
				this.setVisible(false);
			}
			break;

		} // end of switch
	} // end of menuActions()

	private void chatInterface() {
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setLineWrap(true);
		add(new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);

		Box box = Box.createHorizontalBox();
		add(box, BorderLayout.SOUTH);
		chatField = new JTextField();
		chatField.requestFocus();
		sendButton = new JButton("Send");
		box.add(chatField);
		box.add(sendButton);

	}

	private boolean fileExist() {
		File file = new File("dumbhistory.txt");

		return file.exists();
	}

	/*****************************************************
	*    Title: How to write to file in Java � BufferedWriter
	*    Author: mkyong
	*    Site owner/sponsor: http://www.mkyong.com
	*    Date: 2013
	*    Code version: Posted on June 2, 2010 ,     Last modified : August 29, 2012 
	*    Availability: http://bit.ly/196RjvC (Accessed 07 December 2014)
	*    Modified:  modified to suit with my program
	*****************************************************/
	
	/** Saving and Reading methods */
	private void saveFile() {
		try {

			File file = new File("dumbhistory.txt");

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

	/*****************************************************
	*    Title: Best way to read a text file
	*    Author: Grimy
	*    Site owner/sponsor: http://stackoverflow.com
	*    Date: 2013
	*    Code version: edited Aug 11 '13 at 17:15
	*    Availability: http://stackoverflow.com/a/10710115 (Accessed 07 December 2014)
	*    Modified:  modified to suit with my program
	*****************************************************/
	
	/** Read file */
	private String readFile() {
		String content = null;
		File file = new File("dumbhistory.txt"); // for ex foo.txt
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