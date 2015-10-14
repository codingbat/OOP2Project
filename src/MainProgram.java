import javax.swing.SwingUtilities;


public class MainProgram {
	public static void main(String[] args) {

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
				
				new LoginScreen().setVisible(true);
				
			}
		});
	}
}
