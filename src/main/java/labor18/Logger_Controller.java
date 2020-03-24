package labor18;

import domain.LogEntry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Logger_Controller implements Initializable {
	@FXML
	private Button button;
	
	@FXML
	private RadioButton option1;
	
	@FXML
	private RadioButton option2;
	
	@FXML
	private RadioButton option3;
	
	@FXML
	private CheckBox checkBox;
	
	@FXML
	private ListView<LogEntry> listView;
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		EventHandler<ActionEvent> actionEventHandler = actionEvent -> {
			Node node = (Node) actionEvent.getTarget();
			if (node.toString().contains("RadioButton"))
				listView.getItems().add(new LogEntry(((RadioButton)node).getText() + " selected", LocalDateTime.now()));
			else if (node.toString().contains("Button"))
				listView.getItems().add(new LogEntry("Button pressed", LocalDateTime.now()));
			else
				listView.getItems().add(new LogEntry("CheckBox " + (((CheckBox)node).isSelected() ? "" : "un") + "checked", LocalDateTime.now()));
		};
		
		button.setOnAction(actionEventHandler);
		option1.setOnAction(actionEventHandler);
		option2.setOnAction(actionEventHandler);
		option3.setOnAction(actionEventHandler);
		checkBox.setOnAction(actionEventHandler);
	}
}

