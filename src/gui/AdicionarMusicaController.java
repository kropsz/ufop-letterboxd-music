package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.interfaces.MusicaDAO;
import model.dao.interfaces.PlaylistDAO;
import model.entities.Musica;
import model.entities.Playlist;

public class AdicionarMusicaController implements Initializable{

    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button buttonPesquisar;
    @FXML
    private Button buttonAddSong;
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

    private Playlist playlistUsuario;

    public void setPlaylist(Playlist playlist){
        this.playlistUsuario = playlist;
    }

    private MusicaDAO musicaDAO = DaoFactory.createMusicaDAO();
    private PlaylistDAO playlistDAO = DaoFactory.createPlaylistDAO();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        columnEstilo.setCellValueFactory(new PropertyValueFactory<>("estilo"));
        columnAnoLanc.setCellValueFactory(new PropertyValueFactory<>("anoLanc"));

        List<Musica> musicas = musicaDAO.findAll();

        tabelaMusica.getItems().addAll(musicas);

    }

    @FXML
    public void searchButton() {
        String nomeMusica = txtPesquisar.getText();
        List<Musica> musicasEncontradas = musicaDAO.findByMusica(nomeMusica);
        exibirMusicasEncontradas(musicasEncontradas);
    }

    private void exibirMusicasEncontradas(List<Musica> musicas) {
        tabelaMusica.getItems().clear();
        tabelaMusica.getItems().addAll(musicas);
    }

    @FXML
    private void onHandleAdicionarMusica() {
        Musica musicaSelecionada = tabelaMusica.getSelectionModel().getSelectedItem();
        if (musicaSelecionada != null && playlistUsuario != null) {
            playlistUsuario = playlistDAO.salvarMusicaPlaylist(playlistUsuario, musicaSelecionada);
            closeWindow();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) buttonAddSong.getScene().getWindow();
        stage.close();
    }

}
