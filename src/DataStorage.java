import java.awt.Color;
import java.awt.Font;

/* AUTHOR: toydotgame
 * CREATED ON: 2023-02-16
 * Data storage class to easily access constants and
 * questions/answers in arrays.
 */

public class DataStorage {
	// Internal:
	static int titleSize = 38;
	static int textSize = 20;
	static String font = "Tahoma";
	
	// Public:
	public static Color borderColor = Color.decode("#1c1c1c");
	public static double windowScaleFactor = 1.5;
	public static int borderWidth = 3;
	public static Font titleText = new Font(font, Font.BOLD, titleSize);
	public static Font genericText = new Font(font, Font.PLAIN, textSize);

	// Text & Arrays:
	public static String descriptionText = "This is a test for the Year 9 Trigonometry unit. It is a timed test, but there is no time limit. You will not be able to exit the test once it has started. There will be 5 questions.<br><br>Good luck!";
}
