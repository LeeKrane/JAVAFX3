package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class Lab18_Task1_Controller implements Initializable {
	@FXML
	private TextField textField;
	
	@FXML
	private Button addButton;
	
	@FXML
	private Button clearButton;
	
	@FXML
	private Button deleteButton;
	
	@FXML
	private Button shuffleButton;
	
	@FXML
	private Button sortButton;
	
	@FXML
	private ListView<String> listView;
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		textField.setOnAction(event -> add());
		addButton.setOnAction(event -> add());
		clearButton.setOnAction(event -> listView.getItems().clear());
		deleteButton.setOnAction(event -> listView.getItems().remove(listView.getSelectionModel().getSelectedItem()));
		shuffleButton.setOnAction(event -> Collections.shuffle(listView.getItems()));
		sortButton.setOnAction(event -> Collections.sort(listView.getItems()));
	}
	
	private void add () {
		if (!textField.getText().isBlank())
			listView.getItems().add(textField.getText());
	}
}
