package labor18;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NumberList_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab18_NumberList.fxml"));
		primaryStage.setTitle("Number List");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
