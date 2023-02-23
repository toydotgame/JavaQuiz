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
}
