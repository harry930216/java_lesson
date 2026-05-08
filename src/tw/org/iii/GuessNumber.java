package tw.org.iii;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GuessNumber extends JFrame {

	private JButton guess;
	private JTextField input;
	private JTextArea log;
	private JPanel top;

	public static void main(String[] args) {

		new GuessNumber();

	}

	public GuessNumber() {
		super("猜數字");

		top = new JPanel(new BorderLayout());
		guess = new JButton("猜");
		input = new JTextField(6);
		log = new JTextArea();

		add(top, BorderLayout.NORTH);
		top.add(guess, BorderLayout.EAST);
		top.add(input, BorderLayout.CENTER);

		add(log, BorderLayout.CENTER);
		log.append(newNumber(1));

		setSize(640, 480);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	// 產生新數字
	private String newNumber(int n) {
		final int a = n;
		StringBuilder sb;
		String s;

		// 這裡在發牌後互換 保證不重複
		int[] numbers = new int[10]; // 10: 0-9
		for (int i = 0; i < numbers.length; i++)
			numbers[i] = i;

		for (int j = 0; j < numbers.length - 1; j++) {
			int random = (int) (Math.random() * (numbers.length - j));
			int bridge = numbers[numbers.length - j - 1];
			numbers[numbers.length - j - 1] = numbers[random];
			numbers[random] = bridge;
		}

		// 把前幾位的牌 塞進字串傳回
		sb = new StringBuilder();
		for (int i = 0; i < a; i++) {
			sb.append(numbers[i]);
		}
		s = sb.toString();

		return s;

	}

}
