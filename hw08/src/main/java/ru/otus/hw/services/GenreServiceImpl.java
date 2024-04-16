package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final SequenceGeneratorService sequenceGeneratorService;

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Transactional
    @Override
    public Genre save(String genreName) {
        long id = sequenceGeneratorService.generateSequence(Genre.SEQUENCE_NAME);
        return genreRepository.save(new Genre(id, genreName));
    }
}