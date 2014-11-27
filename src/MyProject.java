import javax.swing.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import chatter.*;

public class MyProject extends JFrame implements ActionListener, Serializable {
	
	JTextArea chatArea;
	JTextField chatField;
	JMenu file;
	JMenu Bot;
	JButton send;
	
	CleverBotSession bot;
	
	public MyProject() {
		
		super("My Project");
		setSize(700, 500);
		//Set the window in the center
		setLocationRelativeTo(null);
		/** Use the default metal styled titlebar */
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);   
        //close on exit
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//set the default layout
		Container cpane = getContentPane();
		cpane.setLayout(new FlowLayout());
		
		//set the ChatArea layout
		Container text = getContentPane();
		text.setLayout(new GridLayout());
		
		//creating menus
		createFile();
		createBot();
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		
		/** Setting menu bar */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.setBackground(Color.YELLOW);
		menuBar.add(file);
		menuBar.add(Bot);
		
		//Chat history areay
		chatArea = new JTextArea();
		text.add(chatArea);
		
		//Chat sending field
		chatField = new JTextField(5);
		text.add(chatField);
		
		//Adding my bot
		bot = new CleverBotSession("http://www.cleverbot.com/webservicemin", 35);
		
		
		
		//Adding actionlistener
		chatField.addActionListener(this);
		
		//Setting status bar
		//http://stackoverflow.com/questions/3035880/how-can-i-create-a-bar-in-the-bottom-of-a-java-app-like-a-status-bar
		StatusBar statusBar = new StatusBar();
		getContentPane().add(statusBar, java.awt.BorderLayout.EAST);
		
		//pack();
		//Underlining each mnemonic characters by default
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
		//System.out.println(question);
		
		if (e.getActionCommand().equals(question)) {
			chatArea.append(question + "\n");
			
			//Bot answers
			try {
				String answer = bot.think(question);
				chatArea.append(answer + "\n");
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			chatField.setText("");
			
			//System.out.println(question);
		}
	}
}
