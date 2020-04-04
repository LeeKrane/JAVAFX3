package labor20;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class TextCombiner2_Controller implements Initializable {
	@FXML
	private RadioButton rb12;
	
	@FXML
	private ToggleGroup grp;
	
	@FXML
	private RadioButton rb21;
	
	@FXML
	private TextField tf1;
	
	@FXML
	private TextField tf2;
	
	@FXML
	private TextField tfOutput;
	
	@FXML
	private Button btnClear;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		updateOutput();
		grp.selectedToggleProperty().addListener(observable -> updateOutput());
		tf1.textProperty().addListener(observable -> updateOutput());
		tf2.textProperty().addListener(observable -> updateOutput());
		btnClear.setOnAction(e -> clear());
	}
	
	private void updateOutput () {
		tfOutput.clear();
		if (tf1.getText().isEmpty() || tf2.getText().isEmpty())
			tfOutput.appendText("Both TextFields must be filled.");
		else if (rb12.isSelected())
			tfOutput.appendText("Result: " + tf1.getText() + tf2.getText());
		else
			tfOutput.appendText("Result: " + tf2.getText() + tf1.getText());
	}
	
	private void clear () {
		tf1.clear();
		tf2.clear();
		rb12.setSelected(true);
	}
}
