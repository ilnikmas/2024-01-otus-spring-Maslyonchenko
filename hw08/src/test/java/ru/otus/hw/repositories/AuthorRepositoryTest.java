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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@DisplayName("Репозиторий на основе MongoRepository для работы с авторами")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.save(new Author(1L, "Author_1"));
        mongoTemplate.save(new Author(2L, "Author_2"));
        mongoTemplate.save(new Author(3L, "Author_3"));
    }

    @DisplayName("должен возвращать авторов по id")
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    void shouldReturnCorrectAuthorsById(long authorId) {
        var actualAuthor = authorRepository.findById(authorId);
        var expectedAuthor = mongoTemplate.findById(authorId, Author.class);
        assertThat(actualAuthor).isPresent()
                .get()
                .isEqualTo(expectedAuthor);
    }

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsList() {
        var actualAuthors = authorRepository.findAll();
        for (Author author : actualAuthors) {
            var expectedAuthor = mongoTemplate.findById(author.getId(), Author.class);
            assertEquals(expectedAuthor, author);
        }
    }
}