package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
@AllArgsConstructor
@Data
public class Comment {
    @Transient
    public static final String SEQUENCE_NAME = "comments_sequence";

    @Id
    private long id;

    private long bookId;

    private String text;
}