package applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Lab16_Task1_TextCombiner extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab16_Task1.fxml"));
        primaryStage.setTitle("Text Combiner");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
