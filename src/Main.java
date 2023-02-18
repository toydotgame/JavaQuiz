import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/* AUTHOR: toydotgame
 * CREATED ON: 2023-02-16
 * Main class that handles GUI events and logic.
 */

public class Main extends Thread {
	static ExecutorService executor = Executors.newFixedThreadPool(1);
	static Runnable popupThread = new Popup();
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(DataStorage.theme);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			Popup.Create("");
			return;
		}		
		Popup.Init();
		Arrays.fill(DataStorage.selectedAnswer, -1);
		
		new GUI("main"); // Launches EDT thread (use for GUI only).
	}
	
	public static void StartQuiz() {
		DataStorage.question = 1;
		GUI.Question(DataStorage.question);
	}
	
	public static void BackQuestion() {
		if(DataStorage.question < 2) {
			return;
		}
		
		SaveAnswers();
		GUI.Question(--DataStorage.question);
	}
	
	public static void NextQuestion() {
		if(DataStorage.question >= DataStorage.questionAmount) {
			return;
		}
		
		SaveAnswers();		
		GUI.Question(++DataStorage.question);
	}
	
	public static void SaveAnswers() {
		DataStorage.working[DataStorage.question - 1] = GUI.workingArea.getText();
		int selectedRadio = -1;
		for(int i = 0; i < 4; i++) {
			if(GUI.answerRadios[i].isSelected()) {
				selectedRadio = i;
			}
		}
		DataStorage.selectedAnswer[DataStorage.question - 1] = selectedRadio;
	}
	
	public static void Grade() {
		DataStorage.score = 0;
		for(int i = 0; i < DataStorage.questionAmount; i++) {
			if(DataStorage.selectedAnswer[i] == DataStorage.correctAnswers[i] - 1) {
				DataStorage.score++;
			}
		}
		
		DataStorage.percentageScore = (int) Math.round((double) DataStorage.score / DataStorage.questionAmount * 100) + "%";
	}
	
	public static void Exit(boolean override) {
		if(override || !DataStorage.inQuiz) {
			System.exit(0);
		}
	}
}
