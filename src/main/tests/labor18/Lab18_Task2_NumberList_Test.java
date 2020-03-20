package labor18;

import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.util.Lists.emptyList;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(ApplicationExtension.class)
public class Lab18_Task2_NumberList_Test {
    public static final String PATH_TO_FXML = "/layouts/Lab18_Task2.fxml";
    private ListView<Integer> lvLeft;
    private ListView<Integer> lvRight;
    private RadioButton rbSingleSelection;
    private RadioButton rbMultipleSelection;
    private Button btnLtr;
    private Button btnRtl;

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
        lvLeft = marshaller.getById(ListView.class, "leftListView");
        lvRight = marshaller.getById(ListView.class, "rightListView");
        rbSingleSelection = marshaller.getById(RadioButton.class, "singleSelectionRadioButton");
        rbMultipleSelection = marshaller.getById(RadioButton.class, "multipleSelectionRadioButton");
        btnLtr = marshaller.getById(Button.class, "rightButton");
        btnRtl = marshaller.getById(Button.class, "leftButton");
    }

    @Test
    void initialState_leftListContainsNumbersUpTo100() {
        List<Integer> expected = IntStream.rangeClosed(0, 100)
                .boxed()
                .collect(toList());

        assertThat(lvLeft.getItems()).isEqualTo(expected);
    }

    @Test
    void initialState_rightListEmpty() {
        assertThat(lvRight.getItems()).isEqualTo(emptyList());
    }

    @Test
    void initialState_singleSelectionMode() {
        assertThat(rbSingleSelection.isSelected()).isTrue();
    }

    @Test
    void initialState_radioButtonsBelongToSameGroup() {
        assertThat(rbSingleSelection.getToggleGroup())
                .isSameAs(rbMultipleSelection.getToggleGroup());
    }

    @Test
    void noneSelectedAnyButtonPressedAnySelectionMode_noOp(FxRobot robot) {
        assertThatCode(() -> btnLtr.fire()).doesNotThrowAnyException();
        assertThatCode(() -> btnRtl.fire()).doesNotThrowAnyException();

        robot.interact(rbMultipleSelection::fire);

        assertThatCode(() -> btnRtl.fire()).doesNotThrowAnyException();
        assertThatCode(() -> btnRtl.fire()).doesNotThrowAnyException();
    }

    @Test
    void leftToRightSingle_selectedItemRemovedFromLeftAddedToRight(FxRobot robot) {
        robot.interact(lvLeft.getItems()::clear);
        robot.interact(() -> lvLeft.getItems().addAll(0, 2, 4, 6));
        robot.interact(() -> lvRight.getItems().addAll(1, 3, 5, 7));

        lvLeft.getSelectionModel().select((Integer) 4);
        robot.interact(btnLtr::fire);

        assertThat(lvLeft.getItems()).containsExactly(0, 2, 6);
        assertThat(lvRight.getItems()).containsExactly(1, 3, 4, 5, 7);
    }

    @Test
    void rightToLeftSingle_selectedItemRemovedFromRightAddedToLeft(FxRobot robot) {
        robot.interact(lvLeft.getItems()::clear);
        robot.interact(() -> lvLeft.getItems().addAll(0, 2, 4, 6));
        robot.interact(() -> lvRight.getItems().addAll(1, 3, 5, 7));

        lvRight.getSelectionModel().select((Integer) 3);
        robot.interact(btnRtl::fire);

        assertThat(lvLeft.getItems()).containsExactly(0, 2, 3, 4, 6);
        assertThat(lvRight.getItems()).containsExactly(1, 5, 7);
    }

    @Test
    void leftToRightMultiple_selectedItemsRemovedFromLeftAddedToRight(FxRobot robot) {
        robot.interact(lvLeft.getItems()::clear);
        robot.interact(() -> lvLeft.getItems().addAll(0, 2, 4, 6));
        robot.interact(() -> lvRight.getItems().addAll(1, 3, 5, 7));

        robot.interact(rbMultipleSelection::fire);
        lvLeft.getSelectionModel().selectIndices(0, 3);
        robot.interact(btnLtr::fire);

        assertThat(lvLeft.getItems()).containsExactly(2, 4);
        assertThat(lvRight.getItems()).containsExactly(0, 1, 3, 5, 6, 7);
    }

    @Test
    void rightToLeftMultiple_selectedItemsRemovedFromRightAddedToLeft(FxRobot robot) {
        robot.interact(lvLeft.getItems()::clear);
        robot.interact(() -> lvLeft.getItems().addAll(0, 2, 4, 6));
        robot.interact(() -> lvRight.getItems().addAll(1, 3, 5, 7));

        robot.interact(rbMultipleSelection::fire);
        lvRight.getSelectionModel().selectIndices(1, 2);
        robot.interact(btnRtl::fire);

        assertThat(lvLeft.getItems()).containsExactly(0, 2, 3, 4, 5, 6);
        assertThat(lvRight.getItems()).containsExactly(1, 7);
    }
}
