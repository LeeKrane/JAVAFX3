package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class Lab18_Task2_Controller implements Initializable {
	@FXML
	private ListView<Integer> leftListView;
	
	@FXML
	private ListView<Integer> rightListView;
	
	@FXML
	private Button rightButton;
	
	@FXML
	private Button leftButton;
	
	@FXML
	private RadioButton singleSelectionRadioButton;
	
	@FXML
	private RadioButton multipleSelectionRadioButton;
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		IntStream.range(0, 101).forEach(leftListView.getItems()::add);
		rightButton.setOnAction(event -> insertSelection(true));
		leftButton.setOnAction(event -> insertSelection(false));
		singleSelectionRadioButton.setOnAction(event -> changeSelectionMode(SelectionMode.SINGLE));
		multipleSelectionRadioButton.setOnAction(event -> changeSelectionMode(SelectionMode.MULTIPLE));
	}
	
	private void changeSelectionMode (SelectionMode single) {
		leftListView.getSelectionModel().setSelectionMode(single);
		rightListView.getSelectionModel().setSelectionMode(single);
	}
	
	private void insertSelection (boolean insertRight) {
		if (insertRight) {
			if (singleSelectionRadioButton.isSelected() && leftListView.getSelectionModel().getSelectedItem() != null)
				// insert multiple into right list view
				insertBinary(leftListView.getItems(), rightListView.getItems(), leftListView.getSelectionModel().getSelectedItem());
			else if (leftListView.getSelectionModel().getSelectedItems().size() > 0) {
				// insert single into right list view
				rightListView.getItems().addAll(leftListView.getSelectionModel().getSelectedItems());
				leftListView.getItems().removeAll(leftListView.getSelectionModel().getSelectedItems());
				Collections.sort(rightListView.getItems());
			}
		} else {
			if (singleSelectionRadioButton.isSelected() && rightListView.getSelectionModel().getSelectedItem() != null)
				// insert multiple into left list view
				insertBinary(rightListView.getItems(), leftListView.getItems(), rightListView.getSelectionModel().getSelectedItem());
			else if (rightListView.getSelectionModel().getSelectedItems().size() > 0) {
				// insert single into left list view
				leftListView.getItems().addAll(rightListView.getSelectionModel().getSelectedItems());
				rightListView.getItems().removeAll(rightListView.getSelectionModel().getSelectedItems());
				Collections.sort(leftListView.getItems());
			}
		}
	}
	
	private void insertBinary (List<Integer> sourceList, List<Integer> targetList, int insert) {
		if (targetList.isEmpty() || targetList.get(targetList.size() - 1) < insert)
			targetList.add(insert);
		else {
			int min = 0, max = targetList.size() - 1;
			double index = max / 2.0;
			while (!(targetList.get((int)index) < insert && targetList.get((int)index + 1) > insert)) {
				if (index == 0)
					break;
				if (targetList.get((int)index) < insert)
					min = (int)index + 1;
				else if (targetList.get((int)index) > insert)
					max = (int)index;
				index = (max + min) / 2.0;
			}
			targetList.add((int)Math.round(index), insert);
		}
		sourceList.remove(Integer.valueOf(insert));
	}
}
