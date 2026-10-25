package kz.iitu.springlab.catalog;

import kz.iitu.springlab.kz.iitu.springlab.web.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll(String author) {
        if (author == null || author.isBlank()) {
            return repository.findAll();
        }

        return repository.findAll().stream()
                .filter(book -> book.author().toLowerCase()
                        .contains(author.toLowerCase()))
                .toList();
    }

    public Page<Book> find(Genre genre, Pageable pageable) {
        if (genre == null) {
            return repository.findAll(pageable);
        }

        return repository.findByGenre(genre, pageable);
    }

    public Slice<Book> findSlice(Genre genre, Pageable pageable) {
        if (genre == null) {
            return repository.findAllSlice(pageable);
        }

        return repository.findSliceByGenre(genre, pageable);
    }

    public Book findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book create(Book book) {
        boolean duplicate = repository.findAll().stream()
                .anyMatch(existing ->
                        existing.title().equalsIgnoreCase(book.title())
                                && existing.author().equalsIgnoreCase(book.author()));

        if (duplicate) {
            throw new DuplicateTitleException(book.title());
        }

        return repository.save(book);
    }

    public Book update(long id, Book book) {
        Book existing = findById(id);

        Book updated = new Book(
                id,
                book.title(),
                book.author(),
                book.year(),
                book.genre(),
                existing.createdAt()
        );

        return repository.save(updated);
    }

    public boolean delete(long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    public List<Book> search(String q) {
        if (q == null || q.isBlank()) {
            throw new IllegalArgumentException("Search query must not be empty");
        }

        String query = q.trim().toLowerCase();

        return repository.findAll().stream()
                .filter(book ->
                        book.title().toLowerCase().contains(query)
                                || book.author().toLowerCase().contains(query))
                .toList();
    }
}