package kz.iitu.springlab.catalog;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import kz.iitu.springlab.kz.iitu.springlab.web.dto.BookMapper;
import kz.iitu.springlab.kz.iitu.springlab.web.dto.BookRequest;
import kz.iitu.springlab.kz.iitu.springlab.web.dto.BookResponse;

@Tag(name = "Books", description = "The catalogue of books")
@RestController
@RequestMapping("/api/books")
public class BookApiController {

    private final BookService service;
    private final BookMapper mapper;

    public BookApiController(BookService service, BookMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Get all books, optionally filtered by author")
    @GetMapping
    public List<BookResponse> getAllBooks(
            @RequestParam(required = false) String author) {
        return mapper.toResponseList(service.findAll(author));
    }

    @Operation(summary = "Find a book by its identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The book was found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public BookResponse getBookById(
            @Parameter(description = "Identifier of the book", example = "42")
            @PathVariable long id) {
        return mapper.toResponse(service.findById(id));
    }

    @Operation(summary = "Search books by title or author")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search completed"),
            @ApiResponse(responseCode = "400", description = "Search query must not be empty")
    })
    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(
            @RequestParam(required = false) String q) {

        if (q == null || q.isBlank()) {
            ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                    HttpStatus.BAD_REQUEST,
                    "Search query must not be empty"
            );
            problem.setTitle("Invalid search query");
            return ResponseEntity.badRequest().body(problem);
        }

        return ResponseEntity.ok(
                mapper.toResponseList(service.search(q))
        );
    }

    @Operation(summary = "Create a book")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Book created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "Duplicate book")
    })
    @PostMapping
    public ResponseEntity<BookResponse> createBook(
            @Valid @RequestBody BookRequest request) {

        Book saved = service.create(mapper.toDomain(request));

        return ResponseEntity
                .created(URI.create("/api/books/" + saved.id()))
                .body(mapper.toResponse(saved));
    }

    @Operation(summary = "Update a book")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book updated"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/{id}")
    public BookResponse updateBook(
            @PathVariable long id,
            @Valid @RequestBody BookRequest request) {

        Book updated = service.update(id, mapper.toDomain(request));
        return mapper.toResponse(updated);
    }

    @Operation(summary = "Delete a book")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Book deleted"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
