package kz.iitu.springlab.catalog;

import kz.iitu.springlab.kz.iitu.springlab.web.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Profile("dev")
@Order(2)
public class QueryDemoRunner implements CommandLineRunner {

    private final BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public QueryDemoRunner(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("findByGenre rows: "
                + bookRepository.findByGenre(Genre.FANTASY).size());

        System.out.println("findByTitleContainingIgnoreCase rows: "
                + bookRepository.findByTitleContainingIgnoreCase("the").size());

        System.out.println("findByYearBetweenOrderByYearDesc rows: "
                + bookRepository.findByYearBetweenOrderByYearDesc(1900, 1955).size());

        System.out.println("findTop3ByGenreOrderByYearDesc rows: "
                + bookRepository.findTop3ByGenreOrderByYearDesc(Genre.FANTASY).size());

        System.out.println("countByAuthorName rows: "
                + bookRepository.countByAuthorName("Agatha Christie"));

        System.out.println("search JPQL rows: "
                + bookRepository.search(Genre.DETECTIVE, 1930).size());

        entityManager.clear();
        List<Book> before = bookRepository.findAllWithoutFetch();
        System.out.println("N+1 before fetch join:");
        for (Book book : before) {
            System.out.println(book.getAuthor().getName());
        }

        entityManager.clear();
        List<Book> after = bookRepository.findAllWithAuthor();
        System.out.println("After fetch join:");
        for (Book book : after) {
            System.out.println(book.getAuthor().getName());
        }

        System.out.println("archiveOlderThan updated rows: "
                + bookRepository.archiveOlderThan(1930));
    }
}