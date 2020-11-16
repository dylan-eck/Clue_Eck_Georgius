package clueGame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PlayerHasGoneErrorPanel {

JFrame frame;
	
	public PlayerHasGoneErrorPanel() {
		frame= new JFrame();
		JOptionPane.showMessageDialog(frame,"Player has already made a move.","Alert",JOptionPane.WARNING_MESSAGE);
	}
	
	public static void main(String[] args) throws BadConfigFormatException{
		new PlayerHasGoneErrorPanel();
	}
}
