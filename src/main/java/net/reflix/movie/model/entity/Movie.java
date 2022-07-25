package net.reflix.movie.model.entity;

import lombok.Getter;
import lombok.Setter;
import net.reflix.movie.model.enums.ColorEnum;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Movie {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(name = "\"year\"")
    private Integer year;
    private Integer duration;
    private String synopsys;

    @Transient // do not persist
    private ColorEnum color;
}
