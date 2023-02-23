import java.awt.event.KeyEvent;

public class KeyListener implements Runnable {
	@Override
	public void run() { // Debug
		System.out.println("Thread: " + Thread.currentThread().getName() + ", running at " + System.currentTimeMillis());
	}
	
	public static void MainPanel(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Main.Exit(false);
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			Main.StartQuiz();
		}
	}
	
	public static void QuestionPanel(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Popup.Create("exit");
			return;
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			// Save answers and check if the current question has been answered. If it hasn't, quit the operation.
			Main.SaveAnswers();
			if(DataStorage.selectedAnswer[DataStorage.question - 1] < 0) {
				return;
			}
			
			if(DataStorage.question == DataStorage.questionAmount) {
				Popup.Create("end");
				return;
			}
			Main.NextQuestion();
			return;
		} else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if(DataStorage.selectedAnswer[DataStorage.question - 1] >= 0) {
				return; // In this case, you can't go back on a question that's already answered, but you can go back to one __from__ an unanswered quesiton.
			}
			
			Main.BackQuestion();
			return;
		}
		
		for(int i = 0; i < 4; i++) {
			GUI.answerRadios[i].setSelected(false);
		}
		switch(e.getKeyCode()) {
			case KeyEvent.VK_1:
				GUI.answerRadios[0].setSelected(true);
				DataStorage.selectedAnswer[DataStorage.question - 1] = 0; // Bug #1: setSelected(true) is not detected in Main.SaveAnswers() when launched from a keyboard listener. Must save the answer manually in the keyboard listener code as a workaround.
				break;
			case KeyEvent.VK_2:
				GUI.answerRadios[1].setSelected(true);
				DataStorage.selectedAnswer[DataStorage.question - 1] = 1;
				break;
			case KeyEvent.VK_3:
				GUI.answerRadios[2].setSelected(true);
				DataStorage.selectedAnswer[DataStorage.question - 1] = 2;
				break;
			case KeyEvent.VK_4:
				GUI.answerRadios[3].setSelected(true);
				DataStorage.selectedAnswer[DataStorage.question - 1] = 3;
				break;
		}
	}
	
	public static void QuestionWorkingArea(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GUI.panel.requestFocus();
		}
	}
	
	public static void PopupExit(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Popup.frame.dispose();
			GUI.panel.requestFocus();
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			Main.Exit(true);
		}
	}
	
	public static void PopupGeneric(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			Main.Exit(true);
		}
	}
	
	public static void PopupEnd(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Popup.frame.dispose();
			GUI.panel.requestFocus();
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			Main.Grade();
			Popup.frame.dispose();
			GUI.panel.requestFocus();
		}
	}
}
