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
import model.dao.DaoFactory;
import model.dao.interfaces.UsuarioDAO;
import model.entities.Usuario;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassoword;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private Button cadastrarButton;
    @FXML
    private Button loginButton;

    private UsuarioDAO usuarioDAO = DaoFactory.createuUsuarioDAO();;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loginButtonOnAction(ActionEvent e) {
        String username = txtUsername.getText();
        String password = txtPassoword.getText();
        Usuario usuario = new Usuario(username, password);
        usuarioDAO.findByUsername(username);
        if (usuario.getUsername().equals(username) && usuario.getPassword().equals(password)) {
            // Login bem-sucedido
            loginMessageLabel.setText("Login com Sucesso ! ");{
            abrirJanelaMusicas();
        } 
        } else {
            // Login inválido
            System.out.println("Usuário ou senha inválidos");
        }
    }


    private void abrirJanelaMusicas() {
        try {
            // Carrega o arquivo FXML da janela de músicas
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MusicaView.fxml"));
            Parent root = loader.load();

            // Cria uma nova janela para a janela de músicas
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fecha a janela de login
            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleCadastrarButton() {
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