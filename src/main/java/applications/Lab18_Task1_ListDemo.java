package applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Lab18_Task1_ListDemo extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab18_Task1.fxml"));
		primaryStage.setTitle("List Demo");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
