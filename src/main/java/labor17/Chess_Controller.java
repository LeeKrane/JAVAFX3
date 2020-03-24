package labor17;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class Chess_Controller implements Initializable {
	@FXML
	private GridPane pane;
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		for (int i = 0; i < pane.getColumnCount(); i++) {
			for (int j = 0; j < pane.getRowCount(); j++) {
				if (i % 2 == j % 2)
					pane.add(new Rectangle(pane.getWidth() / 8, pane.getHeight() / 8, Color.WHITESMOKE), i, j);
				else
					pane.add(new Rectangle(pane.getWidth() / 8, pane.getHeight() / 8, Color.valueOf("#303030")), i, j);
			}
		}
		pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeColorOfField((int)(event.getX() / (pane.getWidth() / 8)), (int)(event.getY() / (pane.getHeight() / 8))));
		pane.heightProperty().addListener(observable -> resizeFields());
		pane.widthProperty().addListener(observable -> resizeFields());
	}
	
	private void resizeFields () {
		for (int i = 0; i < pane.getColumnCount(); i++) {
			for (int j = 0; j < pane.getRowCount(); j++) {
				Rectangle changeSize = (Rectangle) pane.getChildren().get(i + (j * 8));
				changeSize.setWidth(pane.getWidth() / 8);
				changeSize.setHeight(pane.getHeight() / 8);
			}
		}
	}
	
	private void changeColorOfField (int column, int row) {
		Rectangle rectangle = new Rectangle();
		
		for (Node node : pane.getChildren()) {
			if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
				rectangle = (Rectangle) node;
				break;
			}
		}
		
		if (column % 2 == row % 2) {
			if (rectangle.getFill().equals(Color.WHITESMOKE))
				rectangle.setFill(Color.INDIANRED);
			else
				rectangle.setFill(Color.WHITESMOKE);
		} else if (rectangle.getFill().equals(Color.valueOf("#303030")))
			rectangle.setFill(Color.INDIANRED);
		else
			rectangle.setFill(Color.valueOf("#303030"));
	}
}
