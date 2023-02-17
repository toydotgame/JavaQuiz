/* AUTHOR: toydotgame
 * CREATED ON: 2023-02-16
 * Main class that handles GUI events and logic.
 */

public class Main {
	public static void main(String[] args) {
		new GUI("mainf"); // Launches EDT thread (use for GUI only).
	}
	
	public static void Exit() {
		System.exit(0);
	}
}
