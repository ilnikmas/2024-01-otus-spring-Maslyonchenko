package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Репозиторий на основе JPA для работы с комментариями ")
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("должен возвращать комментарий по id комментария")
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6})
    void findById(long commentId) {
        var actualComment = commentRepository.findById(commentId);
        var expectedComment = em.find(Comment.class, commentId);
        assertThat(actualComment).isPresent()
                .get()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен возвращать список всех комментариев по id книги")
    @Test
    void findAllByBookId() {
        var expectedComments = new ArrayList<Comment>();
        expectedComments.add(em.find(Comment.class, 1));
        expectedComments.add(em.find(Comment.class, 2));
        var actualComments = commentRepository.findAllByBookId(1);
        assertThat(actualComments).containsExactlyElementsOf(expectedComments);
    }

    @DisplayName("должен сохранять новый комментарий")
    @Test
    void shouldSaveNewComment() {
        Book book = em.find(Book.class, 1);
        var expectedComment = new Comment(0, book, "New comment");
        var returnedComment = commentRepository.save(expectedComment);

        var actualComment = em.find(Comment.class, returnedComment.getId());

        assertEquals(expectedComment, actualComment);
    }

    @DisplayName("должен удалять комментарий по id ")
    @Test
    void shouldDeleteComment() {
        assertThat(commentRepository.findById(1L)).isPresent();
        commentRepository.deleteById(1L);
        assertNull(em.find(Comment.class, 1L));
    }
}