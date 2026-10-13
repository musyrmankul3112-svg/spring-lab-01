package kz.iitu.springlab.kz.iitu.springlab.web;

import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> list(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "10") int limit) {

        return service.findAll(author)
                .stream()
                .limit(limit)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> find(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book saved = service.create(book);

        return ResponseEntity
                .created(URI.create("/api/books/" + saved.id()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> replace(
            @PathVariable long id,
            @RequestBody Book book) {

        service.findById(id);

        Book updated = service.create(
                new Book(id, book.title(), book.author(), book.year())
        );

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        if (!service.delete(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<?> stats() {
        List<Book> books = service.findAll(null);

        long count = books.size();

        int earliest = books.stream()
                .mapToInt(Book::year)
                .min()
                .orElse(0);

        int latest = books.stream()
                .mapToInt(Book::year)
                .max()
                .orElse(0);

        return ResponseEntity.ok(
                Map.of(
                        "count", count,
                        "earliestYear", earliest,
                        "latestYear", latest
                )
        );
    }
}