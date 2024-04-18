package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(String id);

    List<Comment> findAllByBookId(String id);

    Comment insert(String bookId, String commentText);

    void deleteById(String id);
}