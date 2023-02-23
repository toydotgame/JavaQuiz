import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/* AUTHOR: toydotgame
 * CREATED ON: 2023-02-16
 * Main class that handles GUI events and logic.
 */

@SuppressWarnings("unused")
public class Main extends Thread {
	static ExecutorService popupExecutor = Executors.newFixedThreadPool(1);
	static Runnable popupThread = new Popup();
	static ExecutorService keyExecutor = Executors.newCachedThreadPool(); // Calling multiple threads, which can be reused.
	static Runnable keyThread = new KeyListener();
	
	public static void main(String[] args) {
		//keyExecutor.execute(keyThread);
		//new Thread(KeyListener::differentMethod).start();
		keyExecutor.submit(KeyListener::differentMethod);
		keyExecutor.submit(KeyListener::differentMethod);
		System.out.println("Thread: " + Thread.currentThread() + ", running at " + System.currentTimeMillis());
		
		/*try {
			UIManager.setLookAndFeel(DataStorage.theme);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			Popup.Create("");
			return;
		}*/
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
			/*System.out.println("Iteration " + i + ":\n" // Bug here where if() is not triggered on keyboard-activated SaveAnswers().
							 + "    Selected Radio: " + selectedRadio + ", isSelected() = " + GUI.answerRadios[i].isSelected() + "\n"
							 + "    Selected Answer in Storage (unset so far): " + DataStorage.selectedAnswer[DataStorage.question - 1]);*/
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
		
		DataStorage.percentageScore = (int) Math.round((double) DataStorage.score / DataStorage.questionAmount * 100);
		
		// Switch cases won't get you out of this:
		if(DataStorage.percentageScore <= 20) {
			DataStorage.grade = "E";
			DataStorage.gradeID = 0;
		} else if(DataStorage.percentageScore <= 40) {
			DataStorage.grade = "D";
			DataStorage.gradeID = 1;
		} else if(DataStorage.percentageScore <= 60) {
			DataStorage.grade = "C";
			DataStorage.gradeID = 2;
		} else if(DataStorage.percentageScore <= 80) {
			DataStorage.grade = "B";
			DataStorage.gradeID = 3;
		} else {
			DataStorage.grade = "A";
			DataStorage.gradeID = 4;
		}
		
		GUI.End();
	}
	
	public static void Exit(boolean override) {
		if(override || !DataStorage.inQuiz) {
			System.exit(0);
		}
	}
}
