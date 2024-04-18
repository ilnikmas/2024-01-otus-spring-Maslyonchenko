package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
@AllArgsConstructor
@Data
public class Comment {

    @Id
    private String id;

    private Book book;

    private String text;
}