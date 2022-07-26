package net.reflix.movie.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "people")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
@Builder
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
