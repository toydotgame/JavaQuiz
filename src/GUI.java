import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

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
		frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Ignore Swing window close behaviour in favour of the window listener:
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Main.Exit(false);
			}
		});
				
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
		DataStorage.inQuiz = true;
		DataStorage.question = question;
		panel.removeAll();
		
		frame.setTitle("Trigonometry Quiz – Question " + DataStorage.question + " of " + DataStorage.questionAmount);
		
		// Check to see that both question and image exists, otherwise exit:
		try {
			String.valueOf(DataStorage.questionText[DataStorage.question - 1]);
			String.valueOf(DataStorage.questionImages[DataStorage.question - 1]);
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
		
		JLabel image = new JLabel();
		image.setIcon(LoadScaledImage(DataStorage.questionImages[DataStorage.question - 1]));
		image.setBounds(Scale(50, 100, 240, 240));
		image.setBorder(BorderFactory.createLineBorder(DataStorage.imageBorderColor, DataStorage.borderWidth));
		panel.add(image);
		
		JTextArea workingArea = new JTextArea();
		workingArea.setBounds(Scale(310, 100, 100, 240));
		workingArea.setBorder(BorderFactory.createLineBorder(DataStorage.imageBorderColor, DataStorage.borderWidth));
		workingArea.setLineWrap(true);
		panel.add(workingArea);
		
		JRadioButton radio = new JRadioButton();
		radio.setBounds(Scale(466, 136, 20, 20));
		panel.add(radio);
		
		frame.repaint();
	}
	
	public static void End() {
		DataStorage.inQuiz = false;
		
		frame.repaint();
	}
	
	static Rectangle Scale(int x, int y, int width, int height) {
		return new Rectangle((int) Math.round(x * DataStorage.windowScaleFactor), (int) Math.round(y * DataStorage.windowScaleFactor), (int) Math.round(width * DataStorage.windowScaleFactor), (int) Math.round(height * DataStorage.windowScaleFactor));
	}
	
	static ImageIcon LoadScaledImage(String path) {
		ImageIcon icon = new ImageIcon(GUI.class.getResource(path));
		icon = new ImageIcon(icon.getImage().getScaledInstance((int) Math.round(240 * DataStorage.windowScaleFactor), (int) Math.round(240 * DataStorage.windowScaleFactor), Image.SCALE_SMOOTH));
		return icon;
	}
}
