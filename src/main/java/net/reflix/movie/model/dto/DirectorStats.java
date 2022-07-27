package net.reflix.movie.model.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DirectorStats {
    private String name;
    private Long countMovie;
}
