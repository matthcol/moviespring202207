package net.reflix.movie.repository;

import net.reflix.movie.model.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPeopleRepository extends JpaRepository<People, Integer> {

}
