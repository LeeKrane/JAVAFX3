package labor22;

import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Means_Controller implements Initializable {
	@FXML
	private TextField tfNewValue;
	
	@FXML
	private Button btnAddValue;
	
	@FXML
	private Button btnRemoveSelected;
	
	@FXML
	private Button btnClear;
	
	@FXML
	private ListView<Double> listView;
	
	@FXML
	private CheckBox cbArithmetic;
	
	@FXML
	private CheckBox cbGeometric;
	
	@FXML
	private CheckBox cbHarmonic;
	
	@FXML
	private Slider slider;
	
	@FXML
	private Text txtArithmetic;
	
	@FXML
	private Text txtGeometric;
	
	@FXML
	private Text txtHarmonic;
	
	private Alert notANumberAlert;
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		notANumberAlert = new Alert(Alert.AlertType.ERROR);
		notANumberAlert.setHeaderText("Error");
		notANumberAlert.setContentText("The list can only contain numbers.");
		
		listView.getItems().addListener((InvalidationListener) observable ->  {
			updateArithmetic();
			updateGeometric();
			updateHarmonic();
		});
		
		slider.valueProperty().addListener(observable -> {
			updateArithmetic();
			updateGeometric();
			updateHarmonic();
		});
		
		tfNewValue.setOnAction(event -> insertNewValue());
		btnAddValue.setOnAction(event -> insertNewValue());
		btnRemoveSelected.setOnAction(event -> listView.getItems().remove(listView.getSelectionModel().getSelectedItem()));
		btnClear.setOnAction(event -> listView.getItems().clear());
		
		txtArithmetic.visibleProperty().bind(cbArithmetic.selectedProperty());
		txtGeometric.visibleProperty().bind(cbGeometric.selectedProperty());
		txtHarmonic.visibleProperty().bind(cbHarmonic.selectedProperty());
		
		updateArithmetic();
		updateGeometric();
		updateHarmonic();
	}
	
	private void insertNewValue () {
		if (!tfNewValue.getText().isBlank()) {
			try {
				Double d = Double.parseDouble(tfNewValue.getText());
				listView.getItems().add(d);
			} catch (NumberFormatException e) {
				notANumberAlert.showAndWait();
			}
		}
		tfNewValue.clear();
	}
	
	private void updateArithmetic () {
		if (listContainsItems(txtArithmetic)) {
			double arithmeticValue = listView.getItems().stream().mapToDouble(Double::valueOf).sum() / listView.getItems().size();
			txtArithmetic.setText(String.format("%." + Math.round(slider.getValue()) + "f", arithmeticValue));
		}
	}
	
	private void updateGeometric () {
		if (listContainsItems(txtGeometric)) {
			Optional<Double> doubleOptional = listView.getItems().stream().min(Double::compareTo);
			if (doubleOptional.isPresent() && Double.compare(doubleOptional.get(), 0.0) < 0.0)
				txtGeometric.setText("The list may only contain positive values");
			else {
				double geometricValue = 1.0;
				for (double d : listView.getItems())
					geometricValue *= d;
				geometricValue = Math.pow(geometricValue, 1.0 / listView.getItems().size());
				txtGeometric.setText((Double.compare(geometricValue, 0.0) == 0 ? "Undefined" : String.format("%." + Math.round(slider.getValue()) + "f", geometricValue)));
			}
		}
	}
	
	private void updateHarmonic () {
		if (listContainsItems(txtHarmonic)) {
			double harmonicValue;
			if (listView.getItems().contains(0.0))
				harmonicValue = 0.0;
			else {
				harmonicValue = listView.getItems().size();
				double divisor = 0.0;
				for (double d : listView.getItems())
					divisor += 1.0 / d;
				harmonicValue /= (Double.compare(divisor, 0.0) == 0 ? 1.0 : divisor);
			}
			txtHarmonic.setText(String.format("%." + Math.round(slider.getValue()) + "f", harmonicValue));
		}
	}
	
	private boolean listContainsItems (Text txt) {
		if (listView.getItems().size() == 0) {
			txt.setText("The list is empty");
			return false;
		}
		return true;
	}
}
