package labor23;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import labor23.domain.Bundesland;

import java.io.IOException;

public class CustomListCell extends ListCell<Bundesland> {
	@FXML
	private GridPane gridPane;
	
	@FXML
	private Label lblBundesland;
	
	@FXML
	private Label lblHauptstadt;
	
	@FXML
	private Label lblEinwohner;
	
	@FXML
	private Label lblFlaeche;
	
	@FXML
	private ImageView ivWappen;
	
	private FXMLLoader loader;
	
	@Override
	protected void updateItem (Bundesland item, boolean empty) {
		super.updateItem(item, empty);
		
		setText(null);
		if (empty || item == null)
			setGraphic(null);
		else {
			boolean fxmlLoaded = loader != null;
			
			if (!fxmlLoaded) {
				loader = new FXMLLoader(getClass().getResource("/layouts/Lab23_BundeslandEntry.fxml"));
				loader.setController(this);
				
				try {
					loader.load();
				} catch (IOException e) {
					System.err.println(e + ": " + e.getMessage());
				}
			}
			
			gridPane.setOnMouseClicked(event -> showDetailWindow(item));
			setupGui(item);
			setGraphic(loader.getRoot());
		}
	}
	
	private void showDetailWindow (Bundesland item) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/layouts/Lab23_BundeslandDetail.fxml"));
			GridPane gridPane = loader.load();
			
			Stage stage = new Stage();
			stage.setTitle(item.getName());
			stage.getIcons().add(new Image(Bundesland.class.getResourceAsStream("/assets/federal_states_icons/" + item.getWappen())));
			stage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(gridPane);
			stage.setScene(scene);
			BundeslandDetail_Controller controller = loader.getController();
			controller.setStage(stage);
			controller.setBundesland(item);
			stage.showAndWait();
			
			if (controller.getClickedOk())
				updateItem(item, false);
		} catch (IOException e) {
			System.err.println(e + ": " + e.getMessage());
		}
	}
	
	private void setupGui (Bundesland item) {
		lblBundesland.setText(item.getName());
		ivWappen.setImage(new Image(Bundesland.class.getResourceAsStream("/assets/federal_states_icons/" + item.getWappen())));
		lblHauptstadt.setText(item.getHauptstadt());
		lblEinwohner.setText(String.format("%d", item.getEinwohner()));
		lblFlaeche.setText(String.format("%.1f", item.getFlaeche()));
	}
}
