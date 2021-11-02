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

import static java.awt.event.KeyEvent.*;
import static org.hyperskill.hstest.testcase.CheckResult.correct;

public class SharpShooterTest extends SwingTest {

    private static final int TARGET_CIRCLE_STEP = 30;
    private static final int TARGET_CENTER = 350;
    private static final int SIGHT_RADIUS = 40;
    private static final int MOVE_STEP = 10;
    private final static int MAX_SHOOTING_ATTEMPTS = 12;

    public SharpShooterTest() {
      super(new SharpShooter());
    }

    @SwingComponent
    private JLabelFixture statusbar;

    @SwingComponent
    private JPanelFixture canvas;

    @DynamicTest(feedback = "At start the Statusbar should contain: " +
                            "Press SPACE button to start the game.")
    CheckResult test1() {

        // before the game start the sight should be immovable
        for (int i = 0; i < 10; i++) {
            canvas.pressKey(VK_LEFT);
        }

        assertEquals(true,
            statusbar.text().toLowerCase().contains("press space button to start the game"),
            "Check statusbar text before game start.");

        return correct();
    }

    @DynamicTest(feedback = "After start the Statusbar should contain: " +
            "Shootings left: " + MAX_SHOOTING_ATTEMPTS + ", Your score: 0")
    CheckResult test2() {

        canvas.pressKey(VK_SPACE);

        assertEquals(false,
                statusbar.text().toLowerCase().contains("press space button to start the game."),
                "Check statusbar text immediately after the game start.");

        assertEquals(true,
                statusbar.text().toLowerCase().contains("shootings left: " + MAX_SHOOTING_ATTEMPTS),
                "Wrong calculation of the attempts number.");

        assertEquals(true,
                statusbar.text().toLowerCase().contains("your score: 0"),
                "Wrong calculation of the score.");

        return correct();
    }

    private final String[][] SCORE_DATA = {
        {"1",  "1", "0", "11", "10"},
        {"2",  "1", "4", "10", "19"},
        {"3",  "1", "3",  "9", "27"},
        {"4",  "1", "3",  "8", "34"},
        {"5",  "1", "3",  "7", "40"},
        {"6",  "1", "3",  "6", "45"},
        {"7",  "1", "3",  "5", "49"},
        {"8",  "1", "3",  "4", "52"},
        {"9",  "1", "3",  "3", "54"},
        {"10", "1", "3",  "2", "55"},
        {"11", "1", "3",  "1", "55"}
    };

    @DynamicTest(data = "SCORE_DATA", feedback = "Wrong calculation of the attempts number or score.")
    CheckResult test3(final String number, final String spacePress, final String upPress,
                      final String attempts, final String score) {

        for (int i = 0; i < Integer.parseInt(upPress); i++) {
            canvas.pressKey(VK_UP);
        }

        for (int i = 0; i < Integer.parseInt(spacePress); i++) {
            canvas.pressKey(VK_SPACE);
        }

        assertEquals(true,
            statusbar.text().toLowerCase().contains("shootings left: " + attempts),
        "Wrong calculation of the attempts number. " +
             "After {0} shoot it should be: {1}.", number, attempts);

        assertEquals(true,
            statusbar.text().toLowerCase().contains("your score: " + score),
        "Wrong calculation of the score. " +
             "After {0} shoot it should be: {1}.", number, attempts);

        return correct();
    }

    @DynamicTest(feedback = "After 12nd shooting the game should be over.")
    CheckResult test4() {

        canvas.pressKey(VK_SPACE);

        assertEquals(false,
                statusbar.text().toLowerCase().contains("shootings left:"),
                "Check statusbar text after the end of the game.");

        assertEquals(true,
                statusbar.text().toLowerCase().contains("game over"),
                "Status bar should contain: Game over.");

        assertEquals(true,
                statusbar.text().toLowerCase().contains("your score: 55"),
                "Status bar should contain: Your score: 55");

        return correct();
    }

    private final int[] BULLET_HOLES = new int[] {0, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31};

    @DynamicTest(data = "BULLET_HOLES", feedback = "After the end of the game the sight should have 11 bullet holes.")
    CheckResult test5(final int dy) {

        var screenshot = takeScreenshot();

        var x = TARGET_CENTER;
        var y = TARGET_CENTER - dy * MOVE_STEP;

        assertEquals(true,
                checkBullet(screenshot, x, y),
                "Expected a bullet hole at point ({0}, {1})", x, y);

        return correct();
    }


    @DynamicTest(feedback = "After the end of the game the sight should be immovable.")
    CheckResult test6() {
        for (int i = 0; i < 10; i++) {
            canvas.pressKey(VK_RIGHT);
        }
        canvas.pressKey(VK_SPACE);

        var screenshot = takeScreenshot();

        var x = TARGET_CENTER - 10 * MOVE_STEP;
        var y = 40;

        assertEquals(false,
                checkBullet(screenshot, x, y),
                "Not expected a bullet hole at point ({0}, {1})", x, y);

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

    // TODO: The game graph

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
