import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
	
	Insets inset = frame.getToolkit().getScreenInsets(frame.getGraphicsConfiguration());
	int yOffset = inset.bottom;
	
	public GUI(String screen) {
		frame.setSize((int) Math.round(700 * DataStorage.windowScaleFactor), (int) Math.round(390 * DataStorage.windowScaleFactor) + yOffset);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
				
		panel.setLayout(null);
		frame.add(panel);
		
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
				Popup("exit");
				break;
		}
		
		frame.setVisible(true);
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
	
	void Popup(String error) {
		JDialog popupFrame = new JDialog(new JFrame(), "Popup");
		popupFrame.setSize((int) Math.round(400 * DataStorage.windowScaleFactor), (int) Math.round(100 * DataStorage.windowScaleFactor) + yOffset); 
		popupFrame.setResizable(false);
		popupFrame.setLocationRelativeTo(null);
		
		JPanel popupPanel = new JPanel();
		popupPanel.setLayout(null);
		popupFrame.add(popupPanel);
		
		JLabel errorText = new JLabel();
		errorText.setBounds(Scale(20, 20, 360, 60));
		errorText.setVerticalAlignment(SwingConstants.TOP);
		switch(error) {
			case "exit":
				errorText.setText("Are you sure you want to quit? All progress will be lost.");
				
				JButton confirmButton = new JButton("Yes");
				confirmButton.setBounds(Scale(230, 60, 60, 20));
				confirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.Exit();
					}
				});
				confirmButton.setFont(DataStorage.genericText);
				confirmButton.setFocusable(false);
				popupPanel.add(confirmButton);
				break;
			default:
				errorText.setText("An unknown error occured.");
				
				JButton button = new JButton("Exit");
				button.setBounds(Scale(300, 60, 60, 20));
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.Exit();
					}
				});
				button.setFont(DataStorage.genericText);
				button.setFocusable(false); // Remove the tab index box thing from around the "Exit" text.
				popupPanel.add(button);
				break;
		}
		errorText.setFont(DataStorage.genericText);
		popupPanel.add(errorText);
	}
	
	Rectangle Scale(int x, int y, int width, int height) {
		return new Rectangle((int) Math.round(x * DataStorage.windowScaleFactor), (int) Math.round(y * DataStorage.windowScaleFactor), (int) Math.round(width * DataStorage.windowScaleFactor), (int) Math.round(height * DataStorage.windowScaleFactor));
	}
}
