package model.dao.interfaces;

import java.util.List;

import model.entities.Usuario;

public interface UsuarioDAO{
    Usuario findById(Integer id);
    Usuario findByUsername(String user);
    List<Usuario> findAll();
    void create(Usuario entity);
    void update(Usuario entity);
    void delete(Integer id);
}