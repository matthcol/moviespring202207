package net.reflix.movie.controller;

import net.reflix.movie.model.dto.PeopleDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/people")
public class PeopleController {

    @GetMapping("{id}")
    public PeopleDto getById(@PathVariable("id") int id) {
        return PeopleDto.builder()
                .id(id)
                .name("Tom Cruise")
                .birthdate(LocalDate.of(1962,7,3))
                .build();
    }

    @PostMapping
    public PeopleDto add(@RequestBody PeopleDto people) {
        people.setId(123);
        return people;
    }
}
