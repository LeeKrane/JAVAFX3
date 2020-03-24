package labor19;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import labor19.domain.Dictionary;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class Dictionary_Controller implements Initializable {
	@FXML
	private TextField textfield;
	
	@FXML
	private ListView<String> lvPermutations;
	
	@FXML
	private ListView<String> lvSameLetters;
	
	private Dictionary dictionary;
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		dictionary = new Dictionary("src/main/resources/assets/de-DE1.dic");
		textfield.textProperty().addListener(observable -> refresh());
	}
	
	private void refresh () {
		lvPermutations.getItems().clear();
		lvSameLetters.getItems().clear();
		if (!textfield.getText().isBlank()) {
			lvPermutations.getItems().addAll(dictionary.getPermutations(textfield.getText()));
			lvSameLetters.getItems().addAll(dictionary.getWordsWithSameLetters(textfield.getText()));
		}
	}
	
	private void addAll (Set<String> add, ObservableList<String> list) {
		list.addAll(add);
	}
}
