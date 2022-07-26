package net.reflix.movie.repository;

import net.reflix.movie.model.entity.People;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TestPeopleRepositoryQueries {
    @Autowired
    IPeopleRepository peopleRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void testFindByYear() {
        // facts
        var people = List.of(
                People.of(null, "Steve McQueen", LocalDate.of(1930, 3, 24)),
                People.of(null, "Steve McQueen", LocalDate.of(1969, 10, 9)),
                People.of(null, "Clint Eastwood", LocalDate.of(1930, 5, 31))
        );
        people.forEach(entityManager::persist);
        entityManager.flush();
        entityManager.clear();
        // when
        int year = 1930;
        var peopleFound = peopleRepository.findByBirthyear(year);
        assertAll(peopleFound
                .map(People::getBirthdate)
               .map(bd -> () -> assertEquals(year, bd.getYear())));
    }

}
