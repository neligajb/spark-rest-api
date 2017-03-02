package com.booneneligan.courses.dao;

import com.booneneligan.courses.exc.DaoException;
import com.booneneligan.courses.model.Course;
import com.booneneligan.courses.model.Review;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static org.junit.Assert.*;

public class Sql2oReviewDaoTest {

    private Sql2oReviewDao reviewDao;
    private Sql2oCourseDao courseDao;
    private Connection conn;
    private Course course;

    @Before
    public void setUp() throws Exception {
        String connection_string = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connection_string, "", "");
        reviewDao = new Sql2oReviewDao(sql2o);
        courseDao = new Sql2oCourseDao(sql2o);
        conn = sql2o.open();
        course = new Course("Test", "http://test.com");
        courseDao.add(course);
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingReviewSetsId() throws Exception {

        Review review = newTestReview(course.getId());
        int originalReviewId = review.getId();

        reviewDao.add(review);

        assertNotEquals(originalReviewId, review.getId());
    }

    @Test
    public void multipleReviewsAreFoundWhenTheyExistsForACourse() throws Exception {
        reviewDao.add(new Review(course.getId(), 5, "Test Comment"));
        reviewDao.add(new Review(course.getId(), 2, "Test Comment"));
        reviewDao.add(new Review(course.getId(), 1, "Test Comment"));

        List<Review> reviews = reviewDao.findByCourseId(course.getId());

        assertEquals(3, reviews.size());
    }

    @Test(expected = DaoException.class)
    public void addingAReviewToANonExistingCourseFails() throws Exception {
        Review review = new Review(42, 5, "Test comment");

        reviewDao.add(review);
    }

    private Review newTestReview(int courseId) {
        return new Review(courseId, 5, "Test Comment");
    }
}