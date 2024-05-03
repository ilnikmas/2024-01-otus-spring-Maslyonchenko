package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final BookService bookService;

    @GetMapping("/commentsList/{id}")
    public String getCommentsByBookId(@PathVariable("id") long id, Model model) {
        Book book = bookService.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        List<Comment> comments = commentService.findAllByBookId(id);
        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        return "comments-list";
    }
}
