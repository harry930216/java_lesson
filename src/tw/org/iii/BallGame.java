package tw.org.iii;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import tw.Harry.apis.BallPanel;

public class BallGame extends JFrame{

	private BallPanel panel;
	
	public BallGame() {
		super("一堆球");
		
		setLayout(new BorderLayout());
		panel = new BallPanel();
		add(panel, BorderLayout.CENTER);
		
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new BallGame();

	}

}
