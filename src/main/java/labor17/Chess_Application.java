package labor17;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Chess_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab17_Chess.fxml"));
		primaryStage.setTitle("Chess");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
