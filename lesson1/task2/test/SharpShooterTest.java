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

import static java.awt.event.KeyEvent.*;
import static org.hyperskill.hstest.testcase.CheckResult.correct;

public class SharpShooterTest extends SwingTest {

    private static final int TARGET_CIRCLE_STEP = 30;
    private static final int TARGET_CENTER = 350;
    private static final int SIGHT_RADIUS = 40;
    private static final int MOVE_STEP = 10;

    public SharpShooterTest() {
        super(new SharpShooter());
    }

    @SwingComponent
    private JPanelFixture canvas;

    @DynamicTest(feedback = "Shoot at the center of the target.")
    CheckResult test1() {

        canvas.pressKey(VK_SPACE);

        var screenshot = takeScreenshot();
        assertEquals(true,
                checkBullet(screenshot, TARGET_CENTER, TARGET_CENTER),
                "The point ({0}, {1}) was tested.", TARGET_CENTER, TARGET_CENTER);

        return correct();
    }

    @DynamicTest(feedback = "Make 10 moves to the right and shoot.")
    CheckResult test2() {

        for (int i = 0; i < 10; i++) {
            canvas.pressKey(VK_RIGHT);
        }
        canvas.pressKey(VK_SPACE);

        var screenshot = takeScreenshot();
        assertEquals(true,
                checkBullet(screenshot, TARGET_CENTER, TARGET_CENTER),
                "The point ({0}, {1) was tested.", TARGET_CENTER, TARGET_CENTER);

        assertEquals(true,
                checkBullet(screenshot, 10 * MOVE_STEP + TARGET_CENTER, TARGET_CENTER),
                "The point ({0}, {1}) was tested.", 10 * MOVE_STEP + TARGET_CENTER, TARGET_CENTER);

        return correct();
    }

    @DynamicTest(feedback = "Make 10 moves to the up and shoot.")
    CheckResult test3() {

        for (int i = 0; i < 10; i++) {
            canvas.pressKey(VK_UP);
        }
        canvas.pressKey(VK_SPACE);

        var screenshot = takeScreenshot();
        assertEquals(true,
                checkBullet(screenshot, TARGET_CENTER, TARGET_CENTER),
                "The point ({0}, {1}) was tested.", TARGET_CENTER, TARGET_CENTER);

        assertEquals(true,
                checkBullet(screenshot, 10 * MOVE_STEP + TARGET_CENTER, TARGET_CENTER),
                "The point ({0}, {1}) was tested.",10 * MOVE_STEP + TARGET_CENTER, TARGET_CENTER);

        assertEquals(true,
                checkBullet(screenshot, 10 * MOVE_STEP + TARGET_CENTER, TARGET_CENTER - 10 * MOVE_STEP),
                "The point ({0}, {1}) was tested.", 10 * MOVE_STEP + TARGET_CENTER, TARGET_CENTER - 10 * MOVE_STEP);

        return correct();
    }

    @DynamicTest(feedback = "Make 20 moves to the left and shoot.")
    CheckResult test4() {

        for (int i = 0; i < 20; i++) {
            canvas.pressKey(VK_LEFT);
        }
        canvas.pressKey(VK_SPACE);

        var screenshot = takeScreenshot();
        assertEquals(true,
                checkBullet(screenshot, TARGET_CENTER, TARGET_CENTER),
                "The point ({0}, {1}) was tested.", TARGET_CENTER, TARGET_CENTER);

        assertEquals(true,
                checkBullet(screenshot, 10 * MOVE_STEP + TARGET_CENTER, TARGET_CENTER),
                "The point ({0}, {1}) was tested.",10 * MOVE_STEP + TARGET_CENTER, TARGET_CENTER);

        assertEquals(true,
                checkBullet(screenshot, 10 * MOVE_STEP + TARGET_CENTER, TARGET_CENTER - 10 * MOVE_STEP),
                "The point ({0}, {1}) was tested.", 10 * MOVE_STEP + TARGET_CENTER, TARGET_CENTER - 10 * MOVE_STEP);

        assertEquals(true,
                checkBullet(screenshot, TARGET_CENTER - 10 * MOVE_STEP, TARGET_CENTER - 10 * MOVE_STEP),
                "The point ({0}, {1}) was tested.", TARGET_CENTER - 10 * MOVE_STEP, TARGET_CENTER - 10 * MOVE_STEP);

        return correct();
    }

