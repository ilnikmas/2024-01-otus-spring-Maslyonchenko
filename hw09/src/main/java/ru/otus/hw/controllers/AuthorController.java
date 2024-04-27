package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.models.Author;
import ru.otus.hw.services.AuthorService;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authorsList")
    public String showAuthorsList(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "authors-list";
    }

    @GetMapping("/authorAdd")
    public String showAddAuthorForm(Author author, Model model) {
        model.addAttribute("author", author);
        return "author-add";
    }

    @PostMapping("/authorAdd")
    public String addAuthor(Author author) {
        authorService.insert(author.getFullName());
        return "redirect:/authorsList";
    }
}
