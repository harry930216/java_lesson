package tw.Harry.apis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BallPanelDemo extends JPanel {

    // ── 設定 ────────────────────────────────────────
    private final int FPS_INTERVAL = 16;       // 畫面刷新間隔（~60fps）
    private final int BALL_INTERVAL = 30;      // 每顆球位置更新間隔

    // ── 資源 ────────────────────────────────────────
    private String[] source = {
        "dir/ball0.png", "dir/ball1.png",
        "dir/ball2.png", "dir/ball3.png"
    };
    private BufferedImage[] imgs = new BufferedImage[source.length];
    private int[] ballWs = new int[source.length];
    private int[] ballHs = new int[source.length];

    // ── 狀態 ────────────────────────────────────────
    private ArrayList<BallTask> balls = new ArrayList<>();
    private Timer timer;

    public BallPanelDemo() {
        setBackground(new Color(50, 247, 240));

        try {
            for (int i = 0; i < source.length; i++) {
                imgs[i] = ImageIO.read(new File(source[i]));
                ballWs[i] = imgs[i].getWidth();
                ballHs[i] = imgs[i].getHeight();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        addMouseListener(new MyMouseListener());

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() { repaint(); }
        }, 0, FPS_INTERVAL);
    }

    // ── 每顆球 ──────────────────────────────────────
    private class BallTask extends TimerTask {
        int cx, cy;
        int dx, dy;
        int ball;
        int ballW, ballH;

        BallTask(int cx, int cy) {
            this.cx = cx;
            this.cy = cy;
            this.ball = (int)(Math.random() * source.length);
            this.ballW = ballWs[ball];
            this.ballH = ballHs[ball];
            this.dx = (int)(Math.random() * 17 - 8);
            this.dy = (int)(Math.random() * 17 - 8);
        }

        @Override
        public void run() {
            int viewW = getWidth();         // ★ 每幀即時讀（修拖動延遲）
            int viewH = getHeight();

            cx += dx;
            cy += dy;

            // 邊界 = panel 大小往內縮一個半邊長（不另加 margin）
            int leftBound   = ballW / 2;
            int rightBound  = viewW - ballW / 2;
            int topBound    = ballH / 2;
            int bottomBound = viewH - ballH / 2;

            // 出界 → 夾回 + 強制方向（可進不可出）
            if (cx < leftBound)   { cx = leftBound;   dx =  Math.abs(dx); }
            if (cx > rightBound)  { cx = rightBound;  dx = -Math.abs(dx); }
            if (cy < topBound)    { cy = topBound;    dy =  Math.abs(dy); }
            if (cy > bottomBound) { cy = bottomBound; dy = -Math.abs(dy); }
        }
    }

    // ── 滑鼠點擊產生球 ──────────────────────────────
    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            BallTask ball = new BallTask(e.getX(), e.getY());
            timer.schedule(ball, 100, BALL_INTERVAL);
            balls.add(ball);
        }
    }

    // ── 畫面 ────────────────────────────────────────
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (BallTask ball : balls) {
            int drawX = ball.cx - ball.ballW / 2;
            int drawY = ball.cy - ball.ballH / 2;
            g.drawImage(imgs[ball.ball], drawX, drawY, null);
        }
    }
}