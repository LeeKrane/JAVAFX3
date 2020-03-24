package labor18;

import domain.Lab18_LogEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Logger_Controller implements Initializable {
	@FXML
	private Button button;
	
	@FXML
	private ToggleGroup opt;
	
	@FXML
	private CheckBox checkBox;
	
	@FXML
	private ListView<Lab18_LogEntry> listView;
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		button.setOnAction(event -> listView.getItems().add(new Lab18_LogEntry("Button pressed", LocalDateTime.now())));
		opt.selectedToggleProperty().addListener((observable, oldValue, newValue) -> listView.getItems().add(new Lab18_LogEntry(((RadioButton) newValue).getText() + " selected", LocalDateTime.now())));
		checkBox.setOnAction(event -> listView.getItems().add(new Lab18_LogEntry("CheckBox " + (checkBox.isSelected() ? "" : "un") + "checked", LocalDateTime.now())));
	}
}

