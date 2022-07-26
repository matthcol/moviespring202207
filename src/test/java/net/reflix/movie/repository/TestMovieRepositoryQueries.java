package net.reflix.movie.repository;

import net.reflix.movie.model.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort.Order;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TestMovieRepositoryQueries {

    @Autowired
    private TestEntityManager entityManager;
    // private EntityManager entityManager;

    @Autowired
    private IMovieRepository movieRepository;

    private List<Movie> moviesFact;

    @BeforeEach
    public void initData() {
        moviesFact = List.of(
                Movie.of("Top Gun: Maverick", 2022),
                Movie.of("The Man Who Knew Too Much", 1956),
                Movie.builder()
                        .title("The Man Who Knew Too Much")
                        .year(1934)
                        .duration(75)
                        .build()
        );
        moviesFact.forEach(entityManager::persist);
        entityManager.flush();
    }

    @Test
    public void testFindByIdPresent() {
        // facts
        var movie = Movie.of("Taxi Driver", 1976);
        entityManager.persistAndFlush(movie);
        var idMovie = movie.getId();
        entityManager.clear();
        // when
        var optMovieFound = movieRepository.findById(idMovie);
        assertTrue(optMovieFound.isPresent());
        optMovieFound.ifPresent(m -> assertAll(
                () -> assertEquals(idMovie, m.getId()),
                () -> assertEquals("Taxi Driver", m.getTitle()),
                () -> assertEquals(1976, m.getYear())
        ));
    }

    @Test
    public void testFindByIdNotPresent() {
        // facts: None
        // when
        var optMovieFound = movieRepository.findById(Integer.MAX_VALUE);
        assertTrue(optMovieFound.isEmpty());
    }

    @Test
    public void testFindAll() {
        var moviesFound = movieRepository.findAll();
        assertEquals(moviesFact.size(), moviesFound.size());
    }

    @Test
    public void testFindAllSort() {
        var moviesFound = movieRepository.findAll(
                Sort.by("title", "year")
        );
        System.out.println(moviesFound);
    }

    @Test
    public void testFindAllSortDesc() {
        var moviesFound = movieRepository.findAll(
                Sort.by(Order.asc("title"),
                        Order.desc("year"))
        );
        System.out.println(moviesFound);
    }

    @Test
    public void testFindAllPaging() {
        var moviePage = movieRepository.findAll(
                Pageable.ofSize(50));
        assertEquals(1, moviePage.getTotalPages());
        assertEquals(moviesFact.size(), moviePage.getTotalElements());
        assertFalse(moviePage.hasNext());
    }

    // TODO: loozk doc api by example
    @Test
    public void testFindAllExample() {
        var movie = Movie.of("The Man Who Knew Too Much", 1934);
        var movieExample = Example.of(movie);
        var moviesFound = movieRepository.findAll(movieExample);
        System.out.println(moviesFound);
    }

    @Test
    public void testFindByTitle() {
        String title = "The Man Who Knew Too Much";
        var moviesFound = movieRepository.findByTitle(title);
        assertAll(moviesFound
                .map(Movie::getTitle)
                .map(t -> () -> assertEquals(title, t)));
    }

    @Test
    public void testFindByTitlePartialIgnoringCase() {
        String title = "man Who knew";
        var moviesFound = movieRepository.findByTitleContainsIgnoringCase(title);
        assertAll(moviesFound
                .map(Movie::getTitle)
                .map(t -> () -> assertTrue(
                        t.toLowerCase().contains(title.toLowerCase()))));
    }

    @Test
    public void testFindByTitlePartialIgnoringCaseAndYearGreater() {
        String title = "man Who knew";
        int year = 1950;
        var moviesFound = movieRepository.findByTitleContainsIgnoringCaseAndYear(title, year);
        System.out.println(moviesFound.collect(Collectors.toList()));
    }

    @Test
    public void testFindPEMovies() {
        var moviesFound = movieRepository.findByPEMovies(
                "The Man Who Knew Too Much",
                1940,
                1960
        ).collect(Collectors.toList());
        System.out.println(moviesFound);
    }
}