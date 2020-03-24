package labor18;

import labor18.domain.LogEntry;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.emptyList;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
public class Logger_Test {
    public static final String PATH_TO_FXML = "/layouts/Lab18_Logger.fxml";
    private Button button;
    private CheckBox checkBox;
    private ListView<LogEntry> listView;
    private RadioButton[] rbOptions;

    @BeforeAll
    static void setFailOnError() {
        Thread.setDefaultUncaughtExceptionHandler((t, error) -> fail(error));
    }

    private static LogEntry[] createMockEntries() {
        return IntStream.range(0, 3)
                .mapToObj(i -> {
                    LogEntry entry = mock(LogEntry.class);
                    when(entry.toString()).thenReturn("Mock Entry " + i);
                    return entry;
                }).toArray(LogEntry[]::new);
    }

    @Start
    private void loadUi(Stage primaryStage) throws IOException {
        FxMarshaller marshaller = new FxMarshaller(primaryStage, PATH_TO_FXML);
        initializeNodes(marshaller);
    }

    @SuppressWarnings("unchecked")
    private void initializeNodes(FxMarshaller marshaller) {
        button = marshaller.getById(Button.class, "button");
        checkBox = marshaller.getById(CheckBox.class, "checkBox");
        listView = marshaller.getById(ListView.class, "listView");
        rbOptions = IntStream.rangeClosed(1, 3)
                .mapToObj(i -> "option" + i)
                .map(id -> marshaller.getById(RadioButton.class, "option1"))
                .toArray(RadioButton[]::new);
    }

    @Test
    void initialState_radioButtonsBelongToSameGroup() {
        assertThat(rbOptions[0].getToggleGroup())
                .isSameAs(rbOptions[1].getToggleGroup())
                .isSameAs(rbOptions[2].getToggleGroup());
    }

    @Test
    void initialState_checkBoxUnchecked() {
        assertThat(checkBox.isSelected()).isFalse();
    }

    @Test
    void initialState_listViewEmpty() {
        assertThat(listView.getItems()).isEqualTo(emptyList());
    }

    @Test
    void button_logEntryAppendedToList() {
        listView.getItems().addAll(createMockEntries());

        button.fire();

        assertThat(getLastMessage()).contains("Button pressed");
    }

    private String getLastMessage() {
        int lastIndex = listView.getItems().size() - 1;
        return listView.getItems().get(lastIndex).toString();
    }

    @Test
    void checkBoxChecked_logEntryCheckedAppendedToList() {
        listView.getItems().addAll(createMockEntries());

        checkBox.fire();

        assertThat(getLastMessage()).containsIgnoringCase("CheckBox checked");
    }

    @Test
    void checkBoxUnchecked_logEntryUncheckedAppendedToList() {
        listView.getItems().addAll(createMockEntries());
        checkBox.setSelected(true);

        checkBox.fire();

        assertThat(getLastMessage()).containsIgnoringCase("CheckBox unchecked");
    }

    @Test
    void sameRadioButtonSelectedMultipleTimes_oneLogEntryAppendedToList() {
        rbOptions[0].fire();

        rbOptions[0].fire();

        assertThat(listView.getItems()).hasSize(1);
    }

    @Test
    void radioButtonSelected_logEntryAppendedToList() {
        listView.getItems().addAll(createMockEntries());

        rbOptions[0].fire();

        assertThat(getLastMessage()).contains("Option 1 selected");
    }
}
