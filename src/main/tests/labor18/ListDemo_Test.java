package labor18;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.util.Lists.emptyList;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(ApplicationExtension.class)
public class ListDemo_Test {
    public static final String PATH_TO_FXML = "/layouts/Lab18_ListDemo.fxml";
    private TextField textField;
    private Button btnAdd;
    private Button btnClear;
    private Button btnSort;
    private ListView<String> listView;
    private Button btnDelete;

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
        btnDelete = marshaller.getById(Button.class, "deleteButton");
        btnAdd = marshaller.getById(Button.class, "addButton");
        btnClear = marshaller.getById(Button.class, "clearButton");
        btnSort = marshaller.getById(Button.class, "sortButton");
        listView = marshaller.getById(ListView.class, "listView");
        textField = marshaller.getById(TextField.class, "textField");
    }

    @Test
    void initialState_listEmpty() {
        assertThat(listView.getItems()).isEqualTo(emptyList());
    }

    @Test
    void addEmptyTextField_listUnchanged() {
        StringBuilder unprintable = IntStream.rangeClosed(0, 32)
                .mapToObj(i -> (char) i)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append);
        textField.setText(unprintable.toString());

        textField.fireEvent(new ActionEvent());

        assertThat(listView.getItems()).isEqualTo(emptyList());
    }

    @Test
    void addViaTextFieldAction_itemAppended() {
        listView.getItems().addAll("List", "is", "not", "empty");
        textField.setText("Text");

        textField.fireEvent(new ActionEvent());

        int lastIndex = listView.getItems().size() - 1;
        assertThat(listView.getItems().get(lastIndex)).isEqualTo("Text");
    }

    @Test
    void addViaButtonAdd_itemAppended() {
        listView.getItems().addAll("List", "is", "not", "empty");
        textField.setText("Text");

        btnAdd.fireEvent(new ActionEvent());

        int lastIndex = listView.getItems().size() - 1;
        assertThat(listView.getItems().get(lastIndex)).isEqualTo("Text");
    }

    @Test
    void sortButton_listSorted() {
        List<String> values = List.of("Observer", "Strategy", "Singleton", "Bridge", "Adapter");
        listView.getItems().addAll(values);

        btnSort.fireEvent(new ActionEvent());

        assertThat(listView.getItems()).containsExactlyElementsOf(new TreeSet<>(values));
    }

    @Test
    void clearButton_listCleared() {
        listView.getItems().addAll("List", "is", "not", "empty");

        btnClear.fireEvent(new ActionEvent());

        assertThat(listView.getItems()).isEqualTo(emptyList());
    }

    @Test
    void deleteButtonNothingSelected_noOp() {
        listView.getItems().addAll("List", "is", "not", "empty");
        listView.getSelectionModel().clearSelection();

        assertThatCode(
                () -> btnDelete.fireEvent(new ActionEvent()))
                .doesNotThrowAnyException();
    }

    @Test
    void deleteButtonItemSelected_selectedItemRemoved() {
        listView.getItems().addAll("List", "is", "not", "empty");
        List<String> expected = List.of("List", "is", "empty");

        listView.getSelectionModel().select(2);
        btnDelete.fireEvent(new ActionEvent());

        assertThat(listView.getItems()).isEqualTo(expected);
    }
}
