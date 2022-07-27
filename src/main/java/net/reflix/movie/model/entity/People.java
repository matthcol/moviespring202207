package net.reflix.movie.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "stars")  // people
@NamedQuery(
        name = "People.findByYear",
        query = "from People p where YEAR(p.birthdate) = :year"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
@Builder
@ToString
public class People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull // lombock
    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
   // @Temporal(TemporalType.DATE) if type Date or Calendar
    private LocalDate birthdate;
}
