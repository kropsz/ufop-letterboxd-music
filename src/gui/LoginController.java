package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassoword;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private Button cadastrarButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loginButtonOnAction(ActionEvent e) {
        if (txtUsername.getText().isBlank() == false && txtPassoword.getText().isBlank() == false) {
            loginMessageLabel.setText("Voce tentou fazer login");
        } else {
            loginMessageLabel.setText("Entre com seu username e senha");
        }
    }

    @FXML
    private void handleCadastrarButton(ActionEvent o) {
        try {
            // Carrega o arquivo FXML da tela de cadastro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CadastroView.fxml"));
            Parent root = loader.load();

            // Cria uma nova janela para a tela de cadastro
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}