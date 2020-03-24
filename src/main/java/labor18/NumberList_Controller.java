package labor18;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class NumberList_Controller implements Initializable {
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
		rightButton.setOnAction(event -> insertSelection(leftListView, rightListView));
		leftButton.setOnAction(event -> insertSelection(rightListView, leftListView));
		singleSelectionRadioButton.setOnAction(event -> changeSelectionMode(SelectionMode.SINGLE));
		multipleSelectionRadioButton.setOnAction(event -> changeSelectionMode(SelectionMode.MULTIPLE));
	}
	
	private void changeSelectionMode (SelectionMode selectionMode) {
		leftListView.getSelectionModel().setSelectionMode(selectionMode);
		rightListView.getSelectionModel().setSelectionMode(selectionMode);
	}
	
	private void insertSelection (ListView<Integer> sourceListView, ListView<Integer> targetListView) {
		if (singleSelectionRadioButton.isSelected() && sourceListView.getSelectionModel().getSelectedItem() != null) {
			// insert multiple into right list view
			int insert = sourceListView.getSelectionModel().getSelectedItem();
			int index = -Arrays.binarySearch(targetListView.getItems().toArray(), insert);
			targetListView.getItems().add(index - 1, insert);
			sourceListView.getItems().remove(Integer.valueOf(insert));
		} else if (sourceListView.getSelectionModel().getSelectedItems().size() > 0) {
			// insert single into right list view
			targetListView.getItems().addAll(sourceListView.getSelectionModel().getSelectedItems());
			sourceListView.getItems().removeAll(sourceListView.getSelectionModel().getSelectedItems());
			Collections.sort(targetListView.getItems());
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
