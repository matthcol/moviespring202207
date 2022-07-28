package net.reflix.movie.model.dto;

import org.junit.jupiter.api.Test;

public class TestMovieDto {

    @Test
    public void testConstructorDefault() {
        var movie = new MovieDto();
    }

    @Test
    public void testConstructorRequiredArgs() {
        var movie = MovieDto.of("Top Gun: Maverick", 2022);
    }

    @Test
    public void testConstructorAllArgs() {
        var movie = MovieDto.of(1,"Top Gun: Maverick", 2022, 120, "Movie with planes");
    }

    @Test
    public void testConstructorBuilder() {
        var movie = MovieDto.builder()
                .id(12)
                .title("Top Gun: Maverick")
                .yearRealease(2022)
                .synopsis("Movie with planes")
                .build();
    }
}
