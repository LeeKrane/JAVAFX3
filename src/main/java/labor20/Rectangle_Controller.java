package labor20;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Rectangle_Controller implements Initializable {
	@FXML
	private Rectangle rectangle;
	
	@FXML
	private Slider sliderWidth;
	
	@FXML
	private Slider sliderHeight;
	
	@FXML
	private ChoiceBox<Color> choiceColor;
	
	@FXML
	private Text txtArea;
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		choiceColor.getItems().addAll(Color.valueOf("#9cce2b"), Color.valueOf("#303030"), Color.INDIANRED, Color.BLACK, Color.BLUEVIOLET);
		choiceColor.getSelectionModel().select(0);
		
		sliderWidth.valueProperty().addListener(observable -> updateRectangle());
		sliderHeight.valueProperty().addListener(observable -> updateRectangle());
		choiceColor.valueProperty().addListener(observable -> updateRectangle());
		
		updateRectangle();
	}
	
	private void updateRectangle () {
		rectangle.setFill(choiceColor.getValue());
		rectangle.setWidth(sliderWidth.getValue());
		rectangle.setHeight(sliderHeight.getValue());
		txtArea.setText((int)(rectangle.getWidth() * rectangle.getHeight()) + " E²");
	}
}
