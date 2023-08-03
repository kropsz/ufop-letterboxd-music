package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CadastroController {

    @FXML
    private TextField txtNewUsername;
    @FXML
    private PasswordField txtNewPassoword;
    @FXML
    private TextField txtNewNome;
    @FXML
    private Label cadastroMessageLabel;

    public void CadastroButtonOnAction(ActionEvent e) {
        if (txtNewUsername.getText().isBlank() == false && txtNewNome.getText().isBlank() == false
                && txtNewPassoword.getText().isBlank() == false) {
            cadastroMessageLabel.setText("Voce tentou fazer login");
        } else {
            cadastroMessageLabel.setText("Entre com seu nome, username e senha");
        }
    }
}
