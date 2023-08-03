package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.interfaces.UsuarioDAO;
import model.entities.Usuario;

public class CadastroController{

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
        // Lógica para cadastrar o usuário
        String nome = txtNewNome.getText();
        String username = txtNewUsername.getText();
        String senha = txtNewPassoword.getText();
        Usuario usuario = new Usuario(nome, username, senha);
        usuarioDAO.create(usuario);

         if (txtNewUsername.getText().isBlank() != false && txtNewNome.getText().isBlank() != false
                && txtNewPassoword.getText().isBlank() != false) {
            cadastroMessageLabel.setText("Tente novamente");
        } else {
            cadastroMessageLabel.setText("Cadastro Realizado com Sucesso !");
        }

        txtNewNome.clear();
        txtNewUsername.clear();
        txtNewPassoword.clear();
        
        
}   
}