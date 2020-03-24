package labor19;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Dictionary_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab19_Dictionary.fxml"));
		primaryStage.setTitle("Dictionary");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
