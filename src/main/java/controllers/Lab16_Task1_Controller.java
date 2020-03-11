package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Lab16_Task1_Controller implements Initializable {
    @FXML
    private RadioButton variant1;
    
    @FXML
    private RadioButton variant2;
    
    @FXML
    private TextField text1;
    
    @FXML
    private TextField text2;
    
    @FXML
    private TextField output;
    
    @FXML
    private Button apply;
    
    @FXML
    private Button clear;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apply.setOnAction(e -> apply());
        clear.setOnAction(e -> clear());
    }
    
    private void apply () {
        output.clear();
        if (text1.getText().isEmpty() || text2.getText().isEmpty())
            output.appendText("Both TextFields must be filled.");
        else if (variant1.isSelected())
            output.appendText(text1.getText() + text2.getText());
        else
            output.appendText(text2.getText() + text1.getText());
    }
    
    private void clear () {
        text1.clear();
        text2.clear();
        output.clear();
        variant1.setSelected(true);
    }
}
