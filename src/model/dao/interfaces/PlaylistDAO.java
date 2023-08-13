package model.dao.interfaces;

import java.util.List;

import javafx.collections.ObservableList;
import model.entities.Musica;
import model.entities.Playlist;
import model.entities.Usuario;

public interface PlaylistDAO {
    Playlist salvarMusicaPlaylist(Playlist playlist, Musica musica);

    ObservableList<Musica> getMusicasDaPlaylist(Playlist playlist);

    int countPlaylistsByUsuario(Usuario usuario);

    boolean verificarMusica(Musica musica, Playlist playlist);

    boolean verificarPropriedadeDaPlaylist(Playlist playlist, Usuario user);

    ObservableList<Playlist> findAllByUsername(Usuario usuario);

    List<Musica> obterMusicasDaPlaylist(Playlist playlist);

    void create(Playlist entity);

    void deletePlaylist(Playlist playlist, Usuario user);

    void deletarMusicaDaPlaylist(int playlistId, int musicaId);

    List<Playlist> findAll(Usuario usuario);

    List<Playlist> findBySearch(String username);
}