package labor18;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ListDemo_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab18_ListDemo.fxml"));
		primaryStage.setTitle("List Demo");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
