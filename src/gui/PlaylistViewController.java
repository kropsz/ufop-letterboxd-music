package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.interfaces.PlaylistDAO;
import model.entities.Musica;
import model.entities.Playlist;

public class PlaylistViewController implements Initializable {
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
    @FXML
    private Label txtPlaylistName;
    @FXML
    private Label txtDescLabel;
    @FXML
    private Button buttonAddSong;

    private Playlist playlist;

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    private PlaylistDAO playlistDAO = DaoFactory.createPlaylistDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        columnEstilo.setCellValueFactory(new PropertyValueFactory<>("estilo"));
        columnAnoLanc.setCellValueFactory(new PropertyValueFactory<>("anoLanc"));
        configureTableColumns();
    }

    public void configureTableColumns() {

        if (playlist != null) {
            txtPlaylistName.setText(playlist.getNome());
            txtDescLabel.setText(playlist.getDesc());
            List<Musica> musicas = playlistDAO.obterMusicasDaPlaylist(playlist);
            tabelaMusica.getItems().addAll(musicas);
        }
    }

    @FXML
    private void adicionarMusica(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AdicionarMusica.fxml"));
            Parent root = loader.load();

            
            AdicionarMusicaController adicionarMusicaController = loader.getController();
            adicionarMusicaController.setPlaylist(playlist);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Selecionar Músicas");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Atualizar a tabela da playlist do usuário após a janela ser fechada
            atualizarTabelaPlaylistUsuario();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void atualizarTabelaPlaylistUsuario() {
        if (playlist != null) {
            ObservableList<Musica> musicasDaPlaylist = playlistDAO.getMusicasDaPlaylist(playlist);
            tabelaMusica.setItems(musicasDaPlaylist);
        }
    }
}
