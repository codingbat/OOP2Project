import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;


public class Dashboard extends JFrame implements GUICreation, ActionListener {


	/**
	 * Eclipse generated the serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private JMenu fileMenu;
	private JButton dumbBot;
	private JButton botVersusBot;
	private JButton supremeBot;
	private JLabel choose;
	private String menuName;
	
	public Dashboard() {
		frameCreation();
		createFile();
		menuBar();
		setLayout(null);

		dumbBot = new JButton("Dumb Bot");
		botVersusBot = new JButton("Bot vs Bot");
		supremeBot = new JButton("Supreme Bot");
		choose = new JLabel("Please choose one: ");
		choose.setFont(new Font("monospaced", Font.BOLD, 24));

		choose.setBounds(130, 120, 400, 60);
		dumbBot.setBounds(130, 190, 120, 60);
		botVersusBot.setBounds(280, 190, 120, 60);
		supremeBot.setBounds(430, 190, 120, 60);

		this.add(choose);
		this.add(dumbBot);
		this.add(botVersusBot);
		this.add(supremeBot);

		/** Listening to the buttons */
		dumbBot.addActionListener(this);
		botVersusBot.addActionListener(this);
		supremeBot.addActionListener(this);

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
		if (e.getSource() == dumbBot) {
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
					new DumbBot().setVisible(true); // go back to the login screen
				}
			});
			this.setVisible(false); // hide the dashboard screen

		} else if (e.getSource() == botVersusBot) {
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
					new BotChattingBot().setVisible(true); // open BotChattingBot session
				}
			});
			this.setVisible(false); // hide the dashboard screen

		} else if (e.getSource() == supremeBot) {
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
					new SupremeBot().setVisible(true); // open SupremeBot session
				}
			});
			this.setVisible(false); // hide the dashboard screen
		}
		
		/** Dealing with menu */
		menuName = e.getActionCommand();
		menuActions();

	} //end of actionPerformed()


	public void frameCreation() {
		/** Frame properties */
		setTitle("Supreme Bot: Dashboard");
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

	}


	public void menuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.setBackground(Color.YELLOW);
		menuBar.add(fileMenu);
	}


	// Creating fileMenu menu
	private void createFile() {
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		fileMenu.setBackground(Color.red);

		JMenuItem logout = new JMenuItem("Logout");
		logout.addActionListener(this);

		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		quit.setForeground(Color.RED);

		fileMenu.add(logout);

		fileMenu.addSeparator();
		fileMenu.add(quit);
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


		} // end of switch
	} //end of menuActions()
}
