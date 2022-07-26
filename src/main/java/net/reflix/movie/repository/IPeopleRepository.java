package net.reflix.movie.repository;

import net.reflix.movie.model.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface IPeopleRepository extends JpaRepository<People, Integer> {


    // use named query People.findByBirthYear if no annotation
    @Query(name = "People.findByYear")
    Stream<People> findByBirthyear(int year);

}
