package labor16;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TextCombiner extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/labor16/textCombiner.fxml"));
        primaryStage.setTitle("Text Combiner");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
