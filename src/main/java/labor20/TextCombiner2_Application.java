package labor20;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TextCombiner2_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/Lab20_TextCombiner2.fxml"));
		primaryStage.setTitle("Text Combiner 2.0");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
