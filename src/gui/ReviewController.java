package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.security.auth.callback.Callback;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.dao.DaoFactory;
import model.dao.interfaces.ReviewDAO;
import model.entities.Musica;
import model.entities.Review;
import model.entities.Usuario;

public class ReviewController implements Initializable{
    
    @FXML
    private Text txtMusica;
    @FXML
    private Text txtNomeMusica;
    @FXML
    private Text txtArtista;
    @FXML
    private Text txtEstilo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button buttonPesquisar;
    @FXML
    private TableView<Review> tableReview;
    @FXML
    private TableColumn<Review, Usuario> columnUsuario;
    @FXML
    private TableColumn<Review, String> columnReview;

    private Musica musica;

    public ReviewController(Musica musica){
        this.musica = musica;
    }

    public void setMusica(Musica musica){
        this.musica = musica;
    }

    private ReviewDAO reviewDAO = DaoFactory.createReviewDAO();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
        
    }

    public void configureTableColumns(){
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
    }
}