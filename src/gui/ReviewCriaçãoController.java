package gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.dao.DaoFactory;
import model.dao.interfaces.ReviewDAO;
import model.entities.Musica;
import model.entities.Review;
import model.entities.Usuario;

public class ReviewCriaçãoController {
    @FXML
    private Button buttonCriarReview;
    @FXML
    private TextField txtReview;

    private Musica musica;
    private Usuario user;
    private ObservableList<Review> reviews;

    public void inicializar(ObservableList<Review> reviews) {
        this.reviews = reviews;
    }

    public void setUsuario(Usuario usuario) {
        this.user = usuario;
    }

    public void setMusica(Musica musica) {
        this.musica = musica;
    }


    private ReviewDAO reviewDAO = DaoFactory.createReviewDAO();


    public void onButtonCriarReview() {
        criarReview();
    }

    @FXML
    private void criarReview(){
        String text = txtReview.getText();
        
        Review review = new Review(musica, user, text);
        reviewDAO.create(review);
        txtReview.getScene().getWindow().hide();
        reviews.add(review);
    }
}