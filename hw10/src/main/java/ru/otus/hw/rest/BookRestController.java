package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/api/books")
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @PostMapping("/api/books")
    public Book addNewBook(@RequestBody Book book) {
        return bookService.insert(book);
    }

    @PutMapping("/api/books")
    public Book updateBook(@RequestBody Book book) {
        return bookService.update(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getGenre().getId());
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteById(id);
    }
}
