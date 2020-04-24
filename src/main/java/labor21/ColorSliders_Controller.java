package labor21;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ColorSliders_Controller implements Initializable {
	@FXML
	private ScrollBar sliderRed;
	
	@FXML
	private ScrollBar sliderGreen;
	
	@FXML
	private ScrollBar sliderBlue;
	
	@FXML
	private Label lblColor;
	
	@FXML
	private CheckBox cbRed;
	
	@FXML
	private CheckBox cbGreen;
	
	@FXML
	private CheckBox cbBlue;
	
	@FXML
	private CheckBox cbGreyscale;
	
	@FXML
	private TextField tfBlue;
	
	@FXML
	private TextField tfGreen;
	
	@FXML
	private TextField tfRed;
	
	private Alert wrongColorAlert;
	private Alert notANumberAlert;
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		initializeAlerts();
		initializeColorBinding(cbRed, tfRed, sliderRed);
		initializeColorBinding(cbGreen, tfGreen, sliderGreen);
		initializeColorBinding(cbBlue, tfBlue, sliderBlue);
		
		cbGreyscale.setOnAction(event -> handleGreyscaleBindings());
		sliderRed.valueProperty().addListener(observable -> updateLabel());
		sliderGreen.valueProperty().addListener(observable -> updateLabel());
		sliderBlue.valueProperty().addListener(observable -> updateLabel());
	}
	
	private void updateLabel () {
		int red = cbRed.isSelected() ? (int) sliderRed.getValue() : 0;
		int green = cbGreen.isSelected() ? (int) sliderGreen.getValue() : 0;
		int blue = cbBlue.isSelected() ? (int) sliderBlue.getValue() : 0;
		Color color = Color.rgb(red, green, blue);
		String hex = String.format("#%02x%02x%02x", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
		
		lblColor.setBackground(new Background(new BackgroundFill(color, new CornerRadii(16), Insets.EMPTY)));
		lblColor.setText(hex);
	}
	
	private void handleGreyscaleBindings () {
		if (cbGreyscale.isSelected()) {
			int avg = (int) Math.round((sliderRed.getValue() + sliderGreen.getValue() + sliderBlue.getValue()) / 3);
			sliderRed.valueProperty().bindBidirectional(sliderBlue.valueProperty());
			sliderRed.valueProperty().bindBidirectional(sliderGreen.valueProperty());
			sliderRed.setValue(avg);
			
			// I assume the following three if-statements are optional, but I like it this way.
			if (!cbRed.isSelected())
				cbRed.setSelected(true);
			if (!cbGreen.isSelected())
				cbGreen.setSelected(true);
			if (!cbBlue.isSelected())
				cbBlue.setSelected(true);
		} else {
			sliderRed.valueProperty().unbindBidirectional(sliderBlue.valueProperty());
			sliderRed.valueProperty().unbindBidirectional(sliderGreen.valueProperty());
		}
	}
	
	private void initializeColorBinding (CheckBox cb, TextField tf, ScrollBar slider) {
		cb.disableProperty().bind(cbGreyscale.selectedProperty());
		cb.setOnAction(event -> updateLabel());
		
		slider.visibleProperty().bind(cb.selectedProperty());
		
		tf.visibleProperty().bind(cb.selectedProperty());
		tf.textProperty().bindBidirectional(slider.valueProperty(), new StringConverter<>() {
			@Override
			public String toString (Number object) {
				return String.format("%d", object.intValue());
			}
			
			@Override
			public Number fromString (String string) {
				if (string.isBlank())
					return 0;
				else if (string.chars().allMatch(Character::isDigit)) {
					int ret = Integer.parseInt(string);
					if (ret < 0 || ret > 255) {
						wrongColorAlert.showAndWait();
						return 0;
					}
					return ret;
				} else {
					notANumberAlert.showAndWait();
					Platform.runLater(tf::clear);
					return 0;
				}
			}
		});
	}
	
	private void initializeAlerts () {
		wrongColorAlert = new Alert(Alert.AlertType.ERROR);
		wrongColorAlert.setHeaderText("Wrong Color!");
		wrongColorAlert.setContentText("The color you entered is invalid. Colors must be between 0 and 255.");
		
		notANumberAlert = new Alert(Alert.AlertType.ERROR);
		notANumberAlert.setHeaderText("Not a Number!");
		notANumberAlert.setContentText("The color you entered is not a number. Colors must be between 0 and 255.");
	}
}
