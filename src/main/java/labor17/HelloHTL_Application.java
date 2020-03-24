package labor17;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloHTL_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab17_HelloHTL.fxml"));
		primaryStage.setTitle("Hello HTL");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
