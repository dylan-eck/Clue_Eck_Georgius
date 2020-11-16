package clueGame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PlayerHasNotGoneErrorPanel {

JFrame frame;
	
	public PlayerHasNotGoneErrorPanel() {
		frame= new JFrame();
		JOptionPane.showMessageDialog(frame,"Player has not done anything.","Alert",JOptionPane.WARNING_MESSAGE);
	}
	
	public static void main(String[] args) throws BadConfigFormatException{
		new PlayerHasNotGoneErrorPanel();
	}
}
