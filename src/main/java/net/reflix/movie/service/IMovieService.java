package net.reflix.movie.service;

import net.reflix.movie.model.dto.MovieDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IMovieService {

    Optional<MovieDto> getById(int id);
    List<MovieDto> getAll();
    MovieDto save(MovieDto movieDto);
    MovieDto update(MovieDto movieDto);
    boolean setDirector(int idMovie, int idPeople);
    boolean addActor(int idMovie, int idPeople);
    boolean setActors(int idMovie, Collection<Integer> idsPeople);
    boolean delete(int id);

}
