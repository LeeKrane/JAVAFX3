package labor22;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Means_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab22_Mittelwerte.fxml"));
		primaryStage.setTitle("Mittelwerte");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
