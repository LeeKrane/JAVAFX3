package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Lab16_Task2_Controller implements Initializable {
	@FXML
	private Pane statusBar;
	
	@FXML
	private TextField textField1;
	
	@FXML
	private TextField textField2;
	
	@FXML
	private Button swapButton;
	
	@FXML
	private Button clearButton;
	
	@FXML
	private Text statusText;
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		statusBar.setVisible(false);
		
		swapButton.setOnAction(e -> swap());
		
		clearButton.setOnAction(e -> clear());
	}
	
	private void clear () {
		textField1.clear();
		textField2.clear();
		statusBar.setVisible(false);
	}
	
	private void swap () {
		if (textField1.getText().isBlank() || textField2.getText().isBlank()) {
			statusText.setText("Both TextFields must be filled.");
		} else {
			String text1 = textField1.getText();
			textField1.setText(textField2.getText());
			textField2.setText(text1);
			statusText.setText("Swapped.");
		}
		statusBar.setVisible(true);
	}
}
