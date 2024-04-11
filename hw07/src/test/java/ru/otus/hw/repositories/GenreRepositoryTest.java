package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий на основе JPA для работы с жанрами ")
@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresList() {
        var actualGenres = genreRepository.findAll();
        for (Genre genre : actualGenres) {
            var expectedGenre = em.find(Genre.class, genre.getId());
            assertEquals(expectedGenre, genre);
        }
    }

    @DisplayName("должен возвращать жанры по id")
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    void shouldReturnCorrectAuthorsById(long genreId) {
        var actualGenre = genreRepository.findById(genreId);
        var expectedGenre = em.find(Genre.class, genreId);
        assertThat(actualGenre).isPresent()
                .get()
                .isEqualTo(expectedGenre);
    }
}