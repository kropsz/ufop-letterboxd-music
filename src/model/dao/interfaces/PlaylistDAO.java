package model.dao.interfaces;

import java.util.List;

import model.entities.Playlist;
import model.entities.Usuario;

public interface PlaylistDAO {
    Playlist findById(Integer id);
    void salvarMusicaPlaylist(Integer playlistID, Integer musicaID);
    List<Playlist> findAll();
    List<Playlist> findAllByUsername(Usuario usuario);
    void create(Playlist entity);
    void update(Playlist entity);
    void delete(Integer id);
}