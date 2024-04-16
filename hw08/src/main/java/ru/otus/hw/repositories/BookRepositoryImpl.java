package ru.otus.hw.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;

import java.util.Optional;

//@Repository
//public class BookRepositoryImpl implements BookRepositoryComplex {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Override
//    public Optional<Book> findById(long id) {
//        return Optional.empty();
//    }
//}
