import java.awt.Insets;
import java.awt.Rectangle;

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
	static JDialog frame = new JDialog(new JFrame(), "Year 9 Mathematics Quiz");
	JPanel panel = new JPanel();
	JLabel title;
	JLabel description;
	
	static Insets inset = frame.getToolkit().getScreenInsets(frame.getGraphicsConfiguration());
	static int yOffset = inset.bottom;
	
	public GUI(String screen) {
		frame.setSize((int) Math.round(700 * DataStorage.windowScaleFactor), (int) Math.round(390 * DataStorage.windowScaleFactor) + yOffset);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
				
		panel.setLayout(null);
		frame.add(panel);
		
		frame.setVisible(true); // Frame created before drawing content due to popup drawing issues.
		
		switch(screen) {
			case "main":
				Main();
				break;
			case "question":
				Question();
				break;
			case "end":
				End();
				break;
			default:
				new Popup("exit");
				break;
		}
	}
	
	void Main() {
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		title = new JLabel("Year 9 Mathematics Quiz");
		title.setBounds(Scale(50, 25, 600, 50));
		title.setBorder(BorderFactory.createLineBorder(DataStorage.borderColor, DataStorage.borderWidth));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setFont(DataStorage.titleText);
		panel.add(title);
		
		description = new JLabel("<html><p align=\"justify\">" + DataStorage.descriptionText + "</p></html>"); // HTML parsing allows for word wrap.
		description.setBounds(Scale(75, 95, 550, 195));
		description.setHorizontalAlignment(JLabel.LEFT);
		description.setVerticalAlignment(JLabel.TOP);
		description.setFont(DataStorage.genericText);
		panel.add(description);
	}
	
	void Question() {
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	void End() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	static Rectangle Scale(int x, int y, int width, int height) {
		return new Rectangle((int) Math.round(x * DataStorage.windowScaleFactor), (int) Math.round(y * DataStorage.windowScaleFactor), (int) Math.round(width * DataStorage.windowScaleFactor), (int) Math.round(height * DataStorage.windowScaleFactor));
	}
}
