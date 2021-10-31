package ru.spb.altercom;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 760;

    public static final int TARGET_CIRCLE_STEP = 30;
    public static final int TARGET_CENTER = 350;

    public static final int SIGHT_RADIUS = 40;
    public static final int SIGHT_INSET = (int) (0.8 * SIGHT_RADIUS);

    public static final int BULLET_HOLE_RADIUS = 5;

    private final JLabel statusbar;
    private final Canvas canvas;

    private int sightX = 350;
    private int sightY = 350;

    public final List<BulletHole> bulletHoleList = new ArrayList<>();

    public Controller() {
        this.statusbar = new JLabel();
        this.statusbar.setName("Statusbar");
        this.canvas = new Canvas(this);
        this.canvas.setName("Canvas");

        updateStatusbar();
    }

    public JLabel getStatusbar() {
        return statusbar;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    private void updateStatusbar() {
        getStatusbar().setText("You are welcome!");
    }

    public int getSightX() {
        return sightX;
    }

    public int getSightY() {
        return sightY;
    }

    public void moveSightLeft() {
        sightX -= 10;
        sightX = Math.max(SIGHT_RADIUS, sightX);
    }

    public void moveSightRight() {
        sightX += 10;
        sightX = Math.min(2 * TARGET_CENTER - SIGHT_RADIUS, sightX);
    }

    public void moveSightUp() {
        sightY -= 10;
        sightY = Math.max(SIGHT_RADIUS, sightY);
    }

    public void moveSightDown() {
        sightY += 10;
        sightY = Math.min(2 * TARGET_CENTER - SIGHT_RADIUS, sightY);
    }

    public void addBulletHole() {
        var point = new Point2D.Double(sightX, sightY);

        bulletHoleList.add(new BulletHole(point));
    }


}
