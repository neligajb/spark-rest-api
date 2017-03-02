package com.booneneligan.courses.dao;

import com.booneneligan.courses.exc.DaoException;
import com.booneneligan.courses.model.Course;

import java.util.List;

public interface CourseDao {
    void add(Course course) throws DaoException;

    List<Course> findAll();

    Course findById(int id);
}
