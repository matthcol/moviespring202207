package net.reflix.movie.model.entity;

import lombok.*;
import net.reflix.movie.model.enums.ColorEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(
        name = "movies",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title", "myear"})
)
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(staticName = "of")
@Builder
@ToString(exclude = {"director", "actor"})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(nullable = false, length = 250)
    private String title;

    @NonNull
    // @Column(name = "\"year\"")
    @Column(name = "myear", nullable = false)
    private Integer year;

    @Column(nullable = true)
    private Integer duration;

    @Column(nullable = true, length = 500)
    private String synopsys;

    // @Transient // do not persist
    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private ColorEnum color;

    @Transient
    private People director;

    @Transient
    private List<People> actors;
}
