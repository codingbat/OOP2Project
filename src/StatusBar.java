import java.awt.Dimension;
import javax.swing.*;

public class StatusBar extends JLabel {
	
	public StatusBar() {
		super();
		super.setPreferredSize(new Dimension(100, 16));
		setMessage("Ready to be Botified");
	}
	
	public void setMessage(String message) {
		setText(" " + message);
	}
}
