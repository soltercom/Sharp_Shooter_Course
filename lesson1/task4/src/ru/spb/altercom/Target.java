package ru.spb.altercom;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import static ru.spb.altercom.Controller.*;


public class Target {

    public static final BufferedImage IMAGE = createImage();

    private static BufferedImage createImage() {
        var image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

        var g2 = image.createGraphics();

        var colors = new int[] {1, 1, 1, 1, 1, 1, 0, 0, 0, 1};
        var font = new Font("SansSerif", Font.BOLD, 24);

        var metrics = g2.getFontMetrics(font);
        var dh = metrics.getHeight();
        var dh2 = metrics.getDescent();
        var dw = metrics.stringWidth("8");

        var rect = new Rectangle(0, 0, WIDTH, HEIGHT);
        g2.setPaint(Color.DARK_GRAY);
        g2.fill(rect);

        for (int i = 10; i > 0; i--) {
            var ellipse = new Ellipse2D.Double(
                    TARGET_CENTER - i * TARGET_CIRCLE_STEP,
                    TARGET_CENTER - i * TARGET_CIRCLE_STEP,
                    2 * i * TARGET_CIRCLE_STEP,
                    2 * i * TARGET_CIRCLE_STEP);
            g2.setPaint(colors[10 - i] == 1 ? Color.BLACK: Color.WHITE);
            g2.fill(ellipse);
            g2.setPaint(colors[10 - i] == 1 ? Color.WHITE: Color.BLACK);
            g2.draw(ellipse);

            if (i < 10) {
                //g2.setPaint(colors[9 - i] == 1 ? Color.WHITE: Color.BLACK);
                g2.setPaint(Color.GRAY);
                g2.setFont(font);
                g2.drawString(String.valueOf(10 - i),
                        TARGET_CENTER - (i + 1) * TARGET_CIRCLE_STEP + (TARGET_CIRCLE_STEP - dw) / 2 + 1,
                        TARGET_CENTER + dh / 2 - dh2);
                g2.drawString(String.valueOf(10 - i),
                        TARGET_CENTER + i * TARGET_CIRCLE_STEP + (TARGET_CIRCLE_STEP - dw) / 2 + 1,
                        TARGET_CENTER + dh / 2 - dh2);
                g2.drawString(String.valueOf(10 - i),
                        TARGET_CENTER - dw / 2,
                        TARGET_CENTER - (i + 1) * TARGET_CIRCLE_STEP + (TARGET_CIRCLE_STEP + dh) / 2 - dh2);
                g2.drawString(String.valueOf(10 - i),
                        TARGET_CENTER - dw / 2,
                        TARGET_CENTER + i * TARGET_CIRCLE_STEP + (TARGET_CIRCLE_STEP + dh) / 2 - dh2);
            }
        }

        return image;
    }

}
