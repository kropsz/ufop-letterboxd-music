package gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.dao.DaoFactory;
import model.dao.interfaces.PlaylistDAO;
import model.entities.Playlist;
import model.entities.Usuario;

public class PlaylistCriaçãoController {
    @FXML
    private TextField txtPlaylistName;
    @FXML
    private TextField txtDesc;
    @FXML
    private Button buttonCriarPlaylist;

    private Usuario usuario;
    private ObservableList<Playlist> playlists;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void inicializar(ObservableList<Playlist> playlists) {
        this.playlists = playlists;
    }

    PlaylistDAO playlistDAO = DaoFactory.createPlaylistDAO();

    @FXML
    private void criarPlaylist() {
        String nome = txtPlaylistName.getText();
        String desc = txtDesc.getText();

        Playlist playlist = new Playlist(nome, usuario, desc);
        playlistDAO.create(playlist);
        txtPlaylistName.getScene().getWindow().hide();
        playlists.add(playlist);
    }
}
