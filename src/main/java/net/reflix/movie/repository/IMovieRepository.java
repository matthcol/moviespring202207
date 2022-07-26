package net.reflix.movie.repository;

import net.reflix.movie.model.dto.DirectorStats;
import net.reflix.movie.model.dto.IDirectorStats;
import net.reflix.movie.model.entity.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
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

    Stream<Movie> findByDirectorName(String name);

    @EntityGraph("Movie.directorAndActors") // graph is defined in entity Movie
    Stream<Movie> findByYear(int year);

    @EntityGraph(attributePaths = {"director", "actors" }) // graph is defined ad hoc here
    Stream<Movie> findByYearBetween(int year1, int year2);

    // JPQK Queries
    @Query("select m from Movie m left join fetch m.director where m.year = :year")
    Stream<Movie> findByYearJpql(int year);

    @Query("from Movie m where m.title = :title and m.year between :year1 and :year2")
    Stream<Movie> findByPEMovies(String title, int year1, int year2);

    @Query("select new net.reflix.movie.model.dto.DirectorStats(d.name, COUNT(m) as countMovie) "
        + " from Movie m join m.director d "
        + " group by d "
        + " having COUNT(m) > :minCount"
        + " order by countMovie desc")
    Stream<DirectorStats> getDirectorStats(Long minCount);

    @Query("select d.name as name, "
            + "    COUNT(m) as countMovie, "
            + "    COALESCE(SUM(m.duration), 0) as durationTotal "
            + " from Movie m join m.director d "
            + " group by d "
            + " having COUNT(m) > :minCount"
            + " order by countMovie desc")
    Stream<IDirectorStats> getDirectorStats2(Long minCount);

}
