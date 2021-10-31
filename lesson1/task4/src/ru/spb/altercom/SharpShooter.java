package ru.spb.altercom;

import javax.swing.*;
import java.awt.*;

public class SharpShooter extends JFrame {

    public SharpShooter() {
        initUI();
        init();
    }

    private void initUI() {
        var controller = new Controller();
        add(controller.getCanvas());
        add(controller.getStatusbar(), BorderLayout.SOUTH);
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sharp shooter");
        setSize(Controller.WIDTH, Controller.HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SharpShooter::new);
    }

}
