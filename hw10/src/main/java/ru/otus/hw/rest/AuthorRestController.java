package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.models.Author;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {

    private final AuthorService authorService;

    @GetMapping("/api/authors")
    public List<Author> getAllAuthors() {
        return authorService.findAll();
    }

    @PostMapping("/api/authors")
    public Author addAuthor(@RequestBody Author author) {
        return authorService.insert(author);
    }
}
