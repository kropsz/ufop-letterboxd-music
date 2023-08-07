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
    public List<Usuario> findAll() {

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
    public Usuario findByUsername(String username) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("""
                SELECT * FROM usuarios WHERE Username = ?
                    """);
            st.setString(1, username);
            rs = st.executeQuery();
            if(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("ID"));
                usuario.setNome(rs.getString("Nome"));
                usuario.setUsername(rs.getString("Username"));
                usuario.setPassword(rs.getString("Password"));
                return usuario;
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        
        return null;
        }

    @Override
    public boolean verifyUser(String user, String password) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("""
                    SELECT * FROM usuarios WHERE Username = ? AND Password = ?
                        """);
            st.setString(1, user);
            st.setString(2, password);
            rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());

        }
    }

}