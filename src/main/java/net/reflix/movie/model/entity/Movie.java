package net.reflix.movie.model.entity;

import lombok.*;
import net.reflix.movie.model.enums.ColorEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(
        name = "movies",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title", "year"})
)
@NamedEntityGraph(name = "Movie.directorAndActors",
        attributeNodes = {
                @NamedAttributeNode("director"),
                @NamedAttributeNode("actors")}
)
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(staticName = "of")
@Builder
@ToString(exclude = {"director", "actors"})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(nullable = false, length = 250)
    private String title;

    @NonNull
    //@Column(name = "year", nullable = false)
    @Column(name = "\"year\"", nullable = false)
   // @Column(name = "myear", nullable = false)
    private Integer year;

    @Column(nullable = true)
    private Integer duration;

    @Column(nullable = true, length = 500)
    private String synopsis;

    @Transient // do not persist
    // @Column(nullable = true)
    // @Enumerated(EnumType.STRING)
    private ColorEnum color;

    @ManyToOne(fetch = FetchType.LAZY) // default EAGER
    @JoinColumn(name="id_director", nullable = true)
    private People director;

    @ManyToMany
    @JoinTable(name = "play",
            joinColumns = @JoinColumn(name = "id_movie"),
            inverseJoinColumns = @JoinColumn(name ="id_actor")
    )
    @Builder.Default // for lombock to respect this initialization by defaut
    private Set<People> actors = new HashSet<>();

    // NB: Set allow fine DML synchro
    //      List provoke delete + n insert each time list is modified
}
