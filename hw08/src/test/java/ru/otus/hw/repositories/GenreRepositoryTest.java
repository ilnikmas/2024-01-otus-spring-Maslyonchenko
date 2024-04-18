package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@DisplayName("Репозиторий на основе MongoRepository для работы с жанрами")
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.save(new Genre("1", "Genre_1"));
        mongoTemplate.save(new Genre("2", "Genre_2"));
        mongoTemplate.save(new Genre("3", "Genre_3"));
    }

    @DisplayName("должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresList() {
        var actualGenres = genreRepository.findAll();
        for (Genre genre : actualGenres) {
            var expectedGenre = mongoTemplate.findById(genre.getId(), Genre.class);
            assertEquals(expectedGenre, genre);
        }
    }

    @DisplayName("должен возвращать жанры по id")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3"})
    void shouldReturnCorrectAuthorsById(String genreId) {
        var actualGenre = genreRepository.findById(genreId);
        var expectedGenre = mongoTemplate.findById(genreId, Genre.class);
        assertThat(actualGenre).isPresent()
                .get()
                .isEqualTo(expectedGenre);
    }
}