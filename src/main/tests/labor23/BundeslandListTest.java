package labor23;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import labor23.domain.Bundesland;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class BundeslandListTest extends FxTest {
    private static final String PATH_TO_FXML = "/layouts/Lab23_BundeslandList.fxml";
    private ListView<Bundesland> listView;


    @Start
    void initializeGUI(Stage primaryStage) throws IOException {
        startApplication(primaryStage, PATH_TO_FXML);
        listView = getById("listView");
    }

    @Test
    void initialState_allBundeslandsInList() {
        assertThat(listView.getItems())
                .extracting(Bundesland::getName)
                .containsExactly("Burgenland", "Kärnten", "Niederösterreich", "Oberösterreich",
                        "Land Salzburg", "Steiermark", "Tirol", "Vorarlberg", "Wien");
    }

    @Test
    void bundeslandClicked_opensDetailWindow(FxRobot robot) {
        robot.clickOn(((VirtualFlow) listView.getChildrenUnmodifiable().get(0)).getCell(1));

        Stage stage = (Stage) robot.listWindows().get(1);
        assertThat(stage.getTitle()).isEqualTo("Kärnten");
        Label lblLandeshauptmann = getDetailNode(stage, "lblLandeshauptmann");
        assertThat(lblLandeshauptmann).hasText("Peter Kaiser");
        Label lblSitze = getDetailNode(stage, "lblSitzverteilung");
        assertThat(lblSitze).hasText("{FPÖ=9, SPÖ=18, TK=3, ÖVP=6}");
        ImageView ivWappen = getDetailNode(stage, "ivWappen");
        assertThat(ivWappen.getImage()).isNotNull();
    }

    @SuppressWarnings("unchecked")
    private <T> T getDetailNode(Stage stage, String id) {
        Node requested = stage.getScene().lookup('#' + id);
        if (requested == null) {
            System.err.println("FxId not found in DetailView: " + id);
            System.exit(ERROR_ID_NOT_FOUND);
        }
        return (T) requested;
    }

    @Test
    void bundeslandClickedHauptstadtChangedExitViaX_hauptstadtInListUnchanged(FxRobot robot) {
        robot.clickOn(listView.getChildrenUnmodifiable().get(0));
        Stage stage = (Stage) robot.listWindows().get(1);
        TextField tfHauptstadt = getDetailNode(stage, "tfHauptstadt");
        String expected = tfHauptstadt.getText();
        robot.interact(tfHauptstadt::clear);

        robot.interact(tfHauptstadt::requestFocus).write("Test");
        robot.interact(stage::close);

        assertThat(listView.getItems()).extracting(Bundesland::getHauptstadt).containsOnlyOnce(expected);
    }

    @Test
    void bundeslandClickedHauptstadtChangedExitViaButton_hauptstadtInListChanged(FxRobot robot) {
        robot.clickOn(listView.getChildrenUnmodifiable().get(0));
        Stage stage = (Stage) robot.listWindows().get(1);
        TextField tfHauptstadt = getDetailNode(stage, "tfHauptstadt");
        robot.interact(tfHauptstadt::clear);

        robot.interact(tfHauptstadt::requestFocus).write("Test");
        Button btnOk = getDetailNode(stage, "btnOk");
        robot.interact(btnOk::fire);

        assertThat(listView.getItems()).extracting(Bundesland::getHauptstadt).containsOnlyOnce("Test");
    }
}
