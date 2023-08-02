package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MainViewController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassoword;
    @FXML
    private Label loginMessageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loginButtonOnAction(ActionEvent e) {
        loginMessageLabel.setText("Voce tentou fazer login");
    }

}