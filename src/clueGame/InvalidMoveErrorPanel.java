package clueGame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InvalidMoveErrorPanel {

JFrame frame;
	
	public InvalidMoveErrorPanel() {
		frame= new JFrame();
		JOptionPane.showMessageDialog(frame,"You can't move there.","Alert",JOptionPane.WARNING_MESSAGE);
	}
	
}
