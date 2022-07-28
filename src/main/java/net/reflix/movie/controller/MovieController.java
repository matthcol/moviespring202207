package net.reflix.movie.controller;

import net.reflix.movie.handler.dto.ApiError;
import net.reflix.movie.handler.dto.ValidationError;
import net.reflix.movie.model.dto.MovieDetailDto;
import net.reflix.movie.model.dto.MovieDto;
import net.reflix.movie.repository.IMovieRepository;
import net.reflix.movie.service.IMovieService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("api/movie")
public class MovieController {

    // logger sl4j
    private Logger logger = LoggerFactory.getLogger(MovieController.class);

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
    public MovieDetailDto getById(@PathVariable("id") int id) {
        // NB: other possibility, empty Optional => 404 Not found
        logger.info("GET ****************************");
       return movieService.getById(id)
               .orElseThrow();
       // return Optional.of(MovieDto.builder()
       //         .id(1).title("Top Gun: Maverick").year(2022).build());
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
    public MovieDto addMovie(@Valid @RequestBody MovieDto movie) {
        logger.debug("about to save movie");
        var res =  movieService.save(movie);
        logger.info("movie added");
        return res;
    }

    @PutMapping("{id}")
    public MovieDto updateMovie(
            @PathVariable("id") int id,
            @Valid @RequestBody MovieDto movie)
    {
        // TODO: error if id <> movie.id
        return movieService.update(movie);
    }

    @PatchMapping("director/{idMovie}")
    public Optional<MovieDetailDto> setDirector(
            @PathVariable("idMovie") int idMovie,
            @RequestParam("did") int idDirector)
    {
        return movieService.setDirector(idMovie, idDirector);
    }

    @PatchMapping("actors/add/{idMovie}")
    public Optional<MovieDetailDto> addActor(
            @PathVariable("idMovie") int idMovie,
            @RequestParam("aid") int idActor)
    {
        return movieService.addActor(idMovie, idActor);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteMovie(@PathVariable("id") int id) {
        // TODO: 404 if id not found
        if (movieService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiError.of(
                            HttpStatus.NOT_FOUND,
                            "No Data Found to delete",
                            List.of()));
        }
    }















}