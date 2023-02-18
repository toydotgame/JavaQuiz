import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
	static JLabel description;
	public static JTextArea workingArea;
	public static JRadioButton[] answerRadios;
	
	static int yOffset = frame.getToolkit().getScreenInsets(frame.getGraphicsConfiguration()).bottom;
	
	public GUI(String screen) {
		frame.setSize((int) Math.round(700 * DataStorage.windowScaleFactor), (int) Math.round(390 * DataStorage.windowScaleFactor) + yOffset);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Ignore Swing window close behaviour in favour of the window listener:
		frame.setIconImage(LoadScaledImage(DataStorage.windowIcon, 64).getImage());
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Main.Exit(false);
			}
		});
				
		panel.setLayout(null);
		panel.setFocusable(true);
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
				Popup.Create("");
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
		
		description = new JLabel("<html><p align=\"justify\">" + DataStorage.descriptionText + "</p></html>"); // HTML parsing allows for word wrap.
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
		panel.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Popup.Create("exit");
					return;
				}
				
				for(int i = 0; i < 4; i++) {
					answerRadios[i].setSelected(false);
				}
				switch(e.getKeyCode()) {
					case KeyEvent.VK_1:
						answerRadios[0].setSelected(true);
						System.out.println(0);
						break;
					case KeyEvent.VK_2:
						answerRadios[1].setSelected(true);
						System.out.println(1);
						break;
					case KeyEvent.VK_3:
						answerRadios[2].setSelected(true);
						System.out.println(2);
						break;
					case KeyEvent.VK_4:
						answerRadios[3].setSelected(true);
						System.out.println(3);
						break;
				}
			}
		});
		
		frame.setTitle("Trigonometry Quiz – Question " + DataStorage.question + " of " + DataStorage.questionAmount);
		
		// Check to see that both question, answers, and image exists, otherwise exit:
		try {
			String.valueOf(DataStorage.questionText[DataStorage.question - 1]);
			String.valueOf(DataStorage.questionImages[DataStorage.question - 1]);
			String.valueOf(DataStorage.answers[DataStorage.question - 1][0]);
		} catch(ArrayIndexOutOfBoundsException e) {
			Popup.Create("outOfBounds");
			return; // Prevent running of further commands which may print more stacks.
		}
		
		title = new JLabel(DataStorage.question + ". " + DataStorage.questionText[DataStorage.question - 1]);
		title.setBounds(Scale(50, 25, 600, 50));
		title.setBorder(BorderFactory.createLineBorder(DataStorage.borderColor, DataStorage.borderWidth));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setFont(DataStorage.titleText);
		panel.add(title);
		
		JLabel image = new JLabel();
		image.setIcon(LoadScaledImage(DataStorage.questionImages[DataStorage.question - 1], 240));
		image.setBounds(Scale(50, 100, 240, 240));
		image.setBorder(BorderFactory.createLineBorder(DataStorage.imageBorderColor, DataStorage.borderWidth));
		panel.add(image);
		
		workingArea = new JTextArea();
		workingArea.setBounds(Scale(310, 100, 125, 240));
		workingArea.setBorder(BorderFactory.createLineBorder(DataStorage.imageBorderColor, DataStorage.borderWidth));
		workingArea.setLineWrap(true);
		workingArea.addKeyListener(new KeyAdapter() { // Have to "exit" the "editor" to give focus back to the panel for the close shortcut.
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					panel.requestFocus();
				}
			}
		});
		try {
			workingArea.setText(DataStorage.working[DataStorage.question - 1]);
		} catch(Exception e) {}
		panel.add(workingArea);
		
		ButtonGroup answerRadioGroup = new ButtonGroup();
		answerRadios = new JRadioButton[] {
				new JRadioButton(DataStorage.answers[DataStorage.question - 1][0]),
				new JRadioButton(DataStorage.answers[DataStorage.question - 1][1]),
				new JRadioButton(DataStorage.answers[DataStorage.question - 1][2]),
				new JRadioButton(DataStorage.answers[DataStorage.question - 1][3])
		};
		AddRadioActionListeners();
		for(int i = 0; i < 4; i++) {
			answerRadios[i].setBounds(Scale(466, 136 + (i * 48), 220, 20)); // Default vertical alignment is centre, so the spacing works nicely.
			answerRadios[i].setFont(DataStorage.answerText);
			answerRadioGroup.add(answerRadios[i]);
			answerRadios[i].setFocusable(false);
			answerRadios[i].setIcon(LoadScaledImage(DataStorage.unselIcons[i], 20));
			panel.add(answerRadios[i]);
		}
		try {
			answerRadios[DataStorage.selectedAnswer[DataStorage.question - 1]].setSelected(true);
		} catch(ArrayIndexOutOfBoundsException e) {} // If question hasn't been answered before, the selected radio index will be -1.
		
		JButton backButton = new JButton("← Back");
		backButton.setBounds(Scale(50, 350, 100, 30));
		backButton.setFocusable(false);
		backButton.setFont(DataStorage.buttonText);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.BackQuestion();
			}
		});
		panel.add(backButton);
		if(DataStorage.question < 2) {
			backButton.setVisible(false);
		}
		
		JButton nextButton = new JButton("Next →");
		nextButton.setBounds(Scale(550, 350, 100, 30));
		nextButton.setFocusable(false);
		nextButton.setFont(DataStorage.buttonText);
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.NextQuestion();
			}
		});
		panel.add(nextButton);
		if(DataStorage.question > DataStorage.questionAmount) {
			nextButton.setVisible(false);
		} else if(DataStorage.question == DataStorage.questionAmount) {
			nextButton.setText("Finish");
			nextButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Popup.Create("end");
				}
			});
		}
		
		frame.repaint();
	}
	
	public static void End() {
		DataStorage.inQuiz = false;
		panel.removeAll();
		frame.setTitle("Year 9 Trigonometry Quiz – End");
		
		title = new JLabel("<html>Score: <font color=\"" + DataStorage.gradeColors[DataStorage.gradeID] + "\">"
						 + DataStorage.score + "/" + DataStorage.questionAmount + ", "
						 + DataStorage.percentageScore + "% "
						 + "(" + DataStorage.grade + ")"
						 + "</font></html>");
		title.setBounds(Scale(50, 25, 600, 50));
		title.setBorder(BorderFactory.createLineBorder(DataStorage.borderColor, DataStorage.borderWidth));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setFont(DataStorage.titleText);
		panel.add(title);
		
		description = new JLabel("<html><p align=\"justify\">" + DataStorage.feedback[DataStorage.gradeID] + "</p>"
							   + "<br><br><b>You may now close the window.</b></html>");
		description.setBounds(Scale(75, 95, 550, 195));
		description.setHorizontalAlignment(JLabel.LEFT);
		description.setVerticalAlignment(JLabel.TOP);
		description.setFont(DataStorage.genericText);
		panel.add(description);
		
		frame.repaint();
	}
	
	static Rectangle Scale(int x, int y, int width, int height) {
		return new Rectangle((int) Math.round(x * DataStorage.windowScaleFactor), (int) Math.round(y * DataStorage.windowScaleFactor), (int) Math.round(width * DataStorage.windowScaleFactor), (int) Math.round(height * DataStorage.windowScaleFactor));
	}
	
	static ImageIcon LoadScaledImage(String path, int size) {
		try {
			ImageIcon icon = new ImageIcon(GUI.class.getResource(path));
			icon = new ImageIcon(icon.getImage().getScaledInstance((int) Math.round(size * DataStorage.windowScaleFactor), (int) Math.round(size * DataStorage.windowScaleFactor), Image.SCALE_SMOOTH));
			return icon;
		} catch(Exception e) { // Usually NullPointer.
			return null; // No image exists.
		}
	}
	
	// TODO: Fix this. It is VERY, VERY, VERY bad.
	public static void AddRadioActionListeners() {
		answerRadios[0].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					answerRadios[0].setIcon(LoadScaledImage(DataStorage.selIcons[0], 20));
					return;
				}
				answerRadios[0].setIcon(LoadScaledImage(DataStorage.unselIcons[0], 20));
			}
		});
		answerRadios[1].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					answerRadios[1].setIcon(LoadScaledImage(DataStorage.selIcons[1], 20));
					return;
				}
				answerRadios[1].setIcon(LoadScaledImage(DataStorage.unselIcons[1], 20));
			}
		});
		answerRadios[2].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					answerRadios[2].setIcon(LoadScaledImage(DataStorage.selIcons[2], 20));
					return;
				}
				answerRadios[2].setIcon(LoadScaledImage(DataStorage.unselIcons[2], 20));
			}
		});
		answerRadios[3].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					answerRadios[3].setIcon(LoadScaledImage(DataStorage.selIcons[3], 20));
					return;
				}
				answerRadios[3].setIcon(LoadScaledImage(DataStorage.unselIcons[3], 20));
			}
		});
	}
}
