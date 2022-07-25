package net.reflix.movie.model.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(staticName = "of")
@Builder
@Getter
@Setter
public class PeopleDto {

    private Integer id;
    @NonNull
    private String name;
    private LocalDate birthdate;
}
