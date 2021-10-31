package ru.spb.altercom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static ru.spb.altercom.Controller.BULLET_HOLE_RADIUS;
import static ru.spb.altercom.Controller.SIGHT_RADIUS;

public class Canvas extends JPanel implements ActionListener {

    private final static int DELAY = 20;
    private final Timer timer = new Timer(DELAY, this);
    private boolean isChanged;

    private final Controller controller;

    public Canvas(Controller controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        setFocusable(true);
        addKeyListener(new TAdapter());
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        repaint();
        timer.stop();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2 = (Graphics2D) g;

        g2.drawImage(Target.IMAGE, 0, 0, null);
        for (var bulletHole: controller.bulletHoleList) {
            g2.drawImage(BulletHole.IMAGE,
                    (int) bulletHole.getPoint().getX() - BULLET_HOLE_RADIUS,
                    (int) bulletHole.getPoint().getY() - BULLET_HOLE_RADIUS, null);
        }
        g2.drawImage(Sight.IMAGE,
                controller.getSightX() - SIGHT_RADIUS,
                controller.getSightY() - SIGHT_RADIUS, null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isChanged) {
            repaint();
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            var key = e.getKeyCode();
            var dx = 0;
            var dy = 0;
            isChanged = false;

            if (key == KeyEvent.VK_LEFT) {
                dx--;
                controller.moveSightLeft();
            }

            if (key == KeyEvent.VK_RIGHT) {
                dx++;
                controller.moveSightRight();
            }

            if (key == KeyEvent.VK_UP) {
                dy--;
                controller.moveSightUp();
            }

            if (key == KeyEvent.VK_DOWN) {
                dy++;
                controller.moveSightDown();
            }

            if (key == KeyEvent.VK_SPACE) {
                controller.addBulletHole();
                isChanged = true;
            }

            if (dx != 0 || dy != 0) {
                isChanged = true;
            }
        }
    }

}
