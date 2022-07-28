package net.reflix.movie.service;

import net.reflix.movie.model.dto.MovieDetailDto;
import net.reflix.movie.model.dto.MovieDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IMovieService {

    Optional<MovieDetailDto> getById(int id);
    List<MovieDto> getAll();
    MovieDto save(MovieDto movieDto);
    MovieDto update(MovieDto movieDto);
    Optional<MovieDetailDto> setDirector(int idMovie, int idPeople);
    Optional<MovieDetailDto> addActor(int idMovie, int idPeople);
    Optional<MovieDetailDto> setActors(int idMovie, Collection<Integer> idsPeople);
    boolean delete(int id);

}
