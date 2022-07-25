package net.reflix.movie.controller;

import net.reflix.movie.model.dto.MovieDto;
import net.reflix.movie.repository.IMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/movie")
public class MovieController {

    @Autowired
    private IMovieRepository movieRepository;

    @GetMapping
    public List<MovieDto> getAll() {
        return List.of(
                MovieDto.of("Thor: Love and Thunder", 2022),
                MovieDto.of("Top Gun: Maverick", 2022),
                MovieDto.of("Taxi Driver", 2022));
    }

    /**
     * url : api/movie/{id}
     * example: api/movie/3
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public MovieDto getById(@PathVariable("id") int id) {
        return MovieDto.builder()
                .id(id)
                .title("Thor: Love and Thunder")
                .year(2022)
                .build();
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
        movie.setId(12);
        return movie;
    }

    @PutMapping("{id}")
    public MovieDto updateMovie(
            @PathVariable("id") int id,
            @RequestBody MovieDto movie)
    {
        return movie;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable("id") int id) {

    }















}