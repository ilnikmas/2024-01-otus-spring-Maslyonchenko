package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final SequenceGeneratorService sequenceGeneratorService;

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAllByBookId(long id) {
        return commentRepository.findAllByBookId(id);
    }

    @Transactional
    @Override
    public Comment insert(long bookId, String commentText) {
        long id = sequenceGeneratorService.generateSequence(Comment.SEQUENCE_NAME);
        return commentRepository.insert(new Comment(id, bookId, commentText));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}