package net.reflix.movie.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieDetailDto extends MovieDto {
    private PeopleDto director;
    private List<PeopleDto> actors;
}
