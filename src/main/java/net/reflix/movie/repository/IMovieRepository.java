package net.reflix.movie.repository;

import net.reflix.movie.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface IMovieRepository extends JpaRepository<Movie, Integer>
{
    // NB: possible return types: Movie, Optional<Movie>, List<Movie, Set<Movie>, Stream<Movie
    //  Page<Movie>

    // Keywords to generate auto SQL query
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    Stream<Movie> findByTitle(String title);

    Stream<Movie> findByTitleContainsIgnoringCase(String title);

    Stream<Movie> findByTitleContainsIgnoringCaseAndYear(String title, int year);

    // JPQK Queries
    @Query("from Movie m where m.title = :title and m.year between :year1 and :year2")
    Stream<Movie> findByPEMovies(String title, int year1, int year2);
}
