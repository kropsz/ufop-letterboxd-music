package model.dao.interfaces;

import java.util.List;

import model.entities.Musica;

public interface MusicaDAO {
    List<Musica> findByMusica(String musica);

    List<Musica> findAll();
}