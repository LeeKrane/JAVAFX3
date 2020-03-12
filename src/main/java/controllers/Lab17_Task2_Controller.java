package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class Lab17_Task2_Controller implements Initializable {
	@FXML
	private GridPane pane;
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		for (int i = 0; i < pane.getColumnCount(); i++) {
			for (int j = 0; j < pane.getRowCount(); j++) {
				if (i % 2 == j % 2)
					pane.add(new Rectangle(64, 64, Color.WHITESMOKE), i, j);
				else
					pane.add(new Rectangle(64, 64, Color.DIMGREY), i, j);
			}
		}
		pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeColorOfField((int)(event.getX() / 64), (int)(event.getY() / 64)));
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
			if (rectangle.getFill().equals(Color.WHITESMOKE)) rectangle.setFill(Color.INDIANRED);
			else rectangle.setFill(Color.WHITESMOKE);
		} else if (rectangle.getFill().equals(Color.DIMGREY)) rectangle.setFill(Color.INDIANRED);
		else rectangle.setFill(Color.DIMGREY);
	}
}
