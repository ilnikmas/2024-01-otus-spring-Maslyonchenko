package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.rest.BookRestController;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование контроллера книг")
@WebMvcTest(BookRestController.class)
class BookRestControllerTest {

    private static final long BOOK_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @DisplayName("Добавление новой книги")
    @Test
    void insertBook() throws Exception {
        Book book = getBook();

        mvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book)))
                .andExpect(status().isOk());
    }

    @DisplayName("Получение списка книг")
    @Test
    void getAllBooks() throws Exception {
        List<Book> books = Collections.singletonList(getBook());
        given(bookService.findAll()).willReturn(books);
        mvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(status().isOk());
    }

    @DisplayName("Обновление книги")
    @Test
    void updateBook() throws Exception {
        Book book = getBook();
        given(bookService.findById(book.getId())).willReturn(Optional.of(book));

        mvc.perform(MockMvcRequestBuilders.put("/api/books")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book)))
                .andExpect(status().isOk());
    }

    @DisplayName("Удаление книги по id")
    @Test
    void deleteBook() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", BOOK_ID))
                .andExpect(status().isOk());
    }

    private Book getBook() {
        given(authorService.findAll()).willReturn(List.of(new Author(1L, "Author_1")));
        given(genreService.findAll()).willReturn(List.of(new Genre(1L, "Genre_1")));

        return new Book(BOOK_ID, "BookTitle_1",
                authorService.findAll().get(0),
                genreService.findAll().get(0));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}