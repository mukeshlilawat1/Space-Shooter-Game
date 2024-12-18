package src.Game.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PlanGame extends JComponent {

    private Graphics2D g2;
    private BufferedImage image;
    private int width; // set the width
    private int height;
    private Thread thread; // create a thread
    private boolean start = true;

    // Game Fps -> Stands for frames per second
    private final int FPS = 60;
    private final int Target_Time = 1000000000 / FPS; // 60 fps per second

    // 1 second = 1,000 milliseconds
    // 1,000,000,000 nanoseconds = 1 second
    // 1,000,000,000 / 60 = 16,666,666.666 nanoseconds
    // Target_time = 16,666,666.666

    public void start() {
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        thread = new Thread(new Runnable() { //thread
            @Override
            public void run() {
                while (start) {
                    long startTime = System.nanoTime();
                    drawBackground();
                    drawGame();
                    reader();
                    long time = System.nanoTime() - startTime;
                    if (time < Target_Time) {
                        long sleep = (Target_Time - time) / 1000000;
                        sleep(sleep);
                        // System.out.println(sleep);
                    }
                }
            }
        });
        thread.start();
    }

    private void drawBackground() {
        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(0, 0, width, height);
    }

    private void drawGame() {

    }

    private void reader() {
        Graphics graphics = getGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
    }

    private void sleep(long speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
}
