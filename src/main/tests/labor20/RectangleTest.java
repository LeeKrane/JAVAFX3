package labor20;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class RectangleTest extends FxTest {
    public static final String PATH_TO_FXML = "/layouts/Lab20_Rectangle.fxml";
    private Text txtArea;
    private Slider sliderWidth;
    private Slider sliderHeight;
    private ChoiceBox<Color> choiceColor;
    private Rectangle rectangle;

    @Start
    void initializeGUI(Stage primaryStage) throws IOException {
        startApplication(primaryStage, PATH_TO_FXML);
        txtArea = getById("txtArea");
        sliderWidth = getById("sliderWidth");
        sliderHeight = getById("sliderHeight");
        choiceColor = getById("choiceColor");
        rectangle = getById("rectangle");
    }

    @Test
    void initialState_rectanglePropertiesAsSpecified() {
        Color expected = choiceColor.getSelectionModel().getSelectedItem();
        assertThat(rectangle.getFill()).isEqualTo(expected);
        assertThat(rectangle.getWidth()).isEqualTo(sliderWidth.getValue());
        assertThat(rectangle.getHeight()).isEqualTo(sliderHeight.getValue());
    }

    @Test
    void initialState_areaContainsAreaOfRectangle() {
        double expectedArea = rectangle.getHeight() * rectangle.getWidth();
        String expected = format(expectedArea);

        assertThat(txtArea).hasText(expected);
    }

    private String format(double expectedArea) {
        return String.format("%.0f E²", expectedArea);
    }

    @Test
    void initialState_choiceHasAtLeast3ColorsIncludingHtlGreen() {
        assertThat(choiceColor.getItems())
                .hasSizeGreaterThan(2)
                .contains(Color.valueOf("#9cce2b"));
    }

    @Test
    void choiceChanged_rectangleFillChanged(FxRobot robot) {
        robot.interact(choiceColor.getSelectionModel()::selectNext);
        Color expected = choiceColor.getSelectionModel().getSelectedItem();

        assertThat(rectangle.getFill()).isEqualTo(expected);
    }

    @Test
    void widthChanged_rectangleWidthChanged(FxRobot robot) {
        robot.interact(() -> sliderWidth.setValue(100));

        assertThat(rectangle.getWidth()).isEqualTo(100);
    }

    @Test
    void heightChanged_rectangleHeightChanged(FxRobot robot) {
        robot.interact(() -> sliderHeight.setValue(100));

        assertThat(rectangle.getHeight()).isEqualTo(100);
    }

    @Test
    void heightChanged_areaChanged(FxRobot robot) {
        double expectedArea = rectangle.getWidth();
        expectedArea *= 100;
        String expected = format(expectedArea);

        robot.interact(() -> sliderHeight.setValue(100));

        assertThat(txtArea).hasText(expected);
    }

    @Test
    void widthChanged_areaChanged(FxRobot robot) {
        double expectedArea = rectangle.getHeight();
        expectedArea *= 100;
        String expected = format(expectedArea);

        robot.interact(() -> sliderWidth.setValue(100));

        assertThat(txtArea).hasText(expected);
    }
}