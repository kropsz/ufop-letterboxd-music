package gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.entities.Usuario;

public class UsuarioViewController {
    @FXML
    private Button buttonMusica;
    @FXML
    private Button buttonPlaylist;
    @FXML
    private Button buttonCriarPlaylist;
    @FXML
    private Button buttonReviews;

    private Usuario usuarioLogado;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public void onButtonMusica() {
        abrirJanelaMusicas();
    }

    public void onButtonCriarPlaylist(){
        abrirJanelaCriarPlaylist();
    }

    public void onButtonPlaylist() {
        abrirJanelaPlaylist();
    }

    private void abrirJanelaMusicas() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MusicaView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirJanelaPlaylist() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SeleçãoDePlaylist.fxml"));
            Parent root = loader.load();
            SelecaoPlaylistController controller = loader.getController();
            controller.setUsuario(usuarioLogado);
            controller.configureTableColumns();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirJanelaCriarPlaylist() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PlaylistCriação.fxml"));
            Parent root = loader.load();
            PlaylistCriaçãoController controller = loader.getController();
            controller.setUsuario(usuarioLogado);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}