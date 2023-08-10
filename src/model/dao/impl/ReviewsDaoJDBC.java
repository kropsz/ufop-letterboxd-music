package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DB;
import database.DbException;
import model.dao.interfaces.ReviewDAO;
import model.entities.Musica;
import model.entities.Review;
import model.entities.Usuario;

public class ReviewsDaoJDBC implements ReviewDAO{
    private Connection conn;
    
    public ReviewsDaoJDBC(Connection conn){
        this.conn = conn;
    }
    @Override
    public Review findByUsername(Usuario usuario) {
        return null;
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
            while(rs.next()){
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
        }finally{
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
        return reviews;
    }

    @Override
    public void create(Review Reviews) {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}