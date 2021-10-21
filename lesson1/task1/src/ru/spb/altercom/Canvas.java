package ru.spb.altercom;

import javax.swing.*;
import java.awt.*;

import static ru.spb.altercom.Controller.BULLET_HOLE_RADIUS;

public class Canvas extends JPanel {

    private final Controller controller;

    public Canvas(Controller controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2 = (Graphics2D) g;

        g2.drawImage(Target.IMAGE, 0, 0, null);
        g2.drawImage(BulletHole.IMAGE, 300 - BULLET_HOLE_RADIUS, 500 - BULLET_HOLE_RADIUS, null);

    }

}
