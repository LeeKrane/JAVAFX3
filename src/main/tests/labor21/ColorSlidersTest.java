package labor21;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.testfx.assertions.api.Assertions.assertThat;

/**
 * Variablennamen im Controller:
 * cbRed, sliderRed, tfRed...
 */
@ExtendWith(ApplicationExtension.class)
public class ColorSlidersTest extends FxTest {
    private static final String PATH_TO_FXML = "/layouts/Lab21_ColorSliders.fxml";
    private static final String[] COLORS = new String[]{"Red", "Green", "Blue"};
    private final Map<String, CheckBox> checkBoxes = new HashMap<>();
    private final Map<String, ScrollBar> sliders = new HashMap<>();
    private final Map<String, TextField> textFields = new HashMap<>();
    private Label lblColor;
    private CheckBox cbGreyscale;

    @Start
    void initializeGUI(Stage primaryStage) throws IOException {
        startApplication(primaryStage, PATH_TO_FXML);
        Arrays.stream(COLORS).forEach(color -> {
            checkBoxes.put(color, getById("cb" + color));
            sliders.put(color, getById("slider" + color));
            textFields.put(color, getById("tf" + color));
        });
        lblColor = getById("lblColor");
        cbGreyscale = getById("cbGreyscale");
    }

    @TestFactory
    Stream<DynamicTest> boundaries_slidersBetween0And255() {
        return sliders.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            ScrollBar slider = e.getValue();

            assertThat(slider.getMin()).isEqualTo(0);
            assertThat(slider.getMax()).isEqualTo(255);
        }));
    }

    @TestFactory
    Stream<DynamicTest> initialState_colorCheckBoxesSelected() {
        return checkBoxes.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            CheckBox cb = e.getValue();

            assertThat(cb.isSelected()).isTrue();
        }));
    }

    @Test
    void initialState_greyscaleNotSelected() {
        assertThat(cbGreyscale.isSelected()).isFalse();
    }

    @TestFactory
    Stream<DynamicTest> initialState_slidersAt0() {
        return sliders.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            ScrollBar sb = e.getValue();

            assertThat(sb.getValue()).isEqualTo(0);
        }));
    }

    @TestFactory
    Stream<DynamicTest> initialState_textFieldsContain0() {
        return textFields.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            TextField tf = e.getValue();

            assertThat(tf).hasText("0");
        }));
    }

    @Test
    void initialState_labelText0s() {
        assertThat(lblColor).hasText("#000000");
    }

    @Test
    void initialState_labelBackgroundBlack() {
        assertThat(getLabelBackground()).isEqualTo(Color.BLACK);
    }

    private Paint getLabelBackground() {
        return lblColor.getBackground()
                .getFills()
                .get(0)
                .getFill();
    }

    @TestFactory
    Stream<DynamicTest> colorCheckBoxUnselected_sliderInvisible(FxRobot robot) {
        return checkBoxes.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            CheckBox cb = e.getValue();
            ScrollBar slider = sliders.get(e.getKey());

            robot.interact(cb::fire);

            assertThat(slider).isInvisible();
        }));
    }

    @TestFactory
    Stream<DynamicTest> colorCheckBoxUnselected_textFieldInvisible(FxRobot robot) {
        return checkBoxes.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            CheckBox cb = e.getValue();
            TextField tf = textFields.get(e.getKey());

            robot.interact(cb::fire);

            assertThat(tf).isInvisible();
        }));
    }

    @TestFactory
    Stream<DynamicTest> colorCheckBoxUnselected_color0forLabel(FxRobot robot) {
        return checkBoxes.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            ScrollBar slider = sliders.get(e.getKey());
            robot.interact(() -> slider.setValue(100));
            CheckBox cb = e.getValue();

            robot.interact(cb::fire);

            assertThat(lblColor).hasText("#000000");
            assertThat(getLabelBackground()).isEqualTo(Color.BLACK);
        }));
    }

    @TestFactory
    Stream<DynamicTest> colorCheckBoxUnselectedAndSelectedAgain_stateRestored(FxRobot robot) {
        return checkBoxes.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            ScrollBar slider = sliders.get(e.getKey());
            robot.interact(() -> slider.setValue(100));
            CheckBox cb = e.getValue();
            robot.interact(cb::fire);

            robot.interact(cb::fire);

            assertThat(slider.getValue()).isEqualTo(100);
        }));
    }

    @TestFactory
    Stream<DynamicTest> sliderMoved_textFieldUpdated(FxRobot robot) {
        return sliders.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            ScrollBar slider = e.getValue();
            TextField tf = textFields.get(e.getKey());

            robot.interact(() -> slider.setValue(100));

            assertThat(tf).hasText("100");
        }));
    }

    @Test
    void sliderRedMoved_labelUpdated(FxRobot robot) {
        ScrollBar slider = sliders.get("Red");
        robot.interact(() -> sliders.get("Green").setValue(150));
        robot.interact(() -> sliders.get("Blue").setValue(200));
        Color expectedColor = Color.rgb(100, 150, 200);

        robot.interact(() -> slider.setValue(100));

        assertThat(lblColor).hasText("#6496c8");
        assertThat(getLabelBackground()).isEqualTo(expectedColor);
    }

    @Test
    void sliderGreenMoved_labelUpdated(FxRobot robot) {
        ScrollBar slider = sliders.get("Green");
        robot.interact(() -> sliders.get("Red").setValue(150));
        robot.interact(() -> sliders.get("Blue").setValue(200));
        Color expectedColor = Color.rgb(150, 100, 200);

        robot.interact(() -> slider.setValue(100));

        assertThat(lblColor).hasText("#9664c8");
        assertThat(getLabelBackground()).isEqualTo(expectedColor);
    }

    @Test
    void sliderBlueMoved_labelUpdated(FxRobot robot) {
        ScrollBar slider = sliders.get("Blue");
        robot.interact(() -> sliders.get("Red").setValue(150));
        robot.interact(() -> sliders.get("Green").setValue(200));
        Color expectedColor = Color.rgb(150, 200, 100);

        robot.interact(() -> slider.setValue(100));

        assertThat(lblColor).hasText("#96c864");
        assertThat(getLabelBackground()).isEqualTo(expectedColor);
    }

    @TestFactory
    Stream<DynamicTest> numberEntered_sliderUpdated(FxRobot robot) {
        return textFields.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            TextField tf = e.getValue();
            ScrollBar slider = sliders.get(e.getKey());

            robot.interact(tf::requestFocus)
                    .write("100");

            assertThat(slider.getValue()).isEqualTo(100);
        }));
    }

    @TestFactory
    Stream<DynamicTest> numberEnteredAndDeleted_sliderUpdated(FxRobot robot) {
        return textFields.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            TextField tf = e.getValue();
            ScrollBar slider = sliders.get(e.getKey());

            robot.interact(tf::requestFocus)
                    .write('3');
            assumeThat(slider.getValue()).isEqualTo(3);

            robot.interact(tf::requestFocus)
                    .eraseText(1);
            assertThat(tf).hasText("");
            assertThat(slider.getValue()).isEqualTo(0);
        }));
    }

    @TestFactory
    Stream<DynamicTest> textEntered_errorMessageDisplayed(FxRobot robot) {
        return textFields.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            TextField tf = e.getValue();

            robot.interact(tf::requestFocus)
                    .write("NaN");

            List<Window> windows = robot.listWindows();
            assertThat(windows).hasSizeGreaterThan(1);
        }));
    }

    @TestFactory
    Stream<DynamicTest> textEntered_textFieldCleared(FxRobot robot) {
        return textFields.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            TextField tf = e.getValue();

            robot.interact(tf::requestFocus)
                    .write("NaN");;

            Stage stage = (Stage) robot.listWindows().get(1);
            prepareDialogForClosing(robot, stage);
            robot.interact(stage::close);

            assertThat(tf).hasText("");
        }));
    }

    @TestFactory
    Stream<DynamicTest> numberExceedingMaxEntered_errorMessageDisplayed(FxRobot robot) {
        return textFields.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            TextField tf = e.getValue();

            robot.interact(tf::requestFocus);
            robot.write("256");

            List<Window> windows = robot.listWindows();
            assertThat(windows).hasSizeGreaterThan(1);
        }));
    }

    @Test
    void greyScaleSelected_colorCheckBoxesDisabled(FxRobot robot) {
        robot.interact(cbGreyscale::fire);

        assertThat(checkBoxes.values()).allMatch(CheckBox::isDisabled);
    }

    @Test
    void greyScaleSelected_slidersSetToMean(FxRobot robot) {
        robot.interact(() -> sliders.get("Red").setValue(30));
        robot.interact(() -> sliders.get("Green").setValue(40));
        robot.interact(() -> sliders.get("Blue").setValue(80));

        robot.interact(cbGreyscale::fire);

        assertThat(sliders.values()).allMatch(slider -> slider.getValue() == 50);
    }

    @TestFactory
    Stream<DynamicTest> greyScaleSelected_slidersSynched(FxRobot robot) {
        return sliders.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            robot.interact(cbGreyscale::fire);
            ScrollBar selectedSlider = e.getValue();

            robot.interact(() -> selectedSlider.setValue(100));

            assertThat(sliders.values()).allMatch(slider -> slider.getValue() == 100);
        }));
    }

    @TestFactory
    Stream<DynamicTest> greyScaleSelected_textFieldSetsAllSliders(FxRobot robot) {
        return textFields.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            robot.interact(cbGreyscale::fire);
            TextField tf = e.getValue();

            robot.interact(tf::requestFocus)
                    .write("100");

            assertThat(sliders.values()).allMatch(slider -> slider.getValue() == 100);
        }));
    }

    @Test
    void greyScaleSelectedAndDeselected_checkBoxesEnabled(FxRobot robot) {
        robot.interact(cbGreyscale::fire);

        robot.interact(cbGreyscale::fire);

        assertThat(checkBoxes.values()).noneMatch(CheckBox::isDisabled);
    }

    @TestFactory
    Stream<DynamicTest> greyScaleSelectedAndDeselected_slidersOperateIndependently(FxRobot robot) {
        return sliders.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            robot.interact(cbGreyscale::fire);
            ScrollBar selectedSlider = e.getValue();
            robot.interact(() -> selectedSlider.setValue(100));
            robot.interact(cbGreyscale::fire);
            assumeThat(sliders.values()).allMatch(slider -> slider.getValue() == 100);

            robot.interact(() -> selectedSlider.setValue(200));

            assertThat(sliders.values())
                    .filteredOn(slider -> slider != selectedSlider)
                    .allMatch(slider -> slider.getValue() == 100);
        }));
    }
}
