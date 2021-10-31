import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.image.ScreenshotTaker;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.SwingTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.swing.SwingComponent;
import ru.spb.altercom.SharpShooter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;

import static java.util.stream.IntStream.range;
import static org.hyperskill.hstest.testcase.CheckResult.correct;
import static ru.spb.altercom.Controller.TARGET_CENTER;
import static ru.spb.altercom.Controller.TARGET_CIRCLE_STEP;

public class SharpShooterTest extends SwingTest {

    public SharpShooterTest() {
        super(new SharpShooter());
    }

    private BufferedImage screenshot;

    @SwingComponent
    private JPanelFixture canvas;

    @SwingComponent
    private JLabelFixture statusbar;

    public void takeScreenshot() {
        if (screenshot == null) {
            var screenshotTaker = new ScreenshotTaker();
            screenshot = screenshotTaker.takeScreenshotOf(canvas.target());
        }
    }


    @DynamicTest(feedback = "The Statusbar should be visible.")
    CheckResult test1() {

        requireVisible(statusbar);

        assertEquals(false, statusbar.text().isEmpty(),
                "The Statusbar should have some text");

        return correct();
    }

    @DynamicTest(feedback = "The Canvas should be visible and should be focused.")
    CheckResult test2() {

        requireVisible(canvas);

        canvas.requireFocused();

        return correct();
    }

    @DynamicTest(feedback = "The Canvas should contain the whole Target object.")
    CheckResult test3() {

        takeScreenshot();

        final var MIN_WIDTH = TARGET_CENTER + 10 * TARGET_CIRCLE_STEP;

        assertEquals(MIN_WIDTH <= screenshot.getWidth() &&
                             MIN_WIDTH <= screenshot.getHeight() , true,
                "The Canvas width should be at least TARGET_CENTER + 10 * TARGET_CIRCLE_STEP = {0}.",
                TARGET_CENTER + 10 * TARGET_CIRCLE_STEP);

        return correct();
    }

    @DynamicTest(feedback = "The Target should have Color.DARK_GRAY background.")
    CheckResult test4() {

        takeScreenshot();

        final var X_TEST = 10;
        final var Y_TEST = 10;

        assertEquals(Color.DARK_GRAY.getRGB(), screenshot.getRGB(X_TEST, Y_TEST),
                "The point at ({0}, {1}) should have Color.DARK_GRAY.", X_TEST, Y_TEST);

        return correct();
    }


    private static final Color[] CIRCLE_COLORS = new Color[] {
            Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
            Color.BLACK, Color.WHITE, Color.WHITE, Color.WHITE, Color.BLACK};

    @DynamicTest(feedback = "The Target should have Color.BLACK and Color.WHITE concentric circles. " +
                            "Center point of the Target should be at (" + TARGET_CENTER + ", " + TARGET_CENTER + ").")
    CheckResult test5() {

        takeScreenshot();

        range(1, 10).forEach(index -> {
            var x = (int) (TARGET_CENTER + (9 - index) * TARGET_CIRCLE_STEP * Math.PI / 4)
                  + (int) (index < 9 ? 0.5 * TARGET_CIRCLE_STEP * Math.PI / 4 : 0);
            var y = (int) (TARGET_CENTER + (9 - index) * TARGET_CIRCLE_STEP * Math.PI / 4)
                  + (int) (index < 9 ? 0.5 * TARGET_CIRCLE_STEP * Math.PI / 4 : 0);

            assertEquals(CIRCLE_COLORS[index].getRGB(), screenshot.getRGB(x, y),
                    "The {0} outer circle should be a {1} color. ({2}, {3}) point was tested.",
                    index + 1, CIRCLE_COLORS[index].equals(Color.BLACK) ? "Color.BLACK" : "Color.WHITE", x, y);
        });

        return correct();
    }

    @DynamicTest(feedback = "Put the center of the bullet hole at the point (300, 500).")
    CheckResult test6() {

        takeScreenshot();

        final var BULLET_X = 300;
        final var BULLET_Y = 500;

        assertEquals(Color.BLACK.getRGB() == screenshot.getRGB(BULLET_X, BULLET_Y), false,
                "Bullet color is not Color.BLACK.");
        assertEquals(Color.WHITE.getRGB() == screenshot.getRGB(BULLET_X, BULLET_Y), false,
                "Bullet color is not Color.WHITE.");
        assertEquals(Color.DARK_GRAY.getRGB() == screenshot.getRGB(BULLET_X, BULLET_Y), false,
                "Bullet color is not Color.DARK_GRAY.");

        return correct();
    }

    // TODO: The sight should stay on screen

    private static void assertEquals(
        final Object expected,
        final Object actual,
        final String error,
        final Object... args) {

        if (!expected.equals(actual)) {
            final var feedback = MessageFormat.format(error, args);
            throw new WrongAnswer(feedback);
        }
    }
}
