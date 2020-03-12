package applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Lab18_Task3_Logger extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab18_Task3.fxml"));
		primaryStage.setTitle("Logger");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
