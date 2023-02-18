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
	static int buttonTextSize = 28;
	static String font = "Tahoma";
	
	// Public:
	public static Color borderColor = Color.decode("#008080");
	public static Color imageBorderColor = Color.decode("#0c0c0c");
	public static double windowScaleFactor = 1.5;
	public static int borderWidth = 3;
	public static Font titleText = new Font(font, Font.BOLD, titleSize);
	public static Font genericText = new Font(font, Font.PLAIN, textSize);
	public static Font buttonText = new Font(font, Font.BOLD, buttonTextSize);
	public static Font answerText = new Font(font, Font.ITALIC, textSize);
	public static String theme = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"; // TODO: Test compatibility with Win10.
	public static int question = 0;
	public static int questionAmount = 5;
	public static boolean inQuiz = false;
	public static String error;
	public static String[] working = new String[questionAmount];
	public static int[] selectedAnswer = new int[questionAmount];
	public static int score;
	public static String percentageScore = "0%";
	public static String windowIcon = "obamna.jpg";

	// Text & Arrays:
	public static String descriptionText = "This is a test for the Year 9 Trigonometry unit. "
										 + "It is a timed test, but there is no time limit. "
										 + "You will not be able to exit the test once it has started. "
										 + "There will be 5 questions. "
										 + "<br><br><p align=\"center\">Good luck!</p>";
	public static String[] questionText = {
		"What is 1 + 1?",
		"What is BOGOS BINTED?",
		"Question3",
		"Question 4",
		"yes 5"
	};
	public static String[][] answers = {
		{ "1", "2", "3", "4" },
		{ "5", "6", "7", "8" },
		{ "1", "2", "3", "4" },
		{ "5", "6", "7", "8" },
		{ "1", "122", "32423 4324 3243234 323", "234 2344" }
	};
	public static int[] correctAnswers = {
		1,
		2,
		3,
		4,
		1
	};
	public static String[] questionImages = {
		"obamna.jpg",
		"",
		"sus.png",
		"",
		""
	};
}
