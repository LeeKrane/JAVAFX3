package labor16;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
public class Lab16_Task2_Swapper_Test {
    public static final String PATH_TO_FXML = "/layouts/Lab16_Task2.fxml";
    private Button btnSwap;
    private Button btnClear;
    private TextField tfText1;
    private TextField tfText2;
    private Text txtState;

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
        btnSwap = marshaller.getById(Button.class, "swapButton");
        btnClear = marshaller.getById(Button.class, "clearButton");
        tfText1 = marshaller.getById(TextField.class, "textField1");
        tfText2 = marshaller.getById(TextField.class, "textField2");
        txtState = marshaller.getById(Text.class, "statusText");
    }

    @Test
    void initialState_textFieldsEmpty() {
        assertThat(tfText1.getText()).isEmpty();
        assertThat(tfText2.getText()).isEmpty();
    }

    @Test
    void initialState_stateHidden() {
        assertThat(txtState.isVisible()).isFalse();
    }

    @Test
    void clear_bothTextFieldsEmptyStateHidden() {
        btnClear.fire();

        assertThat(tfText1.getText()).isEmpty();
        assertThat(tfText2.getText()).isEmpty();
        assertThat(txtState.isVisible()).isFalse();
    }

    @Test
    void swapTextField2Empty_noSwap() {
        tfText1.setText("text");

        btnSwap.fire();

        assertThat(tfText1.getText()).isEqualTo("text");
        assertThat(tfText2.getText()).isEmpty();
        assertThat(txtState.isVisible()).isTrue();
    }

    @Test
    void swapTextField1Empty_noSwap() {
        tfText2.setText("text");

        btnSwap.fire();
        System.out.println(tfText1.getText());
        assertThat(tfText1.getText()).isEmpty();
        assertThat(tfText2.getText()).isEqualTo("text");
        assertThat(txtState.isVisible()).isTrue();
    }

    @Test
    void swapBothTextFieldsFilled_textsSwapped() {
        tfText1.setText("1");
        tfText2.setText("2");

        btnSwap.fire();

        assertThat(tfText1.getText()).isEqualTo("2");
        assertThat(tfText2.getText()).isEqualTo("1");
        assertThat(txtState.isVisible()).isTrue();
    }
}