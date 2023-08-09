package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DB;
import database.DbException;
import model.dao.interfaces.MusicaDAO;
import model.entities.Musica;

public class MusicaDaoJDBC implements MusicaDAO {
    private Connection conn;

    public MusicaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Musica> findByMusica(String titulo) {
        List<Musica> musicasEncontradas = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        Musica musica;
        try {
            st = conn.prepareStatement("""
                    SELECT * FROM musicas WHERE titulo LIKE ?
                        """);
            st.setString(1, "%" + titulo + "%");
            rs = st.executeQuery();
            while (rs.next()) {
                musica = new Musica();
                musica.setId(rs.getInt("ID"));
                musica.setTitulo(rs.getString("Titulo"));
                musica.setArtista(rs.getString("Artista"));
                musica.setEstilo(rs.getString("Estilo"));
                musica.setAnoLanc(rs.getInt("AnoLançamento"));
                musicasEncontradas.add(musica);

            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return musicasEncontradas;
    }

    @Override
    public Musica findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Musica> findAll() {
        List<Musica> musicas = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        Musica musica;
        try {
            st = conn.prepareStatement("""
                    SELECT * FROM musicas
                         """);
            rs = st.executeQuery();
            while (rs.next()) {
                musica = new Musica();
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
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return musicas;
    }
    @Override
    public void create(Musica musica) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void update(Musica musica) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}