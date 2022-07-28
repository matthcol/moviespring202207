package net.reflix.movie.config;

import net.reflix.movie.model.dto.MovieDto;
import net.reflix.movie.model.entity.Movie;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper =  new ModelMapper();
        modelMapper.createTypeMap(MovieDto.class, Movie.class)
                .addMapping(md -> md.getYearRelease(), Movie::setYear);
        modelMapper.createTypeMap(Movie.class, MovieDto.class)
                .addMapping(me -> me.getYear(), MovieDto::setYearRelease);
        return modelMapper;
    }
}
