package kz.iitu.springlab.catalog;

import jakarta.persistence.*;
import kz.iitu.springlab.kz.iitu.springlab.web.Genre;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
@NamedEntityGraph(
        name = "Book.withAuthorAndTags",
        attributeNodes = {
                @NamedAttributeNode("author"),
                @NamedAttributeNode("tags")
        }
)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinColumn(name = "author_id")
    private Author author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Genre genre;

    private int year;
    private boolean archived;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Version
    private long version;

    @ManyToMany
    @JoinTable(
            name = "book_tag",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    protected Book() {
    }

    public Book(Long id, String title, String authorName, int year,
                Genre genre, Instant createdAt) {
        this.id = id;
        this.title = title;
        this.author = authorName == null ? null : new Author(authorName, null);
        this.year = year;
        this.genre = genre;
        this.createdAt = createdAt != null ? createdAt : Instant.now();
    }

    public Book(String title, Genre genre, int year) {
        this(null, title, null, year, genre, Instant.now());
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getBooks().add(this);
    }

    public Long id() { return id; }
    public String title() { return title; }
    public String author() { return author == null ? null : author.getName(); }
    public int year() { return year; }
    public Genre genre() { return genre; }
    public Instant createdAt() { return createdAt; }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public Author getAuthor() { return author; }
    public Genre getGenre() { return genre; }
    public int getYear() { return year; }
    public boolean isArchived() { return archived; }
    public Instant getCreatedAt() { return createdAt; }
    public long getVersion() { return version; }
    public Set<Tag> getTags() { return tags; }

    public void setAuthor(Author author) { this.author = author; }
    public void setTitle(String title) { this.title = title; }
    public void setGenre(Genre genre) { this.genre = genre; }
    public void setYear(int year) { this.year = year; }
    public void setArchived(boolean archived) { this.archived = archived; }
}