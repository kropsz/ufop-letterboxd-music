package model.dao.interfaces;

import java.util.List;

import model.entities.Musica;

public interface MusicaDAO {
    Musica findByMusica(String musica);
    Musica findById(Integer id);
    List<Musica> findAll();
    void create(Musica musica);
    void update(Musica musica);
    void delete(Integer id);
}