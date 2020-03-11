package controllers;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Lab17_Task1_Controller implements Initializable {
	@FXML
	private StackPane pane;
	
	@FXML
	private Rectangle box;
	
	@FXML
	private Label label;
	
	RotateTransition rotate;
	RotateTransition rotateBack;
	DropShadow dropShadow;
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		setupRotateTranslations();
		dropShadow.setColor(Color.WHITESMOKE);
		dropShadow.setRadius(10);
		// TODO: DropShadow, Reflection
		box.setOnMouseClicked(e -> rotate.play());
	}
	
	private void setupRotateTranslations () {
		rotate = new RotateTransition(Duration.seconds(1), box);
		rotate.setFromAngle(0);
		rotate.setToAngle(180);
		rotate.setOnFinished(e -> rotateBack.play());
		rotateBack = new RotateTransition(Duration.seconds(1), box);
		rotateBack.setFromAngle(180);
		rotateBack.setToAngle(0);
	}
}
