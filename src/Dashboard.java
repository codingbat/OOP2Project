import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;


public class Dashboard extends JFrame implements GUICreation, ActionListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenu fileMenu;
	JMenu botMenu;
	JButton dumbBot;
	JButton botVersusBot;
	JButton supremeBot;
	JLabel choose;

	public Dashboard() {
		frameCreation();
		createFile();
		createBot();
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

		/** Underlining each mnemonic characters by default */
		UIManager.getDefaults().put("Button.showMnemonics", Boolean.TRUE);
	}



	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dumbBot) {
			// a safer approach to deal with swing objects is to use SwingUtilities
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new LoginScreen().setVisible(true); // go back to the login screen
				}
			});
			this.setVisible(false); // hide the dashboard screen

		} else if (e.getSource() == botVersusBot) {
			// a safer approach to deal with swing objects is to use SwingUtilities
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new BotChattingBot().setVisible(true); // open BotChattingBot session
				}
			});
			this.setVisible(false); // hide the dashboard screen

		} else if (e.getSource() == supremeBot) {
			// a safer approach to deal with swing objects is to use SwingUtilities
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new SupremeBot().setVisible(true); // open SupremeBot session
				}
			});
			this.setVisible(false); // hide the dashboard screen
		}

	} //end of actionPerformed()


	public void frameCreation() {
		/** Frame properties */
		setTitle("Supreme Bot: Dashboard");
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

}
