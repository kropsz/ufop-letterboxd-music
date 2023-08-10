package model.dao.interfaces;

import java.util.List;

import javafx.collections.ObservableList;
import model.entities.Musica;
import model.entities.Review;
import model.entities.Usuario;

public interface ReviewDAO{
    Review findByUsername(Usuario usuario);
    List<Review> findAll(Musica musica);
    List<Review> findReviewByUsername(String nomeUsername);
    int countReviewsByUsuario(Usuario usuario);
    void create(Review Reviews);
    ObservableList<Review> findAllByMusica(Musica musica);
    void delete(Integer id);
}