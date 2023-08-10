package model.dao.interfaces;

import java.util.List;

import model.entities.Musica;
import model.entities.Review;
import model.entities.Usuario;

public interface ReviewDAO{
    Review findByUsername(Usuario usuario);
    List<Review> findAll(Musica musica);
    void create(Review Reviews);
    void delete(Integer id);
}