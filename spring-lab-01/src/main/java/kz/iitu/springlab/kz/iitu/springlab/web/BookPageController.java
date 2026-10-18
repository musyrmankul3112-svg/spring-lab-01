package kz.iitu.springlab.kz.iitu.springlab.web;

import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("/books")
public class BookPageController {

    private final BookService service;
    private final MessageSource messageSource;

    public BookPageController(
            BookService service,
            MessageSource messageSource
    ) {
        this.service = service;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String list(
            @RequestParam(required = false) String author,
            Model model
    ) {
        model.addAttribute("books", service.findAll(author));
        model.addAttribute("author", author);
        model.addAttribute("title", "Catalogue");

        return "books/list";
    }

    @GetMapping("/{id}")
    public String findById(
            @PathVariable long id,
            Model model
    ) {
        model.addAttribute("book", service.findById(id));
        return "books/detail";
    }

    @GetMapping("/new")
    public String newForm(
            Model model,
            Locale locale
    ) {
        String englishMessage = messageSource.getMessage(
                "book.new",
                null,
                Locale.ENGLISH
        );

        model.addAttribute("debugEnglish", englishMessage);
        model.addAttribute("form", new BookForm());
        model.addAttribute("genres", Genre.values());

        return "books/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("form") BookForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirect
    ) {
        if (binding.hasErrors()) {
            model.addAttribute("genres", Genre.values());
            return "books/form";
        }


        Book saved = service.create(
                new Book(
                        null,
                        form.getTitle(),
                        form.getAuthor(),
                        form.getYear(),
                        form.getGenre(),
                        java.time.Instant.now()
                )
        );


        redirect.addFlashAttribute(
                "message",
                "Book \"" + saved.title() + "\" has been added"
        );

        return "redirect:/books";
    }
}

