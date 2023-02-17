import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
	static JDialog frame = new JDialog(new JFrame(), "Year 9 Trigonometry Quiz");
	static JPanel panel = new JPanel();
	static JLabel title;
	
	static int yOffset = frame.getToolkit().getScreenInsets(frame.getGraphicsConfiguration()).bottom;
	
	public GUI(String screen) {
		frame.setSize((int) Math.round(700 * DataStorage.windowScaleFactor), (int) Math.round(390 * DataStorage.windowScaleFactor) + yOffset);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
				
		panel.setLayout(null);
		panel.setFocusable(true);
		panel.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					new Popup("exit");
				}
			}
		});
		frame.add(panel);
		
		frame.setVisible(true); // Frame created before drawing content due to popup drawing issues.
		
		// Switch case only for debug purposes. In practice only loads main.
		switch(screen) {
			case "main":
				Main();
				break;
			case "question":
				Question(DataStorage.question);
				break;
			case "end":
				End();
				break;
			default:
				new Popup("");
				break;
		}
	}
	
	public static void Main() {
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		title = new JLabel("Year 9 Mathematics Quiz");
		title.setBounds(Scale(50, 25, 600, 50));
		title.setBorder(BorderFactory.createLineBorder(DataStorage.borderColor, DataStorage.borderWidth));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setFont(DataStorage.titleText);
		panel.add(title);
		
		JLabel description = new JLabel("<html><p align=\"justify\">" + DataStorage.descriptionText + "</p></html>"); // HTML parsing allows for word wrap.
		description.setBounds(Scale(75, 95, 550, 195));
		description.setHorizontalAlignment(JLabel.LEFT);
		description.setVerticalAlignment(JLabel.TOP);
		description.setFont(DataStorage.genericText);
		panel.add(description);
		
		JButton startButton = new JButton("Start");
		startButton.setBounds(Scale(250, 310, 200, 60));
		startButton.setFocusable(false);
		startButton.setFont(DataStorage.buttonText);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.StartQuiz();
			}
		});
		panel.add(startButton);
		
		frame.repaint(); // Redraw because the frame had been set visible prior to the elements being drawn.
	}
	
	public static void Question(int question) {
		DataStorage.question = question;
		panel.removeAll();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frame.setTitle("Trigonometry Quiz – Question " + DataStorage.question + " of " + DataStorage.questionAmount);
		
		try {
			String.valueOf(DataStorage.questionText[DataStorage.question - 1]);
		} catch(ArrayIndexOutOfBoundsException e) {
			new Popup("outOfBounds");
		}
		
		title = new JLabel(DataStorage.question + ". " + DataStorage.questionText[DataStorage.question - 1]);
		title.setBounds(Scale(50, 25, 600, 50));
		title.setBorder(BorderFactory.createLineBorder(DataStorage.borderColor, DataStorage.borderWidth));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setFont(DataStorage.titleText);
		panel.add(title);
		
		frame.repaint();
	}
	
	void End() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frame.repaint();
	}
	
	static Rectangle Scale(int x, int y, int width, int height) {
		return new Rectangle((int) Math.round(x * DataStorage.windowScaleFactor), (int) Math.round(y * DataStorage.windowScaleFactor), (int) Math.round(width * DataStorage.windowScaleFactor), (int) Math.round(height * DataStorage.windowScaleFactor));
	}
}
