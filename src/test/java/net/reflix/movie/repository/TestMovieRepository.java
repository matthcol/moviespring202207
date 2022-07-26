package net.reflix.movie.repository;

import net.reflix.movie.model.entity.Movie;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TestMovieRepository {

    @Autowired
    private IMovieRepository movieRepository;

    // @Rollback(false)
    @Test
    public void testSaveMovie() {
        String title = "Top Gun: Maverick";
        int year = 2022;
        var movie = Movie.of(title, year);
        movieRepository.save(movie);
        assertNotNull(movie.getId());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Z"
            , "Top Gun: Maverick"
            , "Night of the Day of the Dawn of the Son of the Bride of the Return of the Revenge of the Terror of the Attack of the Evil Mutant Hellbound Flesh Eating Crawling Alien Zombified Subhumanoid Living Dead, Part 5"
    })
    public void testSaveMovieTitle(String title) {
        int year = 2022;
        var movie = Movie.of(title, year);
        movieRepository.save(movie);
        assertNotNull(movie.getId());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings={""})
    @MethodSource("net.reflix.movie.provider.Provider#tooLongTitle")
    public void testSaveMovieTitleNok(String title) {
        Exception ex = assertThrows(Exception.class, () -> {
            int year = 2022;
            var movie = Movie.of(title, year);
            movieRepository.save(movie);
        });
        assertTrue(ex instanceof NullPointerException
                || ex instanceof DataIntegrityViolationException);
    }
}
