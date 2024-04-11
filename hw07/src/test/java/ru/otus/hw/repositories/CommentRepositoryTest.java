package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Comment;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

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
}