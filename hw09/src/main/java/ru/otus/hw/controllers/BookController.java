package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/booksList")
    public String showBooksList(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books-list";
    }

    @GetMapping("/bookEdit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("book", bookService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id))));
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "book-update";
    }

    @PostMapping("/bookUpdate")
    public String updateBook(Book book) {
        bookService.update(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getGenre().getId());
        return "redirect:/booksList";
    }

    @PostMapping("/bookDelete/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "redirect:/booksList";
    }

    @GetMapping("/bookAdd")
    public String showAddBookForm(Book book, Model model) {
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "book-add";
    }

    @PostMapping("/bookAdd")
    public String addBook(Book book) {
        bookService.insert(book.getTitle(), book.getAuthor().getId(), book.getGenre().getId());
        return "redirect:/booksList";
    }
}
