package ru.spb.altercom;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import static ru.spb.altercom.Controller.BULLET_HOLE_RADIUS;

public class BulletHole {

    public static final BufferedImage IMAGE = createImage();

    private static BufferedImage createImage() {
        var image = new BufferedImage(2 * BULLET_HOLE_RADIUS, 2 * BULLET_HOLE_RADIUS, BufferedImage.TYPE_INT_ARGB);

        var g2 = image.createGraphics();
        var ellipse = new Ellipse2D.Double(
                0,
                0,
                2 * BULLET_HOLE_RADIUS,
                2 * BULLET_HOLE_RADIUS);
        g2.setPaint(new Color(0x78, 0x90, 0x9c));
        g2.fill(ellipse);
        g2.setStroke(new BasicStroke(2));
        g2.setPaint(new Color(0x37, 0x47, 0x4f));
        g2.draw(ellipse);

        return image;
    }

    private final Point2D point;

    public BulletHole(Point2D point) {
        this.point = point;
    }

    public Point2D getPoint() {
        return point;
    }

}
