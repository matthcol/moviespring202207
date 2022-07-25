package net.reflix.movie.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
@Builder
public class MovieDto {

    private Integer id;

    @NonNull
    private String title;

    @NonNull
    private Integer year;

    private Integer duration;

    private String synopsis;
}
