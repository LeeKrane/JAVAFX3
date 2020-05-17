package labor23;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import labor23.domain.Bundesland;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class BundeslandList_Controller implements Initializable {
	@FXML
	private ListView<Bundesland> listView;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			List<Bundesland> bundeslandList = Bundesland.readFile(Files.newInputStream(Paths.get("src/main/resources/assets/bundesländer.csv")));
			listView.setCellFactory(bundeslandListView -> new CustomListCell());
			listView.getItems().setAll(bundeslandList);
		} catch (IOException e) {
			System.err.println(e + ": " + e.getMessage());
		}
	}
}
