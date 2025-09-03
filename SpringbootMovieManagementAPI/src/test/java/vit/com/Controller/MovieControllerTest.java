package vit.com.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import vit.com.Entity.Movies;
import vit.com.Service.MovieService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        Movies m1 = new Movies(1L, "Inception", "Nolan", 2010, "Sci-Fi", 9.0);
        Movies m2 = new Movies(2L, "Interstellar", "Nolan", 2014, "Sci-Fi", 8.5);

        List<Movies> movies = Arrays.asList(m1, m2);

        when(service.getAll()).thenReturn(movies);

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Inception"))
                .andExpect(jsonPath("$[1].title").value("Interstellar"));
    }

    @Test
    void testGetById() throws Exception {
        Movies movie = new Movies(1L, "Inception", "Nolan", 2010, "Sci-Fi", 9.0);

        when(service.getById(1L)).thenReturn(movie);

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.director").value("Nolan"));
    }

    @Test
    void testCreate() throws Exception {
        Movies movie = new Movies(null, "Tenet", "Nolan", 2020, "Action", 7.5);
        Movies savedMovie = new Movies(3L, "Tenet", "Nolan", 2020, "Action", 7.5);

        when(service.create(any(Movies.class))).thenReturn(savedMovie);

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("Tenet"));
    }

    @Test
    void testUpdate() throws Exception {
        Movies updated = new Movies(1L, "Inception 2", "Nolan", 2025, "Sci-Fi", 9.2);

        when(service.update(eq(1L), any(Movies.class))).thenReturn(updated);

        mockMvc.perform(put("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception 2"))
                .andExpect(jsonPath("$.releaseYear").value(2025));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }
}
