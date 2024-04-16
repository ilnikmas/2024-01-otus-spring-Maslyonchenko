package ru.otus.hw.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
@NoArgsConstructor
@Data
public class DatabaseSequence {
    @Id
    private String id;

    private long seq;
}
