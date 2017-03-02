package com.booneneligan.courses.dao;

import com.booneneligan.courses.exc.DaoException;
import com.booneneligan.courses.model.Review;

import java.util.List;

public interface ReviewDao {
    void add(Review review) throws DaoException;

    List<Review> findAll();

    List<Review> findByCourseId(int courseId);
}
