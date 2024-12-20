package src.Game.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlanGame extends JComponent {

    private Graphics2D g2;
    private BufferedImage image;
    private int width; // set the width
    private int height;
    private Thread thread; // create a thread
    private boolean start = true;
    private Key key;
    private int shotTime;

    // Game Fps -> Stands for frames per second
    private final int FPS = 60;
    private final int Target_Time = 1000000000 / FPS; // 60 fps per second

    // 1 second = 1,000 milliseconds
    // 1,000,000,000 nanoseconds = 1 second
    // 1,000,000,000 / 60 = 16,666,666.666 nanoseconds
    // Target_time = 16,666,666.666

    // create a Game object
    private Player player;
    private List<Bullet> bullets;
    private List<Rocket> rockets;
    private List<Effect> BoomEffect;

    public void start() {
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        thread = new Thread(new Runnable() { // thread
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
        initObjectGame();
        initKeyboard();
        initBullets();
        thread.start();
    }

    private void addRocket() {
        Random ran = new Random();
        int locationY = ran.nextInt(height - 50) + 25;
        Rocket rocket = new Rocket();
        rocket.changeLocation(0, locationY);
        rocket.changeAngle(0);
        rockets.add(rocket);
        int locationY2 = ran.nextInt(height - 50) + 25;
        Rocket rocket2 = new Rocket();
        rocket2.changeLocation(width, locationY2);
        rocket2.changeAngle(180);
        rockets.add(rocket2);

    }

    private void initObjectGame() {
        player = new Player();
        player.changeLocation(150, 150);
        rockets = new ArrayList<>();
        BoomEffect = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    addRocket();
                    sleep(3000);
                }
            }
        }).start();
    }

    private void initKeyboard() {
        key = new Key();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    key.setKey_left(true);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    key.setKey_right(true);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKey_space(true);
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKey_j(true);
                } else if (e.getKeyCode() == KeyEvent.VK_K) {
                    key.setKey_k(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    key.setKey_left(false);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    key.setKey_right(false);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKey_space(false);
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKey_j(false);
                } else if (e.getKeyCode() == KeyEvent.VK_K) {
                    key.setKey_k(false);
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                float s = 0.5f;
                while (start) {
                    float angle = player.getAngle();
                    if (key.isKey_left()) {
                        angle -= s;
                    }

                    if (key.isKey_right()) {
                        angle += s;
                    }
                    if (key.isKey_j() || key.isKey_k()) {
                        if (shotTime == 0) {
                            if (key.isKey_j()) {
                                bullets.add(0, new Bullet(player.getX(), player.getY(), player.getAngle(), 5, 3f));
                            } else {
                                bullets.add(0, new Bullet(player.getX(), player.getY(), player.getAngle(), 20, 3f));
                            }
                        }
                        shotTime++;
                        if (shotTime == 15) {
                            shotTime = 0;
                        }
                    } else {
                        shotTime = 0;
                    }
                    if (key.isKey_space()) {
                        player.speedUp();
                    } else {
                        player.speedDown();
                    }
                    player.update();
                    player.changeAngle(angle);
                    for (int i = 0; i < rockets.size(); i++) {
                        Rocket rocket = rockets.get(i);
                        if (rocket != null) {
                            rocket.update();
                            if (!rocket.check(width, height)) {
                                rockets.remove(rocket);
                                System.out.println("removed");
                            }
                        }
                    }
                    sleep(5);
                }
            }
        }).start();
    }

    // private void checkBullets(Bullet bullet) {
    // for (int i = 0; i < rockets.size(); i++) {
    // Rocket rocket = rockets.get(i);
    // if (rocket != null) {
    // Area area = new Area(bullet.getShape());
    // area.intersect(rocket.getShape());
    // if (!area.isEmpty()) {
    // BoomEffect.add(new Effect(bullet.getCenterX(), bullet.getCenterY(), 3, 5, 60,
    // 5, new Color(230, 207, 105)));
    //
    //
    // // rockets.remove(rocket);
    // bullets.remove(bullet);
    // }
    // }
    // }
    // }

    // private void checkBullets(Bullet bullet) {
    // for (int i = 0; i < rockets.size(); i++) {
    // Rocket rocket = rockets.get(i);
    // if (rocket != null) {
    // Area area = new Area(bullet.getShape());
    // area.intersect(rocket.getShape());
    // if (!area.isEmpty()) {
    // BoomEffect.add(new Effect(bullet.getCenterX(), bullet.getCenterY(), 3, 5, 60,
    // 5, new Color(230, 207, 105)));

    // // rockets.remove(rocket);
    // bullets.remove(bullet);
    // break; // Exit the loop after finding a collision
    // }
    // }
    // }
    // }

    private void checkBullets(Bullet bullet) {
        for (int i = 0; i < rockets.size(); i++) {
            Rocket rocket = rockets.get(i);
            if (rocket != null) {
                Area area = new Area(bullet.getShape());
                area.intersect(rocket.getShape());
                if (!area.isEmpty()) {
                    // Add a BoomEffect for the explosion
                    BoomEffect.add(new Effect(
                            bullet.getCenterX(),
                            bullet.getCenterY(),
                            80, // Increased max_distance for better visibility
                            10, // Adjust size for better effect
                            3, // Adjust speed for better animation
                            30, // Increased number of particles for richer effect
                            new Color(230, 207, 105)));

                    if (true) { // for test hp
                        // Optionally remove the rocket if desired
                        rockets.remove(rocket);
                        double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
                        double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;

                        // Add a BoomEffect for the explosion
                        BoomEffect.add(new Effect(
                                x,
                                y,
                                80, // Increased max_distance for better visibility
                                10, // Adjust size for better effect
                                3, // Adjust speed for better animation
                                30, // Increased number of particles for richer effect
                                new Color(32, 178, 170))); // Add a BoomEffect for the explosion
                        BoomEffect.add(new Effect(
                                x,
                                y,
                                80, // Increased max_distance for better visibility
                                10, // Adjust size for better effect
                                3, // Adjust speed for better animation
                                25, // Increased number of particles for richer effect
                                new Color(32, 178, 170))); // Add a BoomEffect for the explosion
                        BoomEffect.add(new Effect(
                                x,
                                y,
                                10, // Increased max_distance for better visibility
                                10, // Adjust size for better effect
                                100, // Adjust speed for better animation
                                20, // Increased number of particles for richer effect
                                new Color(232, 207, 105))); // Add a BoomEffect for the explosion
                        BoomEffect.add(new Effect(
                                x,
                                y,
                                80, // Increased max_distance for better visibility
                                10, // Adjust size for better effect
                                3, // Adjust speed for better animation
                                30, // Increased number of particles for richer effect
                                new Color(32, 178, 170))); // Add a BoomEffect for the explosion
                        BoomEffect.add(new Effect(
                                x,
                                y,
                                80, // Increased max_distance for better visibility
                                10, // Adjust size for better effect
                                3, // Adjust speed for better animation
                                30, // Increased number of particles for richer effect
                                new Color(32, 178, 170)));
                    }

                    // Remove the bullet after collision
                    bullets.remove(bullet);

                    break; // Exit loop after handling the collision
                }
            }
        }
    }

    private void initBullets() {
        bullets = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    for (int i = 0; i < bullets.size(); i++) {
                        Bullet bullet = bullets.get(i);
                        if (bullet != null) {
                            bullet.update();
                            checkBullets(bullet);
                            if (!bullet.check(width, height)) {
                                bullets.remove(bullet);
                            }
                        } else {
                            bullets.remove(bullet);
                        }
                    }
                    for (int i = 0; i < BoomEffect.size(); i++) {
                        Effect boomEffect = BoomEffect.get(i);
                        if (boomEffect != null) {
                            boomEffect.update();
                            if (!boomEffect.check()) {
                                BoomEffect.remove(boomEffect);
                            }
                        } else {
                            BoomEffect.remove(boomEffect);
                        }
                    }
                    sleep(1);
                }
            }
        }).start();
    }

    private void drawBackground() {
        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(0, 0, width, height);
    }

    private void drawGame() {
        player.draw(g2);
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet != null) {
                bullet.draw(g2);
            }

        }

        for (int i = 0; i < rockets.size(); i++) {
            Rocket rocket = rockets.get(i);
            if (rocket != null) {
                rocket.draw(g2);
            }
        }

        for (int i = 0; i < BoomEffect.size(); i++) {
            Effect boomEffect = BoomEffect.get(i);
            if (boomEffect != null) {
                boomEffect.draw(g2);
            }
        }
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
