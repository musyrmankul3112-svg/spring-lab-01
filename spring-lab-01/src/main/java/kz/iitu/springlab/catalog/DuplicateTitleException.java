package kz.iitu.springlab.catalog;

public class DuplicateTitleException extends RuntimeException {

    private final String title;

    public DuplicateTitleException(String title) {
        super("Book already exists with title: " + title);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
