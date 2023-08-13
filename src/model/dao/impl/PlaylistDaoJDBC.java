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
                String desc = rs.getString("Descricao");
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
            while (rs.next()) {
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
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
        return musicas;
    }

    @Override
    public void create(Playlist playlist) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("""
                        INSERT INTO playlists(Nome, Username, Descricao)
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
            while (rs.next()) {
                Musica musica = new Musica();
                musica.setId(rs.getInt("ID"));
                musica.setTitulo(rs.getString("Titulo"));
                musica.setArtista(rs.getString("Artista"));
                musica.setEstilo(rs.getString("Estilo"));
                musica.setAnoLanc(rs.getInt("AnoLançamento"));
                musicasDaPlaylist.add(musica);

            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

        return musicasDaPlaylist;
    }

    @Override
    public int countPlaylistsByUsuario(Usuario usuario) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("""
                    SELECT COUNT(*) AS total FROM playlists WHERE Username = ?
                    """);
            st.setString(1, usuario.getUsername());
            rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public boolean verificarMusica(Musica musica, Playlist playlist) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("""
                    SELECT COUNT(*) AS total FROM musicas_playlists WHERE MusicaID = ? AND PlaylistID = ?
                        """);
            st.setInt(1, musica.getId());
            st.setInt(2, playlist.getId());
            rs = st.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                return total > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletePlaylist(Playlist playlist, Usuario user) {
        PreparedStatement stMusica = null;
        PreparedStatement stPlaylist = null;
        try {
            stMusica = conn.prepareStatement("DELETE FROM musicas_playlists WHERE PlaylistID = ?");
            stMusica.setInt(1, playlist.getId());
            stMusica.executeUpdate();

            stPlaylist = conn.prepareStatement("""
                        DELETE FROM playlists WHERE ID = ? AND Username = ?
                    """);
            stPlaylist.setInt(1, playlist.getId());
            stPlaylist.setString(2, user.getUsername());
            int rowsAffected = stPlaylist.executeUpdate();
            if (rowsAffected == 0) {
                throw new DbException("Não foi possível deletar a playlist.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stPlaylist);
            DB.closeStatement(stMusica);
        }
    }

    @Override
    public boolean verificarPropriedadeDaPlaylist(Playlist playlist, Usuario usuario) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT ID FROM playlists WHERE ID = ? AND Username = ?");
            st.setInt(1, playlist.getId());
            st.setString(2, usuario.getUsername());
            rs = st.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);

        }
    }

    @Override
    public void deletarMusicaDaPlaylist(int playlistId, int musicaId) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM musicas_playlists WHERE PlaylistID = ? AND MusicaID = ?");
            st.setInt(1, playlistId);
            st.setInt(2, musicaId);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) {
                throw new DbException("Não foi possível excluir a música da playlist.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Playlist> findAll(Usuario usuario) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Playlist> playlists = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                    """
                            SELECT p.ID, p.Nome, p.Descricao, u.Username, u.Nome AS UserNome FROM playlists p
                            JOIN usuarios u ON p.Username = u.Username
                            WHERE p.Username != ?
                            """);
            st.setString(1, usuario.getUsername());
            rs = st.executeQuery();

            while (rs.next()) {
                Playlist playlist = new Playlist();
                playlist.setId(rs.getInt("ID"));
                playlist.setNome(rs.getString("Nome"));
                playlist.setDesc(rs.getString("Descricao"));
                Usuario owner = new Usuario();
                owner.setUsername(rs.getString("Username"));
                owner.setNome(rs.getString("Nome"));
                playlist.setUser(owner);

                playlists.add(playlist);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

        return playlists;
    }

    @Override
    public List<Playlist> findBySearch(String nomeUsername) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Playlist> playlists = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                    """
                            SELECT p.ID, p.Nome, u.Username, u.Nome AS UserNome FROM playlists p
                            JOIN usuarios u ON p.Username = u.Username
                            WHERE u.Username = ?
                            """);
            st.setString(1, nomeUsername);
            rs = st.executeQuery();

            while (rs.next()) {
                Playlist playlist = new Playlist();
                playlist.setId(rs.getInt("ID"));
                playlist.setNome(rs.getString("Nome"));
                playlist.setDesc(rs.getString("Descricao"));
                Usuario owner = new Usuario();
                owner.setUsername(rs.getString("Username"));
                owner.setNome(rs.getString("UserNome"));

                playlist.setUser(owner);

                playlists.add(playlist);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

        return playlists;
    }

}