package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import database.DB;
import database.DbException;
import model.dao.interfaces.UsuarioDAO;
import model.entities.Usuario;

public class UsuarioDaoJDBC implements UsuarioDAO {

    private Connection conn;

    public UsuarioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Usuario findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Usuario usuario;
        try {
            st = conn.prepareStatement("""
                    INSERT INTO usuarios (nome, username, senha) VALUES (?, ?, ?)
                        """);

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                // Criar um objeto Usuario com os dados do resultado
                usuario = new Usuario();
                usuario.setId(rs.getInt("ID"));
                usuario.setNome(rs.getString("Nome"));
                usuario.setUsername(rs.getString("Username"));
                return usuario;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Usuario> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void create(Usuario usuario) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO usuarios (Nome, Username, Password) " +
                    "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, usuario.getNome());
            st.setString(2, usuario.getUsername());
            st.setString(3, usuario.getPassword());
            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    usuario.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Erro inesperado! Nenhuma linha foi afetada");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Usuario entity) {
       
    }

    @Override
    public void delete(Integer id) {
        
        
    }

    @Override
    public Usuario findByUsername(String user) {
        return null;
    }

}