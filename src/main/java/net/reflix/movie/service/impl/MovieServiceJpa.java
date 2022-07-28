package net.reflix.movie.service.impl;

import net.reflix.movie.model.dto.MovieDetailDto;
import net.reflix.movie.model.dto.MovieDto;
import net.reflix.movie.model.entity.Movie;
import net.reflix.movie.repository.IMovieRepository;
import net.reflix.movie.repository.IPeopleRepository;
import net.reflix.movie.service.IMovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// NB: @Transactional on the whole service or on individual methods

@Service
@Transactional // (noRollbackFor = ArithmeticException.class)
public class MovieServiceJpa implements IMovieService {

    @Autowired
    private IMovieRepository movieRepository;

    @Autowired
    private IPeopleRepository peopleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<MovieDetailDto> getById(int id) {
        var movieEntityOpt = movieRepository.findById(id);
        var movieDtoOpt = movieEntityOpt.map(
                me -> modelMapper.map(me, MovieDetailDto.class));
        return movieDtoOpt;
    }

    @Override
    public List<MovieDto> getAll() {
       return movieRepository.findAll(
                Sort.by(
                        Sort.Order.desc("year"),
                        Sort.Order.asc("title")))
               .stream()
               .map(me -> modelMapper.map(me, MovieDto.class))
               .collect(Collectors.toList());
    }

    @Override
    public MovieDto save(MovieDto movieDto) {
        var movieEntity = modelMapper.map(movieDto, Movie.class);
        movieRepository.save(movieEntity);
        return modelMapper.map(movieEntity, MovieDto.class);
    }

    @Override
    public MovieDto update(MovieDto movieDto) {
        return null;
    }

    @Override
    public Optional<MovieDetailDto> setDirector(int idMovie, int idPeople) {
        return movieRepository.findById(idMovie)
                .flatMap(me -> peopleRepository.findById(idPeople)
                        .map(pe -> {
                            me.setDirector(pe);
                            return modelMapper.map(me, MovieDetailDto.class);
                        })
                );
    }

    @Override
    public Optional<MovieDetailDto> addActor(int idMovie, int idPeople) {
        return movieRepository.findById(idMovie)
                .flatMap(me -> peopleRepository.findById(idPeople)
                        .map(pe -> {
                            me.getActors().add(pe);
                            return modelMapper.map(me, MovieDetailDto.class);
                        })
                );
    }

    @Override
    public Optional<MovieDetailDto> setActors(int idMovie, Collection<Integer> idsPeople) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
