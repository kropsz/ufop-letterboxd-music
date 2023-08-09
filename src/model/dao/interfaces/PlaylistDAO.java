package model.dao.interfaces;

import java.util.List;

import javafx.collections.ObservableList;
import model.entities.Musica;
import model.entities.Playlist;
import model.entities.Usuario;

public interface PlaylistDAO {
    Playlist findById(Integer id);
    Playlist salvarMusicaPlaylist(Playlist playlist, Musica musica);
    ObservableList<Musica> getMusicasDaPlaylist(Playlist playlist);
    List<Playlist> findAll();
    List<Playlist> findAllByUsername(Usuario usuario);
    List<Musica> obterMusicasDaPlaylist(Playlist playlist);
    void create(Playlist entity);
    void update(Playlist entity);
    void delete(Integer id);
}