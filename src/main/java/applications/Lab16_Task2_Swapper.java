package applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Lab16_Task2_Swapper extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab16_Task2.fxml"));
		primaryStage.setTitle("Swapper");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
