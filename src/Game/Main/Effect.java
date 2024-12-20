package src.Game.Main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Effect {
    private final double x;
    private final double y;
    private final double max_distance;
    private final int max_size;
    private final Color color;
    private final int totalEffect;
    private final int speed;
    private double currrent_distance;
    private ModelBoom[] booms;
    private float alpha = 1f;

    public Effect(double x, double y, double max_distance, int max_size, int speed, float totalEffect, Color color) {
        this.x = x;
        this.y = y;
        this.totalEffect = (int) totalEffect;
        this.max_size = max_size;
        this.max_distance = max_distance;
        this.speed = speed;
        this.color = color;
        createRandom();
    }

    private void createRandom() {
        booms = new ModelBoom[totalEffect];
        float per = 360f / totalEffect;
        Random ran = new Random();
        for (int i = 0; i < totalEffect; i++) { // Fixed to iterate only up to totalEffect - 1
            int r = ran.nextInt((int) per) + 1;
            int boomsSize = ran.nextInt(max_size) + 1;
            float angle = i * per + r;
            booms[i] = new ModelBoom(boomsSize, angle); // Use i directly
        }
    }

    public void draw(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();
        Composite oldComposite = g2.getComposite();
        g2.setColor(color);
        g2.translate(x, y);

        for (ModelBoom b : booms) {
            double bom = Math.cos(Math.toRadians(b.getAngle())) * currrent_distance;
            double bom1 = Math.sin(Math.toRadians(b.getAngle())) * currrent_distance;
            double boomsSize = b.getSize();
            double space = boomsSize / 2;

            if (currrent_distance >= max_distance - (max_distance * 0.7f)) {
                alpha = (float) ((max_distance - currrent_distance) / (max_distance * 0.7f));
            }

            if (alpha > 1) {
                alpha = 1;
            } else if (alpha < 0) {
                alpha = 0;
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(new Rectangle2D.Double(bom - space, bom1 - space, boomsSize, boomsSize));
        }

        g2.setComposite(oldComposite);
        g2.setTransform(oldTransform);
    }

    public void update() {
        currrent_distance += speed;
    }

    public boolean check() {
        return currrent_distance < max_distance;
    }
}
