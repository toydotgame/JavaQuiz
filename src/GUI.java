import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* AUTHOR: toydotgame
 * CREATED ON: 2023-02-16
 * GUI class; loads content layouts.
 */

public class GUI {
	// Using a JDialog to hide some of the window controls.
	JDialog frame = new JDialog(new JFrame(), "Year 9 Mathematics Quiz");
	JPanel panel = new JPanel();
	
	public GUI() {
		frame.setSize(1050, 585);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		panel.setLayout(null);
		frame.add(panel);
		
		JLabel label = new JLabel("Year 9 Mathematics Quiz");
		label.setBounds(75, 38, 100, 100);
		label.setBorder(BorderFactory.createLineBorder(DataStorage.borderColor));
		panel.add(label);
		
		frame.setVisible(true);
	}
}
