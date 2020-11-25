package clueGame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ErrorPanel {
	JFrame frame;
	public ErrorPanel(String errorMessage) {
		frame= new JFrame();
		JOptionPane.showMessageDialog(frame,errorMessage,"Alert",JOptionPane.WARNING_MESSAGE);
	}
}
