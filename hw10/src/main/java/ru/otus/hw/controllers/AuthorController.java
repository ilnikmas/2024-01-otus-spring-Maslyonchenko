package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    @GetMapping("/authorsList")
    public String showAuthorsList(Model model) {
        model.addAttribute("keywords", "authors, authors list");
        return "authors-list";
    }

    @GetMapping("/authorAdd")
    public String showAddAuthorForm() {
        return "author-add";
    }
}
