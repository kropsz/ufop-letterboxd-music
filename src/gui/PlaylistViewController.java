package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Musica;
import model.entities.Playlist;
import model.entities.Usuario;

public class PlaylistViewController implements Initializable {
    @FXML
    private Label txtPlaylistName;
    @FXML
    private Label txtDescLabel;
    @FXML
    private Button buttonAddSong;
    // @FXML
    // private Button buttonChangeDesc;
    @FXML
    private TableView<Musica> tabelaMusica;
    @FXML
    private TableColumn<Musica, String> columnTitulo;
    @FXML
    private TableColumn<Musica, String> columnArtista;
    @FXML
    private TableColumn<Musica, String> columnEstilo;
    @FXML
    private TableColumn<Musica, Integer> columnAnoLanc;

    // private Usuario usuario;
    private Playlist playlist;

    public void setUsuario(Usuario usuario) {
        // this.usuario = usuario;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
    }

    public void configureTableColumns() {
        if (playlist != null) {
            txtDescLabel.setText(playlist.getDesc());
        }
        columnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        columnEstilo.setCellValueFactory(new PropertyValueFactory<>("estilo"));
        columnAnoLanc.setCellValueFactory(new PropertyValueFactory<>("anoLanc"));
    }

    public void addSong() {
        abreTelaMusica();
    }

    public void abreTelaMusica() {
    }

}