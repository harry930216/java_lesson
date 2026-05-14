package tw.org.iii;

import java.awt.BorderLayout;
import java.util.Scanner;

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

	private String answer; // 答案
	private String inputNumber; // 輸入數字

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
	    
	    // ===== 開局 =====
	    newNumber(4);
	   	System.out.println("答案: " + answer);   // debug 用
	    
	    // ===== 按鈕事件 =====
	    guess.addActionListener(e -> {
	    	inputNumber = input.getText();
	        compareAnswer(inputNumber);
	        input.setText("");
	    });
	    
	    setSize(640, 480);
	    setVisible(true);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// 邏輯:產生新數字 (n<=10 這件事還沒處理)
	private void newNumber(int n) {
		final int a = n;
		StringBuilder sb;

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
		answer = sb.toString();
	}

	// 邏輯:比較數字(字串)
	//
	private void compareAnswer(String inputNumber) {
		int a = 0, b = 0; // a, b = 0
		StringBuilder sb;

		// 檢查rawNumber是否合法?


		// 產出 a,b
		for (int i = 0; i < inputNumber.length(); i++) {
			if (answer.indexOf(inputNumber.charAt(i)) == i)
				a++;// 先檢查是否有
			else if (answer.indexOf(inputNumber.charAt(i)) >= 0)
				b++;
		}

		// log 顯示 a,b
		log.append(String.format("輸入值:%s -> %dA%dB\n",inputNumber, a, b));
//		System.out.printf("%dA%dB",a,b);
	}
	//

}
