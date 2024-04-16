package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final SequenceGeneratorService sequenceGeneratorService;

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author save(String name) {
        long id = sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME);
        return authorRepository.save(new Author(id, name));
    }
}