package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataMongoTest
@DisplayName("Репозиторий на основе MongoRepository для работы с книгами")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        mongoTemplate.save(new Book(1L, "BookTitle_1",
                dbAuthors.get(0), dbGenres.get(0)));
        mongoTemplate.save(new Book(2L, "BookTitle_2",
                dbAuthors.get(1), dbGenres.get(1)));
        mongoTemplate.save(new Book(3L, "BookTitle_3",
                dbAuthors.get(2), dbGenres.get(2)));
    }

    @DisplayName("должен загружать книгу по id")
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    void shouldReturnCorrectBookById(long bookId) {
        var actualBook = bookRepository.findById(bookId);
        var expectedBook = mongoTemplate.findById(bookId, Book.class);
        assertThat(actualBook).isPresent()
                .get()
                .isEqualTo(expectedBook);

    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = bookRepository.findAll();
        for (Book book : actualBooks) {
            var expectedBook = mongoTemplate.findById(book.getId(), Book.class);
            assertEquals(expectedBook, book);
        }
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = new Book(1, "BookTitle_10500", dbAuthors.get(0), dbGenres.get(0));
        var returnedBook = bookRepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        var actualBook = mongoTemplate.findById(returnedBook.getId(), Book.class);

        assertEquals(returnedBook, actualBook);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        assertNotNull(mongoTemplate.findById(1L, Book.class));
        var expectedBook = new Book(1L, "BookTitle_10500", dbAuthors.get(2), dbGenres.get(2));

        var returnedBook = bookRepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        var actualBook = mongoTemplate.findById(returnedBook.getId(), Book.class);

        assertEquals(returnedBook, actualBook);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        assertNotNull(mongoTemplate.findById(1L, Book.class));
        bookRepository.deleteById(1L);
        assertNull(mongoTemplate.findById(1L, Book.class));
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }
}