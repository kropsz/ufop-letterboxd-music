package model.dao.interfaces;

import java.util.List;

import model.entities.Reviews;

public interface ReviewDAO{
    List<Reviews> findByReviews(String Reviews);
    Reviews findById(Integer id);
    List<Reviews> findAll();
    void create(Reviews Reviews);
    void update(Reviews Reviews);
    void delete(Integer id);
}