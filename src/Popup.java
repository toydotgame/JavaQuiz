import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Popup {
	JDialog frame = new JDialog(new JFrame(), "Error");
	JPanel panel = new JPanel();
	
	public Popup(String error) {
		frame.setSize((int) Math.round(400 * DataStorage.windowScaleFactor), (int) Math.round(100 * DataStorage.windowScaleFactor) + GUI.yOffset); 
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		panel.setLayout(null);
		frame.add(panel);
		
		JLabel errorText = new JLabel();
		errorText.setBounds(GUI.Scale(20, 20, 360, 60));
		errorText.setVerticalAlignment(SwingConstants.TOP);
		switch(error) { // Contains pseudo-methods for creating each different kind of prompt.
			case "exit":
				errorText.setText("Are you sure you want to quit? All progress will be lost.");
				
				JButton confirmButton = new JButton("Yes");
				confirmButton.setBounds(GUI.Scale(230, 60, 60, 20));
				confirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.Exit();
					}
				});
				confirmButton.setFont(DataStorage.genericText);
				confirmButton.setFocusable(false);
				panel.add(confirmButton);
				
				JButton denyButton = new JButton("No");
				denyButton.setBounds(GUI.Scale(300, 60, 60, 20));
				denyButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
						GUI.frame.requestFocus();
					}
				});
				denyButton.setFont(DataStorage.genericText);
				denyButton.setFocusable(false);
				panel.add(denyButton);
				
				break;
			default:
				errorText.setText("An unknown error occured.");
				
				JButton button = new JButton("Exit");
				button.setBounds(GUI.Scale(300, 60, 60, 20));
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.Exit();
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
