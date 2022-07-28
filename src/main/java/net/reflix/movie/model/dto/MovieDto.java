package net.reflix.movie.model.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
@Builder
public class MovieDto {

    private Integer id;

    @NotNull // Java Bean Validation
    @NotBlank // no empty string
    @Size(max = 250)
    @NonNull // lombock
    private String title;

    @NotNull
    @Min(1850)
    @NonNull // lombock
    private Integer yearRelease;

    private Integer duration;

    private String synopsis;
}
