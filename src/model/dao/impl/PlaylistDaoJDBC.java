package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import database.DB;
import database.DbException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dao.interfaces.PlaylistDAO;
import model.entities.Musica;
import model.entities.Playlist;
import model.entities.Usuario;

public class PlaylistDaoJDBC implements PlaylistDAO {
    private Connection conn;

    public PlaylistDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public ObservableList<Playlist> findAllByUsername(Usuario usuario) {
        ObservableList<Playlist> playlists = FXCollections.observableArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("""
                        SELECT *
                        FROM playlists
                        WHERE Username = ?
                    """);
            st.setString(1, usuario.getUsername());
            rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nome = rs.getString("Nome");
                String desc = rs.getString("Descriacao");
                Playlist playlist = new Playlist(id, nome, usuario, desc);
                playlists.add(playlist);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        return playlists;
    }

    @Override
    public Playlist salvarMusicaPlaylist(Playlist playlist, Musica musica) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("""
                    INSERT INTO musicas_playlists (PlaylistID, MusicaID) VALUES (?, ?)
                        """);
            st.setInt(1, playlist.getId());
            st.setInt(2, musica.getId());
            st.executeUpdate();
            return playlist;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Musica> obterMusicasDaPlaylist(Playlist playlist) {
        List<Musica> musicas = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("""
                             SELECT  m.ID, m.Titulo, m.Artista, m.Estilo, m.AnoLançamento
                             FROM musicas m
                             INNER JOIN musicas_playlists mp ON m.ID = mp.MusicaID
                             WHERE mp.PlaylistID = ?       
                    """);
                    st.setInt(1, playlist.getId());
                    rs = st.executeQuery();
                    while(rs.next()){
                        Musica musica = new Musica();
                        musica.setId(rs.getInt("ID"));
                        musica.setTitulo(rs.getString("Titulo"));
                        musica.setArtista(rs.getString("Artista"));
                        musica.setEstilo(rs.getString("Estilo"));
                        musica.setAnoLanc(rs.getInt("AnoLançamento"));
                        musicas.add(musica);

                    }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
        return musicas;
    }

    @Override
    public Playlist findById(Integer id) {

        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public void create(Playlist playlist) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("""
                        INSERT INTO playlists(Nome, Username, Descriacao)
                        VALUES (?, ?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, playlist.getNome());
            st.setString(2, playlist.getUser().getUsername());
            st.setString(3, playlist.getDesc());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    playlist.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("No rows affected");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Playlist playlist) {

        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Integer id) {

        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Playlist> findAll() {

        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ObservableList<Musica> getMusicasDaPlaylist(Playlist playlist) {
        ObservableList<Musica> musicasDaPlaylist = FXCollections.observableArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("""
                SELECT * FROM musicas m JOIN musicas_playlists mp ON m.ID = mp.MusicaID WHERE mp.PlaylistID = ?
                    """);
            st.setInt(1, playlist.getId());
            rs = st.executeQuery();
            while(rs.next()){
                Musica musica = new Musica();
                musica.setId(rs.getInt("ID"));
                musica.setArtista(rs.getString("Artista"));
                musica.setEstilo(rs.getString("Estilo"));
                musica.setAnoLanc(rs.getInt("AnoLançamento"));
                musicasDaPlaylist.add(musica);

            }        
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

        return musicasDaPlaylist;
    }

}