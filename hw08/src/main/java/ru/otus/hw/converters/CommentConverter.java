package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.Comment;

import java.util.List;

@Component
public class CommentConverter {
    public String commentListToString(List<Comment> comment) {
        StringBuilder sb = new StringBuilder();
        for (Comment item : comment) {
            sb.append(commentToString(item)).append("\n");
        }
        return sb.toString();
    }

    public String commentToString(Comment comment) {
        return "Id: %d, Text: %s".formatted(comment.getId(), comment.getText());
    }
}