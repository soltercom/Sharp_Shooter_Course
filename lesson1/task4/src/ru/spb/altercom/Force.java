package ru.spb.altercom;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Force {

    private static final Random rnd = new Random();
    private static final double VELOCITY = 1.5D;
    private static final int MAX_DURATION = 20;
    private static final int MAX_VELOCITY = 10;

    private static final int RANDOM_MOVEMENT_FREQUENCY = 10;

    private final List<ForceUnit> forceList = new ArrayList<>();

    public void addLeftMovement() {
        var list = Arrays.stream(ForceUnit.SIMPLE_MOVE)
                .mapToObj(item -> new Point2D.Double(-item, 0))
                .collect(Collectors.toList());
        forceList.add(new ForceUnit(list));
    }

    public void addRightMovement() {
        var list = Arrays.stream(ForceUnit.SIMPLE_MOVE)
                .mapToObj(item -> new Point2D.Double(item, 0))
                .collect(Collectors.toList());
        forceList.add(new ForceUnit(list));
    }

    public void addUpMovement() {
        var list = Arrays.stream(ForceUnit.SIMPLE_MOVE)
                .mapToObj(item -> new Point2D.Double(0, -item))
                .collect(Collectors.toList());
        forceList.add(new ForceUnit(list));
    }

    public void addDownMovement() {
        var list = Arrays.stream(ForceUnit.SIMPLE_MOVE)
                .mapToObj(item -> new Point2D.Double(0, item))
                .collect(Collectors.toList());
        forceList.add(new ForceUnit(list));
    }

    public boolean addRandomMovement() {
        var start = rnd.nextInt(RANDOM_MOVEMENT_FREQUENCY);
        if (start != 0) {
            return false;
        }

        var duration = rnd.nextInt(MAX_DURATION);
        var vx = MAX_VELOCITY - rnd.nextInt(2 * MAX_VELOCITY) - 1;
        var vy = MAX_VELOCITY - rnd.nextInt(2 * MAX_VELOCITY) - 1;
        var dist = new Point2D.Double(vx, vy).distance(ForceUnit.NO_MOVE);

        var list = IntStream.range(0, duration)
                .map(i -> rnd.nextInt(MAX_VELOCITY))
                .mapToObj(i -> new Point2D.Double(i * vx / dist, i * vy / dist))
                .collect(Collectors.toList());

        forceList.add(new ForceUnit(list));

        return true;
    }

    public boolean hasForces() {
        return forceList.size() > 0;
    }

    public Point2D.Double getVelocity() {
        var vx = 0D;
        var vy = 0D;
        var it = forceList.iterator();
        while (it.hasNext()) {
            var force = it.next();
            if (force.hasNext()) {
                var fv = force.next();
                vx += fv.getX();
                vy += fv.getY();
            } else {
                it.remove();
            }
        }
        return new Point2D.Double(vx, vy);
    }

    private static class ForceUnit {

        private final static double[] SIMPLE_MOVE = new double[] {
                1 * VELOCITY, 2 * VELOCITY, 3 * VELOCITY, 2 * VELOCITY, 1 * VELOCITY
        };

        private final static Point2D.Double NO_MOVE = new Point2D.Double(0, 0);

        private final List<Point2D.Double> velocity;
        private int state;

        public ForceUnit(List<Point2D.Double> velocity) {
            this.velocity = List.copyOf(velocity);
            state = 0;
        }

        public boolean hasNext() {
            return state < velocity.size();
        }

        public Point2D next() {
            if (hasNext()) {
                return velocity.get(state++);
            } else {
                return NO_MOVE;
            }
        }

    }

}
