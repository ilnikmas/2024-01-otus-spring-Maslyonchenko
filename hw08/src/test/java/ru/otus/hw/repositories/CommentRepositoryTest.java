package ru.otus.hw.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@DirtiesContext
@DisplayName("Репозиторий на основе MongoRepository для работы с комментариями")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.save(new Comment(1L, 1L, "Comment_1"));
        mongoTemplate.save(new Comment(2L, 2L, "Comment_2"));
        mongoTemplate.save(new Comment(3L, 3L, "Comment_3"));
        mongoTemplate.save(new Comment(4L, 1L, "Comment_4"));
        mongoTemplate.save(new Comment(5L, 2L, "Comment_5"));
        mongoTemplate.save(new Comment(6L, 3L, "Comment_6"));
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(Comment.class);
    }

    @DisplayName("должен возвращать комментарий по id комментария")
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6})
    void findById(long commentId) {
        var actualComment = commentRepository.findById(commentId);
        var expectedComment = mongoTemplate.findById(commentId, Comment.class);
        assertThat(actualComment).isPresent()
                .get()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен возвращать список всех комментариев по id книги")
    @Test
    void findAllByBookId() {
        var expectedComments = new HashSet<Comment>();
        expectedComments.add(mongoTemplate.findById(1, Comment.class));
        expectedComments.add(mongoTemplate.findById(4, Comment.class));
        var actualComments = commentRepository.findAllByBookId(1);
        assertThat(actualComments).containsExactlyElementsOf(expectedComments);
    }

    @DisplayName("должен сохранять новый комментарий")
    @Test
    void shouldSaveNewComment() {
        Book book = mongoTemplate.findById(1, Book.class);
        assert book != null;
        var expectedComment = new Comment(0, book.getId(), "New comment");
        var returnedComment = commentRepository.save(expectedComment);

        var actualComment = mongoTemplate.findById(returnedComment.getId(), Comment.class);

        assertEquals(expectedComment, actualComment);
    }

    @DisplayName("должен удалять комментарий по id ")
    @Test
    void shouldDeleteComment() {
        assertNotNull(mongoTemplate.findById(1L, Comment.class));
        commentRepository.deleteById(1L);
        assertNull(mongoTemplate.findById(1L, Comment.class));
    }
}