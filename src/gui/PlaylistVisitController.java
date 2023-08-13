package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.dao.DaoFactory;
import model.dao.interfaces.PlaylistDAO;
import model.entities.Musica;
import model.entities.Playlist;
import model.entities.Usuario;

public class PlaylistVisitController implements Initializable {
    @FXML
    private Text txtNomePlaylist;
    @FXML
    private Text txtUsuario;
    @FXML
    private TableView<Musica> tableMusicas;
    @FXML
    private TableColumn<Musica, String> columnTitulo;
    @FXML
    private TableColumn<Musica, String> columnArtista;
    @FXML
    private TableColumn<Musica, String> columnEstilo;
    @FXML
    private TableColumn<Musica, Integer> columnAno;

    private Usuario usuario;
    private Playlist playlist;
    private PlaylistDAO playlistDAO = DaoFactory.createPlaylistDAO();

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
    }

    public void configureTableColumns() {
        columnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        columnEstilo.setCellValueFactory(new PropertyValueFactory<>("estilo"));
        columnAno.setCellValueFactory(new PropertyValueFactory<>("anoLanc"));
        if (playlist != null) {
            List<Musica> musicas = playlistDAO.obterMusicasDaPlaylist(playlist);
            tableMusicas.getItems().addAll(musicas);
            txtNomePlaylist.setText(playlist.getNome());
            txtUsuario.setText(usuario.getUsername());
        }
    }

}