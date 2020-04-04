package labor20;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.testfx.assertions.api.Assertions.assertThat;


@ExtendWith(ApplicationExtension.class)
public class TextCombinerTest extends FxTest {
    public static final String PATH_TO_FXML = "/layouts/Lab20_TextCombiner2.fxml";
    private RadioButton rbOrderAs12;
    private RadioButton rbOrderAs21;
    private final TextField[] textFields = new TextField[2];
    private TextField tfResult;
    private Button btnClear;

    @Start
    void initializeGUI(Stage primaryStage) throws IOException {
        startApplication(primaryStage, PATH_TO_FXML);
        rbOrderAs12 = getById("rb12");
        rbOrderAs21 = getById("rb21");
        textFields[0] = getById("tf1");
        textFields[1] = getById("tf2");
        tfResult = getById("tfOutput");
        btnClear = getById("btnClear");
    }

    @Test
    void initialState_radioButtonsBelongToSameGroup() {
        assertThat(rbOrderAs12.getToggleGroup()).isSameAs(rbOrderAs21.getToggleGroup());
    }

    @Test
    void initialState_oneRadioButtonSelected() {
        assertThat(rbOrderAs12.getToggleGroup().getSelectedToggle()).isNotNull();
    }

    @TestFactory
    Stream<DynamicTest> initialState_textFieldsEmpty() {
        return Arrays.stream(textFields).map(tf -> DynamicTest.dynamicTest(tf.getId(), () ->
                assertThat(tf).hasText("")));
    }

    @Test
    void initialState_resultNotEditable() {
        assertThat(tfResult.isEditable()).isFalse();
    }

    @TestFactory
    Stream<DynamicTest> textFieldEmpty_resultError(FxRobot robot) {
        return Arrays.stream(textFields).map(tf -> DynamicTest.dynamicTest(tf.getId(), () -> {
            robot.interact(tf::requestFocus);

            robot.write("text");

            assertThat(tfResult.getText()).isNotEmpty()
                    .isNotEqualTo("text");
        }));
    }

    @Test
    void bothTextFieldsEmpty_resultError() {
        assertThat(tfResult.getText()).isNotEmpty();
    }

    @Test
    void bothTextFieldsFilledOrderAs12_resultIsCombination(FxRobot robot) {
        robot.interact(rbOrderAs12::fire);

        robot.interact(textFields[0]::requestFocus);
        robot.write('1');
        robot.interact(textFields[1]::requestFocus);
        robot.write('2');

        assertThat(tfResult).hasText("Result: 12");
    }

    @Test
    void bothTextFieldsFilledOrderAs21_resultIsCombination(FxRobot robot) {
        robot.interact(rbOrderAs21::fire);

        robot.interact(textFields[0]::requestFocus);
        robot.write('1');
        robot.interact(textFields[1]::requestFocus);
        robot.write('2');

        assertThat(tfResult).hasText("Result: 21");
    }

    @Test
    void clear_clearsAllTextFields(FxRobot robot) {
        robot.interact(textFields[0]::requestFocus);
        robot.write('1');
        robot.interact(textFields[1]::requestFocus);
        robot.write('2');

        robot.interact(btnClear::fire);

        assertThat(textFields).allMatch(tf -> tf.getText().isEmpty());
        assertThat(tfResult.getText()).isNotBlank();
    }
}