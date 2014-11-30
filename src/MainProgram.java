import javax.swing.SwingUtilities;


public class MainProgram {
	public static void main(String[] args) {

		// https://stackoverflow.com/a/15302285
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//
				// Create a new instance of our application and display it.
				//
				new LoginScreen().setVisible(true);
			}
		});
	}
}
