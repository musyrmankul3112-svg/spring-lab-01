package kz.iitu.springlab.kz.iitu.springlab.web.dto;
import kz.iitu.springlab.kz.iitu.springlab.web.Genre;
import java.time.Instant;

public record BookResponse(
        Long id,
        String title,
        String author,
        int year,
        Genre genre,
        Instant createdAt
) {
}
