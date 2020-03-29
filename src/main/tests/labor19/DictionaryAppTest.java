package labor19;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.testfx.assertions.api.Assertions.assertThat;


@ExtendWith(ApplicationExtension.class)
public class DictionaryAppTest extends FxTest {
    public static final String PATH_TO_FXML = "/layouts/Lab19_Dictionary.fxml";
    private TextField textField;
    private ListView<String> lvPermutations;
    private ListView<String> lvSameLetters;

    @Start
    void initializeGUI(Stage primaryStage) throws IOException {
        startApplication(primaryStage, PATH_TO_FXML);
        textField = getById("textfield");
        lvPermutations = getById("lvPermutations");
        lvSameLetters = getById("lvSameLetters");
    }

    @Test
    void initalState_allEmpty() {
        assertThat(textField).hasText("");
        assertThat(lvPermutations).isEmpty();
        assertThat(lvSameLetters).isEmpty();
    }

    @Test
    void textEntered_listsChange(FxRobot robot) {
        robot.interact(textField::requestFocus);
        robot.write('e');

        assertThat(lvPermutations.getItems()).containsExactly("e");
        assertThat(lvSameLetters.getItems()).containsExactly("e");

        robot.write('i');

        assertThat(lvPermutations.getItems()).containsExactly("Ei", "ei");
        assertThat(lvSameLetters.getItems()).containsExactly("Ei", "ei");

        robot.write('s');

        assertThat(lvPermutations.getItems()).containsExactly("Eis", "eis", "sei", "sie");
        assertThat(lvSameLetters.getItems()).containsExactly("Eies", "Eis", "eies", "eis", "sei", "sie");
    }

    @Test
    void textNotInDictionary_listsEmpty(FxRobot robot) {
        robot.interact(textField::requestFocus);
        robot.write("习近平");

        assertThat(lvPermutations.getItems()).isEmpty();
        assertThat(lvSameLetters.getItems()).isEmpty();
    }
}
