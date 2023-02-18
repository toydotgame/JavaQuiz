import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/* AUTHOR: toydotgame
 * CREATED ON: 2023-02-16
 * Main class that handles GUI events and logic.
 */

public class Main extends Thread {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(DataStorage.theme);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			new Popup("");
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
