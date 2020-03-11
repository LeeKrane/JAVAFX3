package applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Lab17_Task1_HelloHTL extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab17_Task1.fxml"));
		primaryStage.setTitle("Hello HTL");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
