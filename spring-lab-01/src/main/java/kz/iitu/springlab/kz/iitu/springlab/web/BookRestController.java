
package kz.iitu.springlab.kz.iitu.springlab.web;

import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookService;
import kz.iitu.springlab.kz.iitu.springlab.web.dto.BookMapper;
import kz.iitu.springlab.kz.iitu.springlab.web.dto.BookRequest;
import kz.iitu.springlab.kz.iitu.springlab.web.dto.BookResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

//@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService service;
    private final BookMapper mapper;

    public BookRestController(BookService service, BookMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<BookResponse> list(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "10") int limit) {

        return mapper.toResponseList(
                service.findAll(author).stream()
                        .limit(limit)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> find(@PathVariable long id) {
        Book book = service.findById(id);
        return ResponseEntity.ok(mapper.toResponse(book));
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(
            @Valid @RequestBody BookRequest request) {

        Book book = mapper.toDomain(request);
        Book saved = service.create(book);

        return ResponseEntity
                .created(URI.create("/api/books/" + saved.id()))
                .body(mapper.toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> replace(
            @PathVariable long id,
            @Valid @RequestBody BookRequest request) {

        Book book = mapper.toDomain(request);
        Book updated = service.update(id, book);

        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.findById(id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
//