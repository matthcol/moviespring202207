package net.reflix.movie.repository;

import net.reflix.movie.model.entity.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

@DataJpaTest
public class TestMovieRepositoryCriteria {

    @Autowired
    EntityManager entityManager;

    @Test
    void  testMovieByTitle(){
        var title = "The Man Who Knew Too Much";
        var cb = entityManager.getCriteriaBuilder();
        var criteriaQuery = cb.createQuery(Movie.class);
        var root = criteriaQuery.from(Movie.class);
        criteriaQuery.select(root)
                .where(cb.equal(root.get("title"), title));
        entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .forEach(m -> System.out.println("\t- " + m));
    }

    @Test
    void  testMovieByYearDuration(){
        int yearMin = 1970;
        int yearMax = 1979;
        int durationMin = 120;
        var cb = entityManager.getCriteriaBuilder();
        var criteriaQuery = cb.createQuery(Movie.class);
        var root = criteriaQuery.from(Movie.class);
        criteriaQuery.select(root)
                .where(cb.and(
                        cb.between(root.get("year"), yearMin, yearMax),
                        cb.ge(root.get("duration"), durationMin)));
        entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .forEach(m -> System.out.println("\t- " + m));
    }
}
