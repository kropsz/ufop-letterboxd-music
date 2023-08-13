package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.interfaces.PlaylistDAO;
import model.entities.Playlist;
import model.entities.Usuario;

public class PlaylistUsersController implements Initializable {
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button buttonPesquisar;
    @FXML
    private Button buttonSelecionarPlaylist;
    @FXML
    private TableView<Playlist> tablePlaylists;
    @FXML
    private TableColumn<Playlist, Usuario> columnUsuario;
    @FXML
    private TableColumn<Playlist, String> columnPlaylist;
    @FXML
    private TableColumn<Playlist, String> columnDesc;

    private Usuario usuario;

    public void setUsuario(Usuario usuarioLogado) {
        this.usuario = usuarioLogado;
    }

    private PlaylistDAO playlistDAO = DaoFactory.createPlaylistDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();

    }

    public void configureTableColumns() {
        columnUsuario.setCellValueFactory(new PropertyValueFactory<>("user"));
        columnPlaylist.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));

        columnUsuario.setCellFactory(param -> new TableCell<Playlist, Usuario>() {
            @Override
            protected void updateItem(Usuario user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                    setText(user.getUsername());
                }
            }
        });

        List<Playlist> playlists = playlistDAO.findAll(usuario);
        tablePlaylists.getItems().addAll(playlists);
    }

    @FXML
    public void searchButton() {
        String nomeUsername = txtPesquisar.getText();
        List<Playlist> playlistEcontradas = playlistDAO.findBySearch(nomeUsername);
        exibirPlaylistEncontradas(playlistEcontradas);
    }

    private void exibirPlaylistEncontradas(List<Playlist> playlists) {
        tablePlaylists.getItems().clear();
        tablePlaylists.getItems().addAll(playlists);
    }

    @FXML
    private void onSelecionarButtonClick() {
        Playlist playlistSelecionada = tablePlaylists.getSelectionModel().getSelectedItem();
        if (playlistSelecionada != null) {
            abrirCenaExibirPlaylist(playlistSelecionada);
        }
    }

    @FXML
    private void abrirCenaExibirPlaylist(Playlist playlist) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PlaylistsVisit.fxml"));
            Parent root = loader.load();
            PlaylistVisitController controller = loader.getController();
            if (playlist != null) {
                controller.setUsuario(usuario);
                controller.setPlaylist(playlist);
                controller.configureTableColumns();
            }
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}