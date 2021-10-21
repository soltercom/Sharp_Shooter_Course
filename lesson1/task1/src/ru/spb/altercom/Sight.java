package ru.spb.altercom;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import static ru.spb.altercom.Controller.SIGHT_INSET;
import static ru.spb.altercom.Controller.SIGHT_RADIUS;

public class Sight {

    public static final BufferedImage IMAGE = createImage();

    private static BufferedImage createImage() {
        var image = new BufferedImage(SIGHT_RADIUS * 2, SIGHT_RADIUS * 2, BufferedImage.TYPE_INT_ARGB);

        var g2 = image.createGraphics();

        var ellipse = new Ellipse2D.Double(2, 2, 2 * SIGHT_RADIUS - 4, 2 * SIGHT_RADIUS - 4);
        g2.setPaint(Color.RED);
        g2.setStroke(new BasicStroke(5));
        g2.draw(ellipse);

        g2.setStroke(new BasicStroke(2));
        var line = new Line2D.Double(0, SIGHT_RADIUS, SIGHT_INSET, SIGHT_RADIUS);
        g2.draw(line);
        line = new Line2D.Double(2 * SIGHT_RADIUS - SIGHT_INSET, SIGHT_RADIUS, 2 * SIGHT_RADIUS, SIGHT_RADIUS);
        g2.draw(line);
        line = new Line2D.Double(Controller.SIGHT_RADIUS, 0, SIGHT_RADIUS, SIGHT_INSET);
        g2.draw(line);
        line = new Line2D.Double(Controller.SIGHT_RADIUS, 2 * SIGHT_RADIUS - SIGHT_INSET, SIGHT_RADIUS, 2 * SIGHT_RADIUS);
        g2.draw(line);

        return image;
    }

}
