package ru.spb.altercom;

import javax.swing.*;

public class Controller {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 750;

    public static final int TARGET_CIRCLE_STEP = 30;
    public static final int TARGET_CENTER = 350;

    public static final int SIGHT_RADIUS = 40;
    public static final int SIGHT_INSET = (int) (0.8 * SIGHT_RADIUS);

    public static final int BULLET_HOLE_RADIUS = 5;

    private final JLabel statusbar;
    private final Canvas canvas;

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

}
