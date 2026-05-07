package tw.org.iii;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

/*
 * Brad18 is-a JFrame
 */

public class Harry18JFrame extends JFrame {
	private JButton b1, b2, b3;
	
	public Harry18JFrame() {
		super("我的視窗");
		
		b1 = new JButton("B1");
		b2 = new JButton("B2");
		b3 = new JButton("B3");
		
		setLayout(new FlowLayout());
		
		add(b1); add(b2); add(b3);
		
		setSize(640, 480);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Harry18JFrame();

	}

}
