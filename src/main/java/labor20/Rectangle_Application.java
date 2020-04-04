package labor20;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Rectangle_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab20_Rectangle.fxml"));
		primaryStage.setTitle("Rectangle");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
