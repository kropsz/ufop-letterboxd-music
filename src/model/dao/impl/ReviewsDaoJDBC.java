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
import model.dao.interfaces.ReviewDAO;
import model.entities.Musica;
import model.entities.Review;
import model.entities.Usuario;

public class ReviewsDaoJDBC implements ReviewDAO {
    private Connection conn;

    public ReviewsDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Review> findAll(Musica musica) {
        List<Review> reviews = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("""
                    SELECT r.*, u.*
                    FROM reviews r
                    INNER JOIN usuarios u ON r.Username = u.Username
                    WHERE r.MusicaID = ?
                        """);
            st.setInt(1, musica.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String comentario = rs.getString("Comentario");
                Usuario usuario = new Usuario();
                usuario.setUsername(rs.getString("Username"));
                usuario.setNome(rs.getString("Nome"));
                Review review = new Review(id, musica, usuario, comentario);
                reviews.add(review);
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
        return reviews;
    }

    @Override
    public void create(Review review) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("""
                        INSERT INTO reviews (MusicaID, Username, Comentario)
                        VALUES (?, ?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, review.getMusica().getId());
            st.setString(2, review.getUser().getUsername());
            st.setString(3, review.getComentario());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteReview(Review review) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("""
                    DELETE FROM reviews WHERE ID = ?
                    """);
            st.setInt(1, review.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public int countReviewsByUsuario(Usuario usuario) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("""
                        SELECT COUNT(*) AS total FROM reviews WHERE Username = ?
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
    public ObservableList<Review> findAllByMusica(Musica musica) {
        ObservableList<Review> reviews = FXCollections.observableArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("""
                    SELECT r.*, u.*
                    FROM reviews r
                    INNER JOIN usuarios u ON r.Username = u.Username
                    WHERE r.MusicaID = ?
                        """);
            st.setInt(1, musica.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String comentario = rs.getString("Comentario");
                Usuario usuario = new Usuario();
                usuario.setUsername(rs.getString("Username"));
                usuario.setNome(rs.getString("Nome"));
                Review review = new Review(id, musica, usuario, comentario);
                reviews.add(review);
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
        return reviews;
    }

    @Override
    public List<Review> findReviewByUsername(String nomeUsername, Musica musica) {
        List<Review> reviewsEncontradas = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        Review review;
        try {
            st = conn.prepareStatement("""
                SELECT r.ID, r.Comentario, m.ID AS MusicaID, m.Titulo, m.Artista, u.Username
                FROM reviews r
                INNER JOIN musicas m ON r.MusicaID = m.ID
                INNER JOIN usuarios u ON r.Username = u.Username
                WHERE u.Username LIKE ? AND m.ID = ?;                
                    """);
            st.setString(1, "%" + nomeUsername + "%");
            st.setInt(2, musica.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                review = new Review();
                review.setId(rs.getInt("ID"));
                review.setComentario(rs.getString("Comentario"));

        
                musica.setId(rs.getInt("MusicaID"));
                musica.setTitulo(rs.getString("Titulo"));
                musica.setArtista(rs.getString("Artista"));

                Usuario usuario = new Usuario();
                usuario.setUsername(rs.getString("Username"));

                review.setMusica(musica);
                review.setUser(usuario);

                reviewsEncontradas.add(review);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
        return reviewsEncontradas;
    }

}