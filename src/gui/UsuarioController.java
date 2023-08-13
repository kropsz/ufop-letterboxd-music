package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.interfaces.MusicaDAO;
import model.dao.interfaces.PlaylistDAO;
import model.dao.interfaces.ReviewDAO;
import model.entities.Musica;
import model.entities.Playlist;
import model.entities.Usuario;

public class UsuarioController implements Initializable {

    @FXML
    private Button buttonCriarPlaylist;
    @FXML
    private Button buttonPesquisar;
    @FXML
    private Button buttonReview;
    @FXML
    private Button buttonRemoverMusica;
    @FXML
    private Button buttonSelecionarPlaylist;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Text txtNomeUsuario;
    @FXML
    private Text txtInfoPlaylists;
    @FXML
    private Text txtTotalPlaylists;
    @FXML
    private Text txtTotalReviews;
    @FXML
    private TableView<Musica> tabelaMusica;
    @FXML
    private TableColumn<Musica, String> columnTitulo;
    @FXML
    private TableColumn<Musica, String> columnArtista;
    @FXML
    private TableView<Playlist> tablePlaylist;
    @FXML
    private TableColumn<Playlist, String> columnNome;
    @FXML
    private TableColumn<Playlist, String> columnDesc;

    private MusicaDAO musicaDAO = DaoFactory.createMusicaDAO();
    private PlaylistDAO playlistDAO = DaoFactory.createPlaylistDAO();
    private ReviewDAO reviewDAO = DaoFactory.createReviewDAO();

    private Usuario usuarioLogado;

    private ObservableList<Playlist> playlists = FXCollections.observableArrayList();

    public UsuarioController(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
        atualizarTotais();

    }

    public void configureTableColumns() {
        columnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        if (usuarioLogado != null) {
            List<Musica> musicas = musicaDAO.findAll();
            List<Playlist> playlist = playlistDAO.findAllByUsername(usuarioLogado);
            tablePlaylist.getItems().addAll(playlist);
            tabelaMusica.getItems().addAll(musicas);
            txtNomeUsuario.setText(usuarioLogado.getUsername());
        }
    }

    private void atualizarTotais() {
        int totalPlaylists = playlistDAO.countPlaylistsByUsuario(usuarioLogado);
        int totalReviews = reviewDAO.countReviewsByUsuario(usuarioLogado);

        txtTotalPlaylists.setText(String.valueOf(totalPlaylists));
        txtTotalReviews.setText(String.valueOf(totalReviews));
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

    public void onButtonCriarPlaylist() {
        abrirJanelaCriarPlaylist();
    }

    private void abrirJanelaCriarPlaylist() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PlaylistCriação.fxml"));
            Parent root = loader.load();
            PlaylistCriaçãoController controller = loader.getController();
            controller.setUsuario(usuarioLogado);
            controller.inicializar(this.playlists);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            atualizarTotais();
            atualizarTabelaPlaylistUsuario();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void atualizarTabelaPlaylistUsuario() {
        playlists = playlistDAO.findAllByUsername(usuarioLogado);
        tablePlaylist.setItems(playlists);
    }

    @FXML
    private void onHandleDeletarReview() {
        Playlist playlistSelecionada = tablePlaylist.getSelectionModel().getSelectedItem();
        if (playlistSelecionada != null) {
            if (playlistSelecionada.getUser().getUsername().equals(usuarioLogado.getUsername())) {
                boolean confirmacao = mostrarConfirmacao("Deseja deletar esta playlist?");
                if (confirmacao) {
                    playlistDAO.deletePlaylist(playlistSelecionada, usuarioLogado);
                    atualizarTotais();
                    atualizarTabelaPlaylistUsuario();
                }
            } else {
                mostrarMensagem("Você só pode deletar suas próprias playlists.");
            }
        } else {
            mostrarMensagem("Selecione uma playlist para deletar.");
        }
    }

    private boolean mostrarConfirmacao(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    private void mostrarMensagem(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        alert.showAndWait();
    }

    @FXML
    private void onSelecionarButtonClick() {
        Playlist playlistSelecionada = tablePlaylist.getSelectionModel().getSelectedItem();
        if (playlistSelecionada != null) {
            abrirCenaExibirPlaylist(playlistSelecionada);
        }
    }

    @FXML
    private void abrirCenaExibirPlaylist(Playlist playlist) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Playlists.fxml"));
            Parent root = loader.load();
            PlaylistViewController controller = loader.getController();
            if (playlist != null) {
                controller.setUsuario(usuarioLogado);
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

    @FXML
    private void onSelecionarButtonClickMusica() {
        Musica musicaSelecionada = tabelaMusica.getSelectionModel().getSelectedItem();
        if (musicaSelecionada != null) {
            abrirCenaExibirReviews(musicaSelecionada);
        }
    }

    @FXML
    private void abrirCenaExibirReviews(Musica musica) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Review.fxml"));
            ReviewController controller = new ReviewController(musica);
            controller.setUsuario(usuarioLogado);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            atualizarTotais();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
