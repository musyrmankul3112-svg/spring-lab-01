package kz.iitu.springlab.catalog;

import java.time.Instant;
import kz.iitu.springlab.kz.iitu.springlab.web.Genre;

public record Book(
        Long id,
        String title,
        String author,
        int year,
        Genre genre,
        Instant createdAt
) {
}

