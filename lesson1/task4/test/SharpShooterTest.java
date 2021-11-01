import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.image.ScreenshotTaker;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.SwingTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.swing.SwingComponent;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;

import static java.awt.event.KeyEvent.*;
import static org.hyperskill.hstest.testcase.CheckResult.correct;

public class SharpShooterTest extends SwingTest {

  public SharpShooterTest() {
    super(new ru.spb.altercom.SharpShooter());
  }

  private static final int TARGET_CIRCLE_STEP = 30;
  private static final int TARGET_CENTER = 350;
  private static final int SIGHT_RADIUS = 40;
  private static final int MOVE_STEP = 10;

  private static final int START_X = 100;
  private static final int START_Y = 100;

  @SwingComponent
  private JPanelFixture canvas;

  @SwingComponent
  private JLabelFixture statusbar;


  @DynamicTest(feedback = "It should be difficult to score 10 points.")
  CheckResult test1() {

      canvas.pressKey(VK_SPACE);

      for (int i = START_X; i < TARGET_CENTER; i += MOVE_STEP) {
          canvas.pressKey(VK_RIGHT);
      }
      for (int i = START_Y; i < TARGET_CENTER; i += MOVE_STEP) {
          canvas.pressKey(VK_DOWN);
      }

      canvas.pressKey(VK_SPACE);

      assertEquals(false,
        statusbar.text().toLowerCase().contains("your score: " + 10),
        "It should be difficult to score 10 points." );

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
