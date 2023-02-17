import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/* AUTHOR: toydotgame
 * CREATED ON: 2023-02-16
 * Main class that handles GUI events and logic.
 */

public class Main {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(DataStorage.theme);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}
		new GUI("main"); // Launches EDT thread (use for GUI only).
	}
	
	public static void Exit() {
		System.exit(0);
	}
}
