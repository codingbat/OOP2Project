import javax.swing.SwingUtilities;


public class MainProgram {
	public static void main(String[] args) {

		// https://stackoverflow.com/a/15302285
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				new LoginScreen().setVisible(true);
				
			}
		});
	}
}
