package labor19;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(ApplicationExtension.class)
public class DictionaryAppTest {
    public static final String PATH_TO_FXML = "/layouts/Lab19_Dictionary.fxml";
    private TextField textField;
    private ListView<String> lvPermutations;
    private ListView<String> lvSameLetters;

    @BeforeAll
    static void setFailOnError() {
        Thread.setDefaultUncaughtExceptionHandler((t, error) -> fail(error));
    }

    @Start
    private void loadUi(Stage primaryStage) throws IOException {
        FxMarshaller marshaller = new FxMarshaller(primaryStage, PATH_TO_FXML);
        initializeNodes(marshaller);
    }

    @SuppressWarnings("unchecked")
    private void initializeNodes(FxMarshaller marshaller) {
        textField = marshaller.getById(TextField.class, "textfield");
        lvPermutations = marshaller.getById(ListView.class, "lvPermutations");
        lvSameLetters = marshaller.getById(ListView.class, "lvSameLetters");
    }

    @Test
    void initalState_allEmpty() {
        assertThat(textField.getText().isEmpty()).isTrue();
        assertThat(lvPermutations.getItems().isEmpty()).isTrue();
        assertThat(lvSameLetters.getItems().isEmpty()).isTrue();
    }

    @Test
    void textEntered_listsChange(FxRobot robot) {
        robot.interact(textField::requestFocus);
        robot.write('e');

        assertThat(lvPermutations.getItems()).isEqualTo(List.of("e"));
        assertThat(lvSameLetters.getItems()).isEqualTo(List.of("e"));

        robot.write('i');

        assertThat(lvPermutations.getItems()).isEqualTo(List.of("Ei", "ei"));
        assertThat(lvSameLetters.getItems()).isEqualTo(List.of("Ei", "ei"));

        robot.write('s');

        assertThat(lvPermutations.getItems()).isEqualTo(List.of("Eis", "eis", "sei", "sie"));
        assertThat(lvSameLetters.getItems()).isEqualTo(List.of("Eies", "Eis", "eies", "eis", "sei", "sie"));
    }

    @Test
    void textNotInDictionary_listsEmpty(FxRobot robot) {
        robot.interact(textField::requestFocus);
        robot.write("习近平");

        assertThat(lvPermutations.getItems()).isEmpty();
        assertThat(lvSameLetters.getItems()).isEmpty();
    }
}