    @DynamicTest(feedback = "Make 20 moves to the down and shoot.")
    CheckResult test5() {

        for (int i = 0; i < 20; i++) {
            canvas.pressKey(VK_DOWN);
        }
        canvas.pressKey(VK_SPACE);

        var screenshot = takeScreenshot();
        assertEquals(true,
                checkBullet(screenshot, 350, 350),
                "The point (350, 350) was tested.");

        assertEquals(true,
                checkBullet(screenshot, 450, 350),
                "The point (250, 350) was tested.");

        assertEquals(true,
                checkBullet(screenshot, 450, 250),
                "The point (450, 250) was tested.");

        assertEquals(true,
                checkBullet(screenshot, 250, 250),
                "The point (250, 250) was tested.");

        assertEquals(true,
                checkBullet(screenshot, 250, 450),
                "The point (250, 450) was tested.");

        return correct();
    }

    @DynamicTest(feedback = "The cursor should not be outside the screen.")
    CheckResult test6() {
        for (int i = 0; i < 50; i++) {
            canvas.pressKey(VK_DOWN);
        }
        for (int i = 0; i < 50; i++) {
            canvas.pressKey(VK_RIGHT);
        }
        canvas.pressKey(VK_SPACE);

        var screenshot = takeScreenshot();
        assertEquals(true,
                checkBullet(screenshot, 2 * TARGET_CENTER - SIGHT_RADIUS, 2 * TARGET_CENTER - SIGHT_RADIUS),
                "The point ({0}, {1}) was tested.", 2 * TARGET_CENTER - SIGHT_RADIUS, 2 * TARGET_CENTER - SIGHT_RADIUS);
        return correct();
    }

    @DynamicTest(feedback = "The cursor should not be outside the screen.")
    CheckResult test7() {
        for (int i = 0; i < 80; i++) {
            canvas.pressKey(VK_UP);
        }
        canvas.pressKey(VK_SPACE);

        var screenshot = takeScreenshot();
        assertEquals(true,
                checkBullet(screenshot, 2 * TARGET_CENTER - SIGHT_RADIUS, SIGHT_RADIUS),
                "The point ({0}, {1}) was tested.", 2 * TARGET_CENTER - SIGHT_RADIUS, SIGHT_RADIUS);
        return correct();
    }

    @DynamicTest(feedback = "The cursor should not be outside the screen.")
    CheckResult test8() {
        for (int i = 0; i < 80; i++) {
            canvas.pressKey(VK_LEFT);
        }
        canvas.pressKey(VK_SPACE);

        var screenshot = takeScreenshot();
        assertEquals(true,
                checkBullet(screenshot, SIGHT_RADIUS, SIGHT_RADIUS),
                "The point ({0}, {1}) was tested.", SIGHT_RADIUS, SIGHT_RADIUS);
        return correct();
    }

    @DynamicTest(feedback = "The cursor should not be outside the screen.")
    CheckResult test9() {
        for (int i = 0; i < 80; i++) {
            canvas.pressKey(VK_DOWN);
        }
        canvas.pressKey(VK_SPACE);

        var screenshot = takeScreenshot();
        assertEquals(true,
                checkBullet(screenshot, SIGHT_RADIUS, 2 * TARGET_CENTER - SIGHT_RADIUS),
                "The point ({0}, {1}) was tested.", SIGHT_RADIUS, 2 * TARGET_CENTER - SIGHT_RADIUS);
        return correct();
    }

    private BufferedImage takeScreenshot() {
        var screenshotTaker = new ScreenshotTaker();
        return screenshotTaker.takeScreenshotOf(canvas.target());
    }

    private boolean checkBullet(BufferedImage screenshot, int x, int y) {
        return Color.BLACK.getRGB() != screenshot.getRGB(x, y) &&
                Color.WHITE.getRGB() != screenshot.getRGB(x, y) &&
                Color.DARK_GRAY.getRGB() != screenshot.getRGB(x, y);
    }

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
