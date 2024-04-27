package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.GenreService;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genresList")
    public String showGenresList(Model model) {
        model.addAttribute("genres", genreService.findAll());
        return "genres-list";
    }

    @GetMapping("/genreAdd")
    public String showAddGenreForm(Genre genre, Model model) {
        model.addAttribute("genre", genre);
        return "genre-add";
    }

    @PostMapping("/genreAdd")
    public String addGenre(Genre genre) {
        genreService.insert(genre.getName());
        return "redirect:/genresList";
    }
}
