package net.reflix.movie.controller;

import net.reflix.movie.model.dto.MovieDto;
import net.reflix.movie.service.IMovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@WebMvcTest
public class TestMovieController {

    public static String BASE_URL = "/api/movie";

    // simulate http client
    @Autowired
    MockMvc mockMvc;

    // component to mock used by controller
    @MockBean
    IMovieService movieService;


    @Test
    void testGetByIdPresent() throws Exception {
        // facts
        int id = 1;

        // prepare mock
        var movieMock = MovieDto.of(id, "Top Gun: Maverick", 2022, null, null);
        given(movieService.getById(eq(id)))
                .willReturn(Optional.of(movieMock));

        // when send request + check response
        mockMvc
                .perform(get(BASE_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) // intercept response to print
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(movieMock.getTitle()))
                ;

        // check mock: service has been called
        then(movieService)
                .should()
                .getById(eq(id));

    }
}
