package tw.Harry.apis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BallPanel extends JPanel {

	private String[] source = { "dir/ball0.png", "dir/ball1.png", "dir/ball2.png", "dir/ball3.png" };
	private BufferedImage[] imgs = new BufferedImage[source.length];
	private ArrayList<BallTask> balls;

	public BallPanel() {

		try {
			for (int i = 0; i < source.length; i++) {
				imgs[i] = ImageIO.read(new File(source[i]));
			}
		} catch (IOException e) {
			System.out.println(e);
		}

		setBackground(new Color(50, 247, 240));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (BallTask ball : balls) {
			g.drawImage(imgs[0], 0, 0, null);
		}
	}

	private class BallTask {

	}
}
