package net.reflix.movie.controller;

import net.reflix.movie.model.dto.MovieDto;
import net.reflix.movie.repository.IMovieRepository;
import net.reflix.movie.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/movie")
public class MovieController {

    @Autowired
    private IMovieService movieService;

    @GetMapping
    public List<MovieDto> getAll() {
        return movieService.getAll();
    }

    /**
     * url : api/movie/{id}
     * example: api/movie/3
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Optional<MovieDto> getById(@PathVariable("id") int id) {
        // NB: other possibility, empty Optional => 404 Not found
       //  return movieService.getById(id);
        return Optional.of(MovieDto.builder()
                .id(1).title("Top Gun: Maverick").year(2022).build());
    }

    /**
     * url: api/movie/byyear?y=2020
     * url: api/movie/byyear?y=2010&y2=2019
     */
    @GetMapping("byyear")
    public List<MovieDto> getByYear(
            @RequestParam("y") Integer year1,
            @RequestParam(value = "y2",required = false) Integer year2)
    {
        return List.of(
                MovieDto.of("Thor: Love and Thunder", year1),
                MovieDto.of("Top Gun: Maverick", year1),
                MovieDto.of("Taxi Driver", year2));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto addMovie(@RequestBody MovieDto movie) {
        return movieService.save(movie);
    }

    @PutMapping("{id}")
    public MovieDto updateMovie(
            @PathVariable("id") int id,
            @RequestBody MovieDto movie)
    {
        // TODO: error if id <> movie.id
        return movieService.update(movie);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable("id") int id) {
        // TODO: 404 if id not found
        var ok = movieService.delete(id);
    }















}