import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Popup implements Runnable {
	static JDialog frame = new JDialog(new JFrame(), "Fatal Error");
	static JPanel panel = new JPanel();
	static JButton button;
	
	public static void Create(String error) {
		DataStorage.error = error;
		Main.executor.execute(Main.popupThread);
	}
	
	public void run() {
		frame.setSize((int) Math.round(400 * DataStorage.windowScaleFactor), (int) Math.round(100 * DataStorage.windowScaleFactor) + GUI.yOffset); 
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		panel.setLayout(null);
		frame.add(panel);
		
		JLabel errorText = new JLabel();
		errorText.setBounds(GUI.Scale(20, 20, 360, 60));
		errorText.setVerticalAlignment(SwingConstants.TOP);
		switch(DataStorage.error) { // Contains pseudo-methods for creating each different kind of prompt.
			case "exit":
				frame.setTitle("Confirmation Dialog");
				errorText.setText("Are you sure you want to quit? All progress will be lost.");
				
				JButton confirmButton = new JButton("Yes");
				confirmButton.setBounds(GUI.Scale(230, 60, 60, 20));
				confirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.Exit(true);
					}
				});
				confirmButton.setFont(DataStorage.genericText);
				confirmButton.setFocusable(false);
				panel.add(confirmButton);
				
				button = new JButton("No");
				button.setBounds(GUI.Scale(300, 60, 60, 20));
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
						GUI.panel.requestFocus();
					}
				});
				button.setFont(DataStorage.genericText);
				button.setFocusable(false);
				panel.add(button);
				
				break;
			case "outOfBounds":
				errorText.setText("Out of Bounds Exception: This question does not exist.");

				button = new JButton("Exit");
				button.setBounds(GUI.Scale(300, 60, 60, 20));
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.Exit(true);
					}
				});
				button.setFont(DataStorage.genericText);
				button.setFocusable(false); // Remove the tab index box thing from around the "Exit" text.
				panel.add(button);
				break;
			default:
				errorText.setText("An unknown error occured.");
				
				button = new JButton("Exit");
				button.setBounds(GUI.Scale(300, 60, 60, 20));
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.Exit(true);
					}
				});
				button.setFont(DataStorage.genericText);
				button.setFocusable(false); // Remove the tab index box thing from around the "Exit" text.
				panel.add(button);
				break;
		}
		errorText.setFont(DataStorage.genericText);
		panel.add(errorText);
		
		frame.toFront();
		frame.setVisible(true);
	}
}
