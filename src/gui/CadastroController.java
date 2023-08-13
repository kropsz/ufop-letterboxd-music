package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.interfaces.UsuarioDAO;
import model.entities.Usuario;

public class CadastroController {

    @FXML
    private TextField txtNewUsername;
    @FXML
    private PasswordField txtNewPassoword;
    @FXML
    private TextField txtNewNome;
    @FXML
    private Label cadastroMessageLabel;
    @FXML
    private Button cadastrarButton;

    private UsuarioDAO usuarioDAO = DaoFactory.createuUsuarioDAO();;

    @FXML
    private void handleCadastrarButton() {
        String nome = txtNewNome.getText();
        String username = txtNewUsername.getText();
        String senha = txtNewPassoword.getText();
        Usuario usuario = new Usuario(nome, username, senha);
        usuarioDAO.create(usuario);

        if (txtNewUsername.getText().isBlank() != false && txtNewNome.getText().isBlank() != false
                && txtNewPassoword.getText().isBlank() != false) {
            cadastroMessageLabel.setText("Tente novamente");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cadastro");
            alert.setHeaderText(null);
            alert.setContentText("Cadastro efetuado com sucesso!");
            alert.showAndWait();
        }
            Stage stage = (Stage) cadastroMessageLabel.getScene().getWindow();
            stage.close();
        
        txtNewNome.clear();
        txtNewUsername.clear();
        txtNewPassoword.clear();
        
    }
}