package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(long id);

    List<Comment> findAllByBookId(long id);

    Comment insert(long bookId, String commentText);

    void deleteById(long id);
}