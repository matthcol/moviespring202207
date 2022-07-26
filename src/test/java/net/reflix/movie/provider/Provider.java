package net.reflix.movie.provider;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.stream.Stream;

public class Provider {
    public static Stream<String> tooLongTitle() {
        return Stream.of(RandomStringUtils.random(251));
    }
}
