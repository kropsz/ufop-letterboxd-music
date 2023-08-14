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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.interfaces.ReviewDAO;
import model.entities.Musica;
import model.entities.Review;
import model.entities.Usuario;

public class ReviewController implements Initializable {

    @FXML
    private Text txtMusica;
    @FXML
    private Text txtNomeMusica;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button buttonPesquisar;
    @FXML
    private Button buttonCriarReview;
    @FXML
    private Button buttonDeletarReview;
    @FXML
    private TableView<Review> tableReview;
    @FXML
    private TableColumn<Review, Usuario> columnUsuario;
    @FXML
    private TableColumn<Review, String> columnReview;

    private Musica musica;
    private Usuario usuario;

    public ReviewController(Musica musica) {
        this.musica = musica;
    }

    public void setUsuario(Usuario usuarioLogado) {
        this.usuario = usuarioLogado;
    }

    private ObservableList<Review> reviews = FXCollections.observableArrayList();

    private ReviewDAO reviewDAO = DaoFactory.createReviewDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();

    }

    public void configureTableColumns() {
        columnUsuario.setCellValueFactory(new PropertyValueFactory<>("user"));
        columnReview.setCellValueFactory(new PropertyValueFactory<>("comentario"));

        columnUsuario.setCellFactory(param -> new TableCell<Review, Usuario>() {
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
        List<Review> reviews = reviewDAO.findAll(musica);
        tableReview.getItems().addAll(reviews);
        txtNomeMusica.setText(musica.getTitulo());
    }

    @FXML
    public void searchButton() {
        String nomeUsername = txtPesquisar.getText();
        List<Review> reviewEncontradas = reviewDAO.findReviewByUsername(nomeUsername, musica);
        exibirReviewsEncontradas(reviewEncontradas);
    }

    private void exibirReviewsEncontradas(List<Review> reviews) {
        tableReview.getItems().clear();
        tableReview.getItems().addAll(reviews);
    }

    public void onButtonCriarReview() {
        abrirJanelaCriarReview();
    }

    @FXML
    private void abrirJanelaCriarReview() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CriarReview.fxml"));
            Parent root = loader.load();
            ReviewCriaçãoController controller = loader.getController();
            controller.setMusica(musica);
            controller.setUsuario(usuario);
            controller.inicializar(this.reviews);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            atualizarTabelaReviewMusica();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void atualizarTabelaReviewMusica() {
        reviews = reviewDAO.findAllByMusica(musica);
        tableReview.setItems(reviews);
    }

    @FXML
    private void onHandleDeletarReview() {
        Review reviewSelecionada = tableReview.getSelectionModel().getSelectedItem();

        if (reviewSelecionada != null) {
            if (reviewSelecionada.getUser().getUsername().equals(usuario.getUsername())) {
                boolean confirmacao = mostrarConfirmacao("Deseja deletar esta review?");
                if (confirmacao) {
                    reviewDAO.deleteReview(reviewSelecionada);
                    atualizarTabelaReviewMusica();
                }
            } else {
                mostrarMensagem("Você só pode deletar suas próprias reviews.");
            }
        } else {
            mostrarMensagem("Selecione uma review para deletar.");
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

}