package applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Lab18_Task2_NumberList extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab18_Task2.fxml"));
		primaryStage.setTitle("Number List");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
