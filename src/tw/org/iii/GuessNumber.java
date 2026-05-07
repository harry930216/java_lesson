package tw.org.iii;

import javax.swing.JFrame;

public class GuessNumber extends JFrame {

	public static void main(String[] args) {

		new GuessNumber();

	}

	public GuessNumber() {
		super("猜數字");

		setSize(640, 480);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
