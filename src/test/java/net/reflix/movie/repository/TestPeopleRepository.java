package net.reflix.movie.repository;

import net.reflix.movie.model.entity.People;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Date;

@DataJpaTest
public class TestPeopleRepository {

    @Autowired
    private IPeopleRepository peopleRepository;

    @Test
    public void testSave() {
        var people = People.builder()
                .name("Steve McQueen")
                .birthdate(LocalDate.of(1930,3,24))
                //.birthdate(new Date())
                .build();
        peopleRepository.save(people);
        assertNotNull(people.getId());
    }


}
