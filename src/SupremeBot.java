import chatter.*;

import javax.swing.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class SupremeBot extends JFrame implements ActionListener, Serializable {

	/**
	 * Eclipse generated the serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea chatArea;
	private JTextField chatField;
	private JMenu file;
	private JButton sendButton;
	private String menuName;
	private ChatterBotFactory factory;
	private ChatterBot bot;
    private ChatterBotSession bot1session;

	private List<String> chatlog;

	public SupremeBot() {
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

		/** Adding bot */
		factory = new ChatterBotFactory();
		
		try {
			bot = factory.create(ChatterBotType.CLEVERBOT);
		} catch (Exception e) {
			System.out.println("Could not establish a session");
		};
		
	    bot1session = bot.createSession();

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
					String answer = bot1session.think(question);
					chatArea.append("Bot: " + answer + "\n");
					chatlog.add("Bot: " + answer + "\n");

				} catch (Exception event) {

					event.printStackTrace();
				}

				saveFile(); // save the chat on every conversation iteration
				chatField.setText(""); // set the chatField to empty
			} // end of input validation
			else
				JOptionPane.showMessageDialog(null, "Please enter something!");
		
		} // end of chatField and sendButton listener

		/** Dealing with menu */
		menuName = e.getActionCommand();
		menuActions();

	} // end of actionPerformed method

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
			// a safer approach to deal with swing objects is to use
			// SwingUtilities
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

		// chatArea.setText(readFile());

		Box box = Box.createHorizontalBox();
		add(box, BorderLayout.SOUTH);
		chatField = new JTextField();
		chatField.requestFocus();
		sendButton = new JButton("Send");
		box.add(chatField);
		box.add(sendButton);
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

			File file = new File("history.txt");

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
	private String readFile() {
		String content = null;
		File file = new File("history.txt"); // for ex foo.txt
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

	private boolean fileExist() {
		File file = new File("history.txt");

		return file.exists();
	}
}
