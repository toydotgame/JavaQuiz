import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* AUTHOR: toydotgame
 * CREATED ON: 2023-02-16
 * GUI loader.
 * Warnings: Cannot handle `windowScaleFactor`s larger than 2³¹.
 */

public class GUI {
	// Using a JDialog to hide some of the window controls.
	JDialog frame = new JDialog(new JFrame(), "Year 9 Mathematics Quiz");
	JPanel panel = new JPanel();
	JLabel title;
	JLabel description;
	
	double scaleFactor = DataStorage.windowScaleFactor;
	
	public GUI() {
		frame.setSize((int) Math.round(700 * scaleFactor), (int) Math.round(390 * scaleFactor));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		panel.setLayout(null);
		frame.add(panel);
		
		title = new JLabel("Year 9 Mathematics Quiz");
		title.setBounds((int) Math.round(50 * scaleFactor), (int) Math.round(25 * scaleFactor), (int) Math.round(600 * scaleFactor), (int) Math.round(50 * scaleFactor));
		title.setBorder(BorderFactory.createLineBorder(DataStorage.borderColor));
		panel.add(title);
		
		description = new JLabel(DataStorage.descriptionText);
		description.setBounds((int) Math.round(75 * scaleFactor), (int) Math.round(95 * scaleFactor), (int) Math.round(550 * scaleFactor), (int) Math.round(90 * scaleFactor));
		description.setBorder(BorderFactory.createLineBorder(DataStorage.borderColor));
		panel.add(description);
		
		frame.setVisible(true);
	}
	
	
}
