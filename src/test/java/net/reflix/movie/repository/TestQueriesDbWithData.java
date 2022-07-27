package net.reflix.movie.repository;

import net.reflix.movie.model.dto.DirectorStats;
import net.reflix.movie.model.entity.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.Arrays;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("testdata")
public class TestQueriesDbWithData {

    @Autowired
    IMovieRepository movieRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    public void testMovieWithActors() {
        int id = 74512;
        var optMovie = movieRepository.findById(id);
        optMovie.ifPresent(m ->
                        System.out.println(m.getActors()));
    }

    @Test
    public void testMovieWithActorByName() {
        // NB:
        //  - with fetch only Bruce Willis in actors
        //  - without fetch : get all actors with N+1 queries
        String query = "select m from Movie m join m.actors a "
                + "where a.name = :name";
        var movies = entityManager
                .createQuery(query, Movie.class)
                .setParameter("name", "Bruce Willis")
                .getResultList();
        for (var movie: movies){
            System.out.println(" - " + movie.getTitle());
            for (var actor: movie.getActors()) {
                System.out.println("\t * " + actor.getName());
            }
        }

    }

    @Test
    public void testStatsByDirectorToObjectArray() {
        String query = "select d.name, COUNT(m) as countMovie "
                + " from Movie m join m.director d "
                + " group by d "
                + " having COUNT(m) > :mincount"
                + " order by countMovie";
        var stats = entityManager
                .createQuery(query, Object[].class)
                .setParameter("mincount", 10L)
                .getResultList();
        stats.forEach(row -> System.out.println(Arrays.toString(row)));
    }

    @Test
    public void testStatsByDirectorToDTO() {
        String query = "select new net.reflix.movie.model.dto.DirectorStats(d.name, COUNT(m) as countMovie) "
                + " from Movie m join m.director d "
                + " group by d "
                + " having COUNT(m) > :mincount"
                + " order by countMovie desc";
        var stats = entityManager
                .createQuery(query, DirectorStats.class)
                .setParameter("mincount", 10L)
                .getResultList();
        stats.forEach(stat -> System.out.println(stat));
    }

    @Test
    public void testStatsByDirectorToDTOFromRepository() {
        long minCount = 20;
        movieRepository
                .getDirectorStats(minCount)
                .forEach(stat -> System.out.println(stat));
    }

    @Test
    public void testStatsByDirectorToDTOFromRepository2() {
        long minCount = 20;
        movieRepository
                .getDirectorStats2(minCount)
                .forEach(stat ->
                        System.out.println(
                                stat.getName() + ": "
                            + stat.getCountMovie()
                            + " ; " +stat.getDurationTotal()
                            + " mn"));
    }

}
