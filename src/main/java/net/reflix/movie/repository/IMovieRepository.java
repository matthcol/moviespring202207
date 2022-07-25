package net.reflix.movie.repository;

import net.reflix.movie.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovieRepository extends JpaRepository<Movie, Integer>
{
}
