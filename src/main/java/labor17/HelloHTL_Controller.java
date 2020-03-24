package labor17;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloHTL_Controller implements Initializable {
	@FXML
	private StackPane pane;
	
	@FXML
	private AnchorPane rotatePane;
	
	private RotateTransition rotate;
	private RotateTransition rotateBack;
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		setupRotateTranslations();
		pane.setOnMouseClicked(e -> rotate.play());
	}
	
	private void setupRotateTranslations () {
		// You also can use the normal pane for the rotation instead of the rotatePane, but this would not look as good as it does now.
		rotate = new RotateTransition(Duration.seconds(1), rotatePane);
		rotateBack = new RotateTransition(Duration.seconds(1), rotatePane);
		
		rotate.setFromAngle(0);
		rotate.setToAngle(180);
		rotate.setOnFinished(e -> rotateBack.play());
		rotateBack.setFromAngle(180);
		rotateBack.setToAngle(0);
	}
}
