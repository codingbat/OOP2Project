import chatter.CleverBotSession;

import javax.swing.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class BotChattingBot extends JFrame implements ActionListener,
Serializable {

	/**
	 * Eclipse generated the serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea chatArea;
	private JMenu file;
	private JButton askButton;
	private String menuName;
	private CleverBotSession bot;

	private List<String> chatlog;

	public BotChattingBot() {
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
		setUndecorated(true); 
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
		bot = new CleverBotSession("http://www.cleverbot.com/webservicemin", 35);

		/** Listening to all the actions */
		askButton.addActionListener(this);

		
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

	// Creating file menu
	private void createFile() {
		file = new JMenu("File");
		file.setMnemonic('F');
		file.setBackground(Color.red);

		JMenuItem logout = new JMenuItem("Logout");
		logout.addActionListener(this);

		JMenuItem back = new JMenuItem("<< Go Back");
		back.addActionListener(this);

		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		quit.setForeground(Color.RED);

		file.add(logout);
		file.add(back);
		file.addSeparator();
		file.add(quit);

	}

	private void chatInterface() {
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setLineWrap(true);
		add(new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);

		Box box = Box.createHorizontalBox();
		add(box, BorderLayout.SOUTH);
		askButton = new JButton("Click to Begin Bot vs Bot Conversation");
		box.add(askButton);


	}

	public void actionPerformed(ActionEvent e) {

		String question = "Hey";
		// System.out.println(question);

		if (e.getSource() == askButton) {
			try {
				question = bot.think(question);
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			chatArea.append("Computer 1: " + question + "\n");
			// Adding to the arraylist
			chatlog.add("Computer 1: " + question + "\n");

			//System.out.println(chatlog.get(chatlog.size()-1));

			try {
				String answer = bot.think(question);
				chatArea.append("Computer 2: " + answer + "\n");
				chatlog.add("Computer 2: " + answer + "\n");

			} catch (Exception e1) {

				e1.printStackTrace();
			}


			askButton.setText("Keep chatting to the computer");

		} // end of asking the bot

		/** Dealing with menu */
		menuName = e.getActionCommand();
		menuActions();

	} // end of actionPerformed method

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
						new LoginScreen().setVisible(true); // go back to the login
						// screen
					}
				});
				this.setVisible(false);
			}
			break;

		case "Quit":
			System.exit(0);
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
						new Dashboard().setVisible(true); // go back to the login
						// screen
					}
				});
				this.setVisible(false);
			}
			break;

		} // end of switch
	} //end of menuActions()
}
