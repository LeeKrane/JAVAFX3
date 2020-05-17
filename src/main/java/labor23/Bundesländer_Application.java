package labor23;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Bundesländer_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab23_BundeslandList.fxml"));
		primaryStage.setTitle("Bundesländer");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
