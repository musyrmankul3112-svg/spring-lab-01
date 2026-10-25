package kz.iitu.springlab.catalog;

import kz.iitu.springlab.kz.iitu.springlab.web.Genre;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Profile("dev")
@Order(1)
public class DevDataSeeder implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final BookRepository bookRepository;

    public DevDataSeeder(
            AuthorRepository authorRepository,
            TagRepository tagRepository,
            BookRepository bookRepository
    ) {
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (bookRepository.count() > 0) {
            return;
        }

        Tag classic = tagRepository.save(new Tag("classic"));
        Tag adventure = tagRepository.save(new Tag("adventure"));
        Tag mystery = tagRepository.save(new Tag("mystery"));
        Tag romance = tagRepository.save(new Tag("romance"));

        Author tolkien = new Author("J.R.R. Tolkien", "United Kingdom");
        Author christie = new Author("Agatha Christie", "United Kingdom");
        Author austen = new Author("Jane Austen", "United Kingdom");

        book(tolkien, "The Hobbit", Genre.FANTASY, 1937, classic, adventure);
        book(tolkien, "The Fellowship of the Ring", Genre.FANTASY, 1954, classic, adventure);
        book(tolkien, "The Two Towers", Genre.FANTASY, 1954, adventure);

        book(christie, "Murder on the Orient Express", Genre.DETECTIVE, 1934, classic, mystery);
        book(christie, "And Then There Were None", Genre.DETECTIVE, 1939, mystery);
        book(christie, "The Mysterious Affair at Styles", Genre.DETECTIVE, 1920, mystery);

        book(austen, "Pride and Prejudice", Genre.ROMANCE, 1813, classic, romance);
        book(austen, "Sense and Sensibility", Genre.ROMANCE, 1811, romance);

        authorRepository.saveAll(List.of(tolkien, christie, austen));
        System.out.println("Seeded 3 authors, 8 books and 4 tags.");
    }

    private static void book(
            Author author,
            String title,
            Genre genre,
            int year,
            Tag... tags
    ) {
        Book book = new Book(title, genre, year);
        author.addBook(book);

        for (Tag tag : tags) {
            book.addTag(tag);
        }
    }
}