package labor16;

import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(ApplicationExtension.class)
public class Lab16_Task1_TextCombiner_Test {
    public static final String PATH_TO_FXML = "/layouts/Lab16_Task1.fxml";
    private RadioButton rbOrderAs12;
    private RadioButton rbOrderAs21;
    private TextField tfText1;
    private TextField tfText2;
    private TextField tfResult;
    private Button btnApply;
    private Button btnClear;

    @BeforeAll
    static void setFailOnError() {
        Thread.setDefaultUncaughtExceptionHandler((t, error) -> fail(error));
    }

    @Start
    private void loadUi(Stage primaryStage) throws IOException {
        FxMarshaller marshaller = new FxMarshaller(primaryStage, PATH_TO_FXML);
        initializeNodes(marshaller);
    }

    private void initializeNodes(FxMarshaller marshaller) {
        rbOrderAs12 = marshaller.getById(RadioButton.class, "variant1");
        rbOrderAs21 = marshaller.getById(RadioButton.class, "variant2");
        tfText1 = marshaller.getById(TextField.class, "text1");
        tfText2 = marshaller.getById(TextField.class, "text2");
        tfResult = marshaller.getById(TextField.class, "output");
        btnApply = marshaller.getById(Button.class, "apply");
        btnClear = marshaller.getById(Button.class, "clear");
    }

    @Test
    void initialState_radioButtonsBelongToSameGroup() {
        assertThat(rbOrderAs12.getToggleGroup())
                .isSameAs(rbOrderAs21.getToggleGroup());
    }

    @Test
    void initialState_oneRadioButtonSelected() {
        assertThat(rbOrderAs12.getToggleGroup().getSelectedToggle()).isNotNull();
    }

    @Test
    void initialState_textFieldsEmpty() {
        assertThat(tfText1.getText()).isEmpty();
        assertThat(tfText2.getText()).isEmpty();
    }

    @Test
    void initialState_resultNotEditable() {
        assertThat(tfResult.isEditable()).isFalse();
    }

    @Test
    void applyTextField1Empty_resultError() {
        tfText2.setText("text");

        btnApply.fire();

        assertThat(tfResult.getText()).isNotEmpty()
                .isNotEqualTo("text");
    }

    @Test
    void applyTextField2Empty_resultError() {
        tfText2.setText("text");

        btnApply.fire();

        assertThat(tfResult.getText()).isNotEmpty()
                .isNotEqualTo("text");
    }

    @Test
    void applyBothTextFieldsEmpty_resultError() {
        btnApply.fire();

        assertThat(tfResult.getText()).isNotEmpty();
    }

    @Test
    void applyBothTextFieldsFilledOrderAs12_resultIsCombination() {
        rbOrderAs12.fire();
        tfText1.setText("1");
        tfText2.setText("2");

        btnApply.fire();

        assertThat(tfResult.getText()).isEqualTo("Result: 12");
    }

    @Test
    void applyBothTextFieldsFilledOrderAs21_resultIsCombination() {
        rbOrderAs21.fire();
        tfText1.setText("1");
        tfText2.setText("2");

        btnApply.fire();

        assertThat(tfResult.getText()).isEqualTo("Result: 21");
    }

    @Test
    void clear_clearsAllTextFields() {
        tfText1.setText("1");
        tfText2.setText("2");
        tfResult.setText("Result: 12");

        btnClear.fire();

        assertThat(tfText1.getText()).isEmpty();
        assertThat(tfText2.getText()).isEmpty();
        assertThat(tfResult.getText()).isEmpty();
    }
}