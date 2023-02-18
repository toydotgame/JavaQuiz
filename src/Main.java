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
		new GUI("main"); // Launches EDT thread (use for GUI only).
	}
	
	public static void StartQuiz() {
		DataStorage.question = 1;
		GUI.Question(DataStorage.question);
	}
		
	public static void Exit(boolean override) {
		if(override || !DataStorage.inQuiz) {
			System.exit(0);
		}
	}
}
