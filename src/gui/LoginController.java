package gui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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


public class LoginController {

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
    @FXML
    private Stage stage;

    @FXML

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private Usuario usuarioLogado;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    private UsuarioDAO usuarioDAO = DaoFactory.createuUsuarioDAO();;

    public void loginButtonOnAction() {
        String username = txtUsername.getText();
        String password = txtPassoword.getText();

        boolean loginValido = usuarioDAO.verifyUser(username, password);
        if (loginValido) {
            loginMessageLabel.setText("Login com Sucesso ! ");
            usuarioLogado = usuarioDAO.findByUsername(username);
            abrirJanelaUsuario();
        } else {
            loginMessageLabel.setText("Login inválido");
        }
    }

    private void abrirJanelaUsuario() {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Usuario.fxml"));
            UsuarioController usuarioController = new UsuarioController(usuarioLogado);
            loader.setController(usuarioController);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCadastrarButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Cadastro.fxml"));
            Parent root = loader.load(); 
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}