package labor23;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import labor23.domain.Bundesland;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class BundeslandDetail_Controller implements Initializable {
	@FXML
	private ImageView ivWappen;
	
	@FXML
	private Label lblLandeshauptmann;
	
	@FXML
	private Label lblSitzverteilung;
	
	@FXML
	private TextField tfHauptstadt;
	
	@FXML
	private Button btnOk;
	
	private Stage stage;
	private Bundesland bundesland;
	private boolean clickedOk;
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		btnOk.setOnAction(event -> handleClose());
	}
	
	void setStage (Stage stage) {
		this.stage = stage;
	}
	
	void setBundesland (Bundesland bundesland) {
		this.bundesland = bundesland;
		ivWappen.setImage(new Image(Bundesland.class.getResourceAsStream("/assets/federal_states_icons/" + bundesland.getWappen())));
		lblLandeshauptmann.setText(bundesland.getLandeshauptmann());
		tfHauptstadt.setText(bundesland.getHauptstadt());
		
		Map<String, Integer> sitze = bundesland.getSitze();
		StringBuilder output = new StringBuilder("{");
		
		for (Map.Entry<String, Integer> entry : sitze.entrySet())
			output.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
		lblSitzverteilung.setText(output.substring(0, output.length() - 2) + '}');
	}
	
	boolean getClickedOk () {
		return clickedOk;
	}
	
	private void handleClose () {
		bundesland.setHauptstadt(tfHauptstadt.getText());
		clickedOk = true;
		stage.close();
	}
}
