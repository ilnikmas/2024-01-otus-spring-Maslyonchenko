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
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        List<Author> dbAuthors = getDbAuthors();
        List<Genre> dbGenres = getDbGenres();
        List<Book> dbBooks = getDbBooks(dbAuthors, dbGenres);
        mongoTemplate.save(new Comment("1", dbBooks.get(0), "Comment_1"));
        mongoTemplate.save(new Comment("2", dbBooks.get(1), "Comment_2"));
        mongoTemplate.save(new Comment("3", dbBooks.get(2), "Comment_3"));
        mongoTemplate.save(new Comment("4", dbBooks.get(0), "Comment_4"));
        mongoTemplate.save(new Comment("5", dbBooks.get(1), "Comment_5"));
        mongoTemplate.save(new Comment("6", dbBooks.get(2), "Comment_6"));
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(Comment.class);
    }

    @DisplayName("должен возвращать комментарий по id комментария")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5", "6"})
    void findById(String commentId) {
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
        expectedComments.add(mongoTemplate.findById("1", Comment.class));
        expectedComments.add(mongoTemplate.findById("4", Comment.class));
        var actualComments = commentRepository.findAllByBookId("1");
        assertThat(actualComments).containsExactlyElementsOf(expectedComments);
    }

    @DisplayName("должен сохранять новый комментарий")
    @Test
    void shouldSaveNewComment() {
        Book book = mongoTemplate.findById("1", Book.class);
        assert book != null;
        var expectedComment = new Comment(null, book, "New comment");
        var returnedComment = commentRepository.save(expectedComment);

        var actualComment = mongoTemplate.findById(returnedComment.getId(), Comment.class);

        assertEquals(expectedComment, actualComment);
    }

    @DisplayName("должен удалять комментарий по id ")
    @Test
    void shouldDeleteComment() {
        assertNotNull(mongoTemplate.findById("1", Comment.class));
        commentRepository.deleteById("1");
        assertNull(mongoTemplate.findById("1", Comment.class));
    }
    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id.toString(), "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id.toString(), "Genre_" + id))
                .toList();
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id.toString(), "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }
}