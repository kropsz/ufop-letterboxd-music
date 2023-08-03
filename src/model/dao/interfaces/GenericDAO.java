package model.dao.interfaces;

import java.util.List;

public interface GenericDAO<T> {
    T findById(Integer id);
    List<T> findAll();
    void create(T entity);
    void update(T entity);
    void delete(Integer id);
}