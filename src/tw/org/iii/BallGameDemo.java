package tw.org.iii;

import java.awt.BorderLayout;
import tw.Harry.apis.BallPanelDemo;     




import javax.swing.JFrame;

import tw.Harry.apis.BallPanel;
import tw.Harry.apis.BallPanelDemo;     
import tw.Harry.apis.BallPanelDemo;     
public class BallGameDemo extends JFrame{

	private BallPanelDemo panel;      
	
	public BallGameDemo() {
		super("一堆球");
		
		setLayout(new BorderLayout());
		panel = new BallPanelDemo();
		add(panel, BorderLayout.CENTER);
		
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new BallGameDemo();

	}

}
