package model.dao.interfaces;

import model.entities.Usuario;

public interface UsuarioDAO {
    Usuario findByUsername(String username);

    boolean verifyUser(String user, String password);

    void create(Usuario entity);
}
