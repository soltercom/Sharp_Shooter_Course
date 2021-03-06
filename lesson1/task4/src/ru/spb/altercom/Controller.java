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

    private final static int MAX_SHOOTING_ATTEMPTS = 12;

    private final JLabel statusbar;
    private final Canvas canvas;

    private int sightX;
    private int sightY;
    private int shootCounter;
    private int lastScore;
    private int totalScore;

    public final List<BulletHole> bulletHoleList = new ArrayList<>();

    private GAME_STATE gameState = GAME_STATE.INIT;

    private final Force force;

    public Controller() {
        this.statusbar = new JLabel();
        this.statusbar.setName("Statusbar");
        this.canvas = new Canvas(this);
        this.canvas.setName("Canvas");

        this.force = new Force();

        initGame();
    }

    public JLabel getStatusbar() {
        return statusbar;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    private void updateStatusbar() {
        switch (gameState) {
            case INIT:
                statusbar.setText("Press the SPACE bar to start the game.");
                break;
            case STARTED:
                statusbar.setText(String.format("Bullets left: %d, your score: %d (%d). Use: \u2192 \u2190 \u2191 \u2193 SPACE buttons.",
                        MAX_SHOOTING_ATTEMPTS - shootCounter, totalScore, lastScore));
                break;
            case OVER:
                statusbar.setText(String.format("Game over. Your score: %d.", totalScore));
                break;
        }
    }

    public int getSightX() {
        return sightX;
    }

    public int getSightY() {
        return sightY;
    }

    private void calcScore(Point2D.Double point) {
        var dist = new Point2D.Double(TARGET_CENTER, TARGET_CENTER).distance(point);

        lastScore = 0;
        for (int i = 1; i <= 10; i++) {
            if (dist <= i * TARGET_CIRCLE_STEP) {
                lastScore = 11 - i;
                break;
            }
        }

        totalScore += lastScore;
        updateStatusbar();
    }

    public void moveSightLeft() {
        if (gameState == GAME_STATE.STARTED) {
            force.addLeftMovement();
        }
    }

    public void moveSightRight() {
        if (gameState == GAME_STATE.STARTED) {
            force.addRightMovement();
        }
    }

    public void moveSightUp() {
        if (gameState == GAME_STATE.STARTED) {
            force.addUpMovement();
        }
    }

    public void moveSightDown() {
        if (gameState == GAME_STATE.STARTED) {
            force.addDownMovement();
        }
    }

    public boolean moveSight() {
        var velocity = force.getVelocity();

        this.sightX += (int) velocity.getX();
        sightX = Math.max(SIGHT_RADIUS, sightX);
        sightX = Math.min(2 * TARGET_CENTER - SIGHT_RADIUS, sightX);

        sightY += (int) velocity.getY();
        sightY = Math.max(SIGHT_RADIUS, sightY);
        sightY = Math.min(2 * TARGET_CENTER - SIGHT_RADIUS, sightY);

        return force.hasForces();
    }

    public boolean addRandomMovement() {
        return force.addRandomMovement();
    }

    public void addBulletHole() {
        if (gameState == GAME_STATE.INIT) {
            startGame();
            return;
        }

        var point = new Point2D.Double(sightX, sightY);
        bulletHoleList.add(new BulletHole(point));

        shootCounter++;
        calcScore(point);

        if (shootCounter >= MAX_SHOOTING_ATTEMPTS) {
            stopGame();
        }

        updateStatusbar();

    }

    private void initialPosition() {
        sightX = 100;
        sightY = 100;
    }

    private void initGame() {
        initialPosition();
        shootCounter = 0;
        updateStatusbar();
    }

    private void startGame() {
        gameState = GAME_STATE.STARTED;
        updateStatusbar();
        canvas.start();
    }

    private void stopGame() {
        gameState = GAME_STATE.OVER;
        updateStatusbar();
        canvas.stop();
    }

    public enum GAME_STATE {
        INIT,
        STARTED,
        OVER
    }
}
